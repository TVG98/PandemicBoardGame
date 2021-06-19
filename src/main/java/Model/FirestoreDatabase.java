package Model;

import Controller.DatabaseController;
import Exceptions.LobbyFullException;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.sun.media.jfxmediaimpl.HostUtils;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

    public void updateJoinable(String lobbyCode, boolean joinable) {
        if (docRef == null) {
            docRef = lobbyRef.document(lobbyCode);
        }

        data.setJoinable(joinable);
        docRef.update("Joinable", data.isJoinable());
    }

    public void addPlayerToLobby(String lobbyCode, Player player) throws LobbyFullException {
        docRef = lobbyRef.document(lobbyCode);
        int index = getServerPlayerIndex(null);

        data.setPlayer(index, player);
        docRef.update("players", data.getPlayers());
    }

    public void removePlayerFromLobby(Player player) {
        try {
            int index = getServerPlayerIndex(player.getPlayerName());
            data.setPlayer(index, null);
            docRef.update("players", data.getPlayers());
        } catch (LobbyFullException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerInServer(Player player) {
        try {
            int index = getServerPlayerIndex(player.getPlayerName());
            data.setPlayer(index, player);
            System.out.println("updating player " + index);
            docRef.update("players", data.getPlayers());
        } catch (LobbyFullException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    public String makeLobby() {
        HashMap<String, Object> serverData = getServerData();
        String lobbyCode = generateLobbyCode();
        docRef = lobbyRef.document(lobbyCode);
        docRef.set(serverData);
        return lobbyCode;
    }

    public HashMap<String, Object> getServerData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        data.setJoinable(true);
        data.setGameStarted(false);
        hashMap.put("GameStarted", data.isGameStarted());
        hashMap.put("Joinable", data.isJoinable());
        hashMap.put("players", data.getPlayers());
        hashMap.put("gameboard", data.getGameboard());

        return hashMap;
    }

    public void updateGameStarted(boolean gameStarted) {
        docRef.update("GameStarted", gameStarted);
    }

    public void updateGameBoard(Gameboard gameboard) {
        docRef.update("gameboard", gameboard);
    }

    public int getServerPlayerIndex(String obj) throws LobbyFullException {
        for (int i = 0; i < 4; i++) {
            try {
                data = docRef.get().get().toObject(DatabaseData.class);
                if (obj == null) {
                    if (data.getPlayer(i) == null) {
                        return i;
                    }
                } else if (data.getPlayer(i).getPlayerName().equals(obj)) {
                    return i;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        throw new LobbyFullException("Lobby is full.");
    }

    public DocumentSnapshot getLobbyByDocumentId(String lobbyCode) {

        ApiFuture<DocumentSnapshot> future = lobbyRef.document(lobbyCode).get();
        DocumentSnapshot document;

        try {
            document = future.get();

            if (document.exists()) {
                return document;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String generateLobbyCode() {
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
                    System.out.println("updating games");
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
