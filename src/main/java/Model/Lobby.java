package Model;

import Observers.LobbyObservable;
import Observers.LobbyObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Lobby implements LobbyObservable {
    private List<LobbyObserver> observers = new ArrayList<LobbyObserver>();

    private boolean joinable;
    private ArrayList<Player> players;
    private final String passwd;

    public Lobby(Player player) {
        this.joinable = true;
        this.players = new ArrayList<>();
        addPlayer(player);
        this.passwd = generateLobbyPassword();
    }

    public Lobby(String passwd) {
        this.joinable = true;
        this.players = new ArrayList<>();
        this.passwd = passwd;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean getJoinable() {
        return joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
        notifyAllObservers();
    }

    public void addPlayer(Player player) {
        if (joinable) {
            this.players.add(player);
            if (players.size() == 4) {
                setJoinable(false);
            }
        }
        //notifyAllObservers();
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void startGame() {
        if (players.size() > 1) {
            for (Player player : players) {
                if (!player.getReadyToStart()) {
                    return;
                }
            }
            //Todo: start game
        }
    }

    private String generateLobbyPassword() {
        final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int passwordLength = 8;
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int index = (int)(CHARSET.length() * Math.random());
            password.append(CHARSET.charAt(index));
        }
        return password.toString();
    }

    public String getPassword() {
        return passwd;
    }

    public boolean checkLobbyCode(String code) {
        return this.passwd.equals(code);
    }

    @Override
    public void register(LobbyObserver observer) {
        System.out.println("registreer");
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        System.out.println(observers.size());
        for (LobbyObserver s : observers) {
            s.update(this);
        }
    }
}
