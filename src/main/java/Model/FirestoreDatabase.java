package Model;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * @created May 26 2021 - 7:35 PM
 * @project testGame
 */

public class FirestoreDatabase {

    private Firestore db;
    private static final String LOBBY_PATH = "lobbies";
    private CollectionReference lobbyRef;

    public FirestoreDatabase()
    {
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws Exception {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/Firebasejson/gametest-3a5f7-firebase-adminsdk-lfjkr-3d3c163166.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        this.db = FirestoreClient.getFirestore();
        this.lobbyRef = this.db.collection(LOBBY_PATH);
    }

    public void addPlayer()
    {

    }

    public void makeLobby(Player player)
    {
        HashMap<String, Object> quote = createLobbyData(player);
        lobbyRef.document("Test").set(quote);

    }

    public HashMap<String, Object> createLobbyData(Player player) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, String> password = new HashMap<>();
        HashMap<String, Player> playerMap = new HashMap<>();

        playerMap.put("Player1", player);
        password.put("password", generateLobbyPassword());
        hashMap.put("Lobby", password);
        hashMap.put("Players", playerMap);
        return hashMap;
    }

    private String generateLobbyPassword() {
        final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int passwordLength = 8;
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int index = (int)(CHARSET.length() * Math.random());
            password.append(CHARSET.charAt(index));
        }
        System.out.println(password);
        return password.toString();
    }

    public DocumentSnapshot getLobbyByDocumentId(String documentId)
    {
        DocumentReference docRef = this.lobbyRef.document(documentId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document;

        try {
            document = future.get();

            if (document.exists()) {
                System.out.println("Document exists");
                System.out.println(document.get("lobby1"));
                return document;
            } else {
                System.out.println("No such document!");
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
        return null;
    }

}
