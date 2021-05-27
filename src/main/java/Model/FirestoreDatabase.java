package Model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @created May 26 2021 - 7:35 PM
 * @project testGame
 */

public class FirestoreDatabase {

    private Firestore db;

    public FirestoreDatabase()
    {
        try{
            initialize();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/Firebasejson/gametest-3a5f7-firebase-adminsdk-lfjkr-3d3c163166.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }


}
