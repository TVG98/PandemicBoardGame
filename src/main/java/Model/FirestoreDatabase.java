package Model;

import Controller.DatabaseController;
import Exceptions.LobbyFullException;
import Exceptions.PlayerNotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreDatabase {
    ListenerRegistration listenerRegistration;

    private Firestore database;
    private static final String LOBBY_PATH = "lobbies";
    private CollectionReference lobbyRef;
    private DocumentReference docRef;
    private DatabaseData data = new DatabaseData();

    private final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int passwordLength = 8;
    private final String fireBaseJsonPath = "src/main/Firebasejson/iipsene-pandemic-firebase-adminsdk-bz9da-2236dabc43.json";

    static FirestoreDatabase firestoreDatabase;

    private FirestoreDatabase() {
            initialize();
    }

    public static FirestoreDatabase getInstance() {
        if (firestoreDatabase == null) {
            firestoreDatabase = new FirestoreDatabase();
        }

        return firestoreDatabase;
    }

    public void initialize() {
        try {
            startUpFirebase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        database = FirestoreClient.getFirestore();
        lobbyRef = this.database.collection(LOBBY_PATH);
    }

    private void startUpFirebase() throws Exception {
        FileInputStream serviceAccount = new FileInputStream(fireBaseJsonPath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void makeLobby(String lobbyCode) {
        HashMap<String, Object> serverData = getServerData();
        docRef = lobbyRef.document(lobbyCode);
        docRef.set(serverData);
    }

    public HashMap<String, Object> getServerData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("currentPlayerIndex", data.getCurrentPlayerIndex());
        hashMap.put("GameStarted", data.isGameStarted());
        hashMap.put("Joinable", data.isJoinable());
        hashMap.put("players", data.getPlayers());
        hashMap.put("gameboard", data.getGameboard());

        return hashMap;
    }

    public void updateIndex(int index) {
        docRef.update("currentPlayerIndex", index);
    }

    public void updateGameStarted(boolean gameStarted) {
        docRef.update("GameStarted", gameStarted);
    }

    public void updateGameBoard(Gameboard gameboard) {
        docRef.update("gameboard", gameboard);
    }

    public void updateJoinable(boolean joinable) {
        data.setJoinable(joinable);
        docRef.update("Joinable", data.isJoinable());
    }

    public void addPlayerToLobby(Player player) throws LobbyFullException {
        int index = getEmptySpot();
        data.setPlayer(index, player);
        docRef.update("players", data.getPlayers());
    }

    public void removeMeFromLobby(String name) {
        try {
            data.setPlayer(getIndexOfName(name), null);
            docRef.update("players", data.getPlayers());
        } catch (PlayerNotFoundException lbe) {
            lbe.printStackTrace();
        }
    }

    public void updatePlayerInServer(Player player) {
        try {
            data.setPlayer(getIndexOfName(player.getPlayerName()), player);
            docRef.update("players", data.getPlayers());
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    public int getEmptySpot() throws LobbyFullException {
        try {
            data = docRef.get().get().toObject(DatabaseData.class);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Player> players = data.getPlayers();

        return getEmptyIndex(players);
    }

    private int getEmptyIndex(List<Player> players) throws LobbyFullException {
        for (int i = 0; i < 4; i++) {
            if (players.get(i) == null) {
                return i;
            }
        }

        throw new LobbyFullException("Lobby is full.");
    }

    private int getIndexOfName(String name) throws PlayerNotFoundException {
        List<Player> players = data.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerName().equals(name)) {
                return i;
            }
        }

        throw new PlayerNotFoundException("Player not found:" + name);
    }

    public String generateLobbyCode() {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            password.append(getRandomChar());
        }

        return password.toString();
    }

    public char getRandomChar() {
        return CHARSET.charAt((int) (CHARSET.length() * Math.random()));
    }

    public void listen(DatabaseController controller, String lobbyCode) {
        if (listenerRegistration == null) {
            EventListener<DocumentSnapshot> eventListener = makeEventListener(controller);

            if (docRef == null) {
                docRef = lobbyRef.document(lobbyCode);
            }

            listenerRegistration = docRef.addSnapshotListener(eventListener);
        }
    }

    private EventListener<DocumentSnapshot> makeEventListener(DatabaseController controller) {
        return (snapshot, e) -> {
            if (snapshot != null && snapshot.exists()) {
                try {
                    System.out.println("new update from FireStore!");
                    data = snapshot.toObject(DatabaseData.class);
                    controller.update(data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ex.getCause().printStackTrace();
                }
            }
        };
    }

    public DatabaseData getDatabaseData() {
        return data;
    }
}
