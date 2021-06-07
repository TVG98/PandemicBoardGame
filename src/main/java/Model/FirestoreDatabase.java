package Model;

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

/**
 * @created May 26 2021 - 7:35 PM
 * @project testGame
 */

public class FirestoreDatabase {

    private Firestore db;
    private static final String LOBBY_PATH = "lobbies";
    private CollectionReference lobbyRef;

    public FirestoreDatabase() {
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

    public void updatePlayersInLobby(String documentId, Lobby lobby) {
        HashMap<String, Object> quote = new HashMap<>();
        quote.put("Players", lobby.getPlayers());

        DocumentReference documentReference = this.lobbyRef.document(documentId);
        documentReference.update("Players", lobby.getPlayers());
    }

    public Lobby makeLobby(Player player) {
        Lobby lobby = new Lobby(player);
        HashMap<String, Object> quote = createLobbyData(lobby);
        lobbyRef.document(lobby.getPassword()).set(quote);
        return lobby;
    }

    public HashMap<String, Object> createLobbyData(Lobby lobby) {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("Joinable", lobby.getJoinable());
        hashMap.put("Players", lobby.getPlayers());
        return hashMap;
    }

    public DocumentSnapshot getLobbyByDocumentId(String documentId) {
        DocumentReference docRef = this.lobbyRef.document(documentId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document;

        try {
            document = future.get();

            if (document.exists()) {
                System.out.println("Document exists");
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
