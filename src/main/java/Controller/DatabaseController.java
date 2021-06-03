package Controller;

import Model.FirestoreDatabase;
import Model.Player;
//import com.google.cloud.firestore.Firestore;

/**
 * @created May 26 2021 - 8:58 PM
 * @project testGame
 */
public class DatabaseController
{
    private final FirestoreDatabase db;

    public DatabaseController() {
        db = new FirestoreDatabase();
    }

    public void addPlayer()
    {

    }

    public void makeLobby(Player player)
    {
        db.makeLobby(player);
    }

    public void initializeDatabase()
    {
        try {
            db.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
