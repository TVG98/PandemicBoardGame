package Model;

import Controller.DatabaseController;
import Exceptions.LobbyFullException;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

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

//        hashMap.put("Joinable", true);
//        hashMap.put("GameStarted", false);
//        hashMap.put("Player1", null);
//        hashMap.put("Player2", null);
//        hashMap.put("Player3", null);
//        hashMap.put("Player4", null);
//        hashMap.put("cities", null);
//        hashMap.put("citiesWithResearchStations", null);
//        hashMap.put("curedDiseases", null);
//        hashMap.put("cures", null);
//        hashMap.put("drawnEpidemicCards", 0);
//        hashMap.put("infectionDiscardStack", null);
//        hashMap.put("infectionRate", 2);
//        hashMap.put("infectionStack", null);
//        hashMap.put("outbreakCounter", 0);
//        hashMap.put("playerDiscardStack", null);
//        hashMap.put("playerStack", null);
//        hashMap.put("topSixInfectionStack", null);
//        hashMap.put("viruses", null);

        return hashMap;
    }

    public void updateGameStarted(boolean gameStarted) {
        docRef.update("GameStarted", gameStarted);
    }

    public void updateGameBoard(Gameboard gameboard) {
        docRef.update("gameboard", gameboard);
    }


//    public void updateCities(List<City> cities) {
//        System.out.println("updating cities!" + cities.get(0).getName() + " : " + cities.get(0).getCubeAmount());
//        try {
//            docRef.update("cities", cities);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateCitiesWithResearchStations(List<City> cities) {
//        try {
//            docRef.update("citiesWithResearchStations", cities);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateCuredDiseases(List<Cure> curedDiseases) {
//        try {
//            docRef.update("curedDiseases", curedDiseases);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateCures(List<Cure> CURES) {
//        try {
//            docRef.update("cures", CURES);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateDrawnEpidemicCards(int drawnEpidemicCards) {
//        try {
//            docRef.update("drawnEpidemicCards", drawnEpidemicCards);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateInfectionDiscardStack(ArrayList<InfectionCard> infectionDiscardStack) {
//        try {
//            docRef.update("infectionDiscardStack", infectionDiscardStack);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateInfectionRate(int infectionRate) {
//        try {
//            docRef.update("infectionRate", infectionRate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateInfectionStack(ArrayList<InfectionCard> infectionStack) {
//        try {
//            docRef.update("infectionStack", infectionStack);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateOutbreakCounter(int outbreakCounter) {
//        try {
//            docRef.update("outbreakCounter", outbreakCounter);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updatePlayerDiscardStack(ArrayList<PlayerCard> playerDiscardStack) {
//
//        ArrayList<String> playerCardNames = new ArrayList<>();
//
//        for(PlayerCard card : playerDiscardStack) {
//            playerCardNames.add(card.getName());
//        }
//
//        try {
//            docRef.update("playerDiscardStack", playerCardNames);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updatePlayerStack(ArrayList<PlayerCard> playerStack) {
//
//        ArrayList<String> playerCardNames = new ArrayList<>();
//
//        for(PlayerCard card : playerStack) {
//            playerCardNames.add(card.getName());
//        }
//
//        try {
//            docRef.update("playerStack", playerCardNames);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateTopSixInfectionStack(ArrayList<InfectionCard> topSixInfectionStack) {
//        try {
//            docRef.update("topSixInfectionStack", topSixInfectionStack);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }
//
//    public void updateViruses(List<Virus> viruses) {
//        try {
//            docRef.update("viruses", viruses);
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause().printStackTrace();
//        }
//    }

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
