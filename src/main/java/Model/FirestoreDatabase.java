package Model;

import Controller.DatabaseController;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FirestoreDatabase {
    ListenerRegistration listenerRegistration;

    private Firestore database;
    private static final String LOBBY_PATH = "lobbies";
    private CollectionReference lobbyRef;
    private DocumentReference docRef;

    private final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int passwordLength = 8;
    private final String fireBaseJsonPath = "src/main/Firebasejson/gametest-3a5f7-firebase-adminsdk-lfjkr-3d3c163166.json";

    public FirestoreDatabase() {
            initialize();
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

    public void addPlayerToLobby(String lobbyCode, Player player) {
        docRef = lobbyRef.document(lobbyCode);
        System.out.println(getLobbyByDocumentId(lobbyCode).getDouble("PlayerAmount"));
        docRef.update("PlayerAmount", FieldValue.increment(1));
        docRef.update("Players", FieldValue.arrayUnion(player));
    }

    public void removePlayerFromLobby(String LobbyCode, Player player) {
        docRef.update("Players", FieldValue.arrayRemove(player));
        docRef.update("PlayerAmount", FieldValue.increment(-1));
    }

    public void updatePlayersInLobby(ArrayList<Player> players) {
        for (Player p: players) {
            System.out.println(p.getReadyToStart());
        }

        docRef.update("Players", players);
    }

    public String makeLobby() {
        HashMap<String, Object> quote = createLobbyData();
        String lobbyCode = generateLobbyCode();
        docRef = lobbyRef.document(lobbyCode);
        docRef.set(quote);
        return lobbyCode;
    }

    public HashMap<String, Object> createLobbyData() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("PlayerAmount", 0);
        hashMap.put("Players", new ArrayList<Player>());
        return hashMap;
    }

    public DocumentSnapshot getLobbyByDocumentId(String lobbyCode) {

        ApiFuture<DocumentSnapshot> future = lobbyRef.document(lobbyCode).get();
        DocumentSnapshot document;

        try {
            document = future.get();

            if (document.exists()) {
                System.out.println("Document exists");
                return document;
            } else {
                System.out.println("No such document!");
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
                System.out.println(snapshot.getData());
                controller.update(snapshot.getData());
            }
        };
    }
}
