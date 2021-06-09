package Model;

import Controller.DatabaseController;
import Controller.LobbyController;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FirestoreDatabase {
    ListenerRegistration listenerRegistration;

    private Firestore db;
    private static final String LOBBY_PATH = "lobbies";
    private CollectionReference lobbyRef;
    private DocumentReference docRef;

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

    public void addPlayerToLobby(String lobbyCode, Player player) {
        if (docRef == null) {
            docRef = lobbyRef.document(lobbyCode);
        }
        docRef.update("Players", FieldValue.arrayUnion(player));
    }

    public void removePlayerFromLobby(String LobbyCode, Player player) {
        docRef.update("Players", FieldValue.arrayRemove(player));
    }

    public void updatePlayersInLobby(ArrayList<Player> players) {
        for (Player p: players) {
            System.out.println(p.getReadyToStart());
        }
        docRef.update("Players", players);
    }

    public Lobby makeLobby(Player player) {
        Lobby lobby = new Lobby(player);
        HashMap<String, Object> quote = createLobbyData(lobby);
        docRef = lobbyRef.document(lobby.getPassword());
        docRef.set(quote);
        return lobby;
    }

    public HashMap<String, Object> createLobbyData(Lobby lobby) {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("Joinable", lobby.getJoinable());
        hashMap.put("Players", lobby.getPlayers());
        return hashMap;
    }

    public DocumentSnapshot getLobbyByDocumentId() {
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

    public void listen(DatabaseController controller, String lobbyCode) {
        if (listenerRegistration == null) {
            EventListener<DocumentSnapshot> eventListener = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot snapshot, @Nullable FirestoreException e) {
                    if (snapshot != null && snapshot.exists()) {
                        System.out.println(snapshot.getData());
                        controller.update(snapshot.getData());
                    }
                }
            };

            if (docRef == null) {
                docRef = lobbyRef.document(lobbyCode);
            }
            listenerRegistration = docRef.addSnapshotListener(eventListener);
        }
    }
}
