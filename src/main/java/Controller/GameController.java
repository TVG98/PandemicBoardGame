package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Exceptions.GameLostException;
import Exceptions.PlayerNotFoundException;
import Model.*;
import Observers.GameBoardObserver;
import Observers.GameObserver;

import java.util.List;
import java.util.Random;

public class GameController {
    static GameController gameController;

    private final Game game;
    private final PlayerController playerController;
    private final GameBoardController gameBoardController;
    private final LobbyController lobbyController;
    private final DatabaseController databaseController;
    private boolean playersUpdated = false;

    private GameController() {
        lobbyController = LobbyController.getInstance();
        lobbyController.setServerLobbyNotJoinable();
        databaseController = DatabaseController.getInstance();
        databaseController.updateGameStarted(true);
        game = new Game(lobbyController.getLobby().getPlayers());
        playerController = PlayerController.getInstance();
        gameBoardController = GameBoardController.getInstance();
        gameBoardController.makeGameBoard();
        startGame();
        game.notifyAllObservers();
    }

    public synchronized static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }
        return gameController;
    }

    private boolean localPlayerIsPlayerOne() {
        String localPlayerName = playerController.getYourPlayerName();
        String firstPlayerName = game.getPlayers().get(0).getPlayerName();
        return localPlayerName.equals(firstPlayerName);
    }

    public void startGame() {
        setPlayers();
        makeGameBoard();
    }

    private void setPlayers() {
        if (localPlayerIsPlayerOne()) {
            if (!playersUpdated) {
                playersUpdated = true;
                for (Player p : game.getPlayers()) {
                    if (p != null) {
                        setPlayer(p);
                    }
                }
            }
        }
    }

    private void makeGameBoard() {
        if (localPlayerIsPlayerOne()) {
            gameBoardController.makeWholeGameBoard();
        }

        sleep(3000);
    }

    private void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private void setPlayer(Player player) {
        try {
            player.setRole(getRandomRole());
            player.setCurrentCity(gameBoardController.getCity("Atlanta"));
            databaseController.updatePlayerInServer(player);
        } catch(CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    private Role getRandomRole() {
        return Role.values()[new Random().nextInt(Role.values().length)];
    }

    public void turn() {
        if (!playerController.hasActionsLeft(getCurrentPlayer()) && itIsYourTurn()) {
            try {
                gameBoardController.handlePlayerCardDraw(getCurrentPlayer(), getPlayerAmount());
                gameBoardController.handleInfectionCardDraw();
                playerController.endTurn(getCurrentPlayer());
                checkWin();
                changeTurn();
            } catch (GameLostException gle) {
                game.setLost();
            }

        }
    }

    private int getPlayerAmount() {
        List<Player> players = game.getPlayers();
        int size = 0;

        for (Player player : players) {
            size += (player == null) ? 0 : 1;
        }

        return size;
    }

    public void changeTurn() {
        game.nextTurn();
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        updateServer(currentPlayerIndex);
    }

    public void checkWin() {
        if (gameBoardController.winByCures()) {
            game.setWon();
        }
    }

    public void handleDrive(String cityName) {
        if (itIsYourTurn()) {
            try {
                Player currentPlayer = getCurrentPlayer();
                City city = gameBoardController.getCity(cityName);
                setDriveBehavior();
                gameBoardController.handleDrive(currentPlayer, city);
                databaseController.updatePlayerInServer(currentPlayer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDriveBehavior() {
        if (itIsYourTurn()) {
            gameBoardController.setDriveBehavior(new DriveBehaviorNormal());
        }
    }

    public void handleDirectFlight(String cityName) {
        if (itIsYourTurn()) {
            try {
                City city = gameBoardController.getCity(cityName);
                Player currentPlayer = getCurrentPlayer();
                gameBoardController.handleDirectFlight(currentPlayer, city);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDirectFlightBehavior() {
        if (itIsYourTurn()) {
            gameBoardController.setDirectFlightBehavior(new DirectFlightBehaviorNormal());
        }
    }

    public void handleCharterFlight(String cityName) {
        if (itIsYourTurn()) {
            try {
                City city = gameBoardController.getCity(cityName);
                Player currentPlayer = getCurrentPlayer();
                setCharterFlightBehavior();
                gameBoardController.handleCharterFlight(currentPlayer, city);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setCharterFlightBehavior() {
        if (itIsYourTurn()) {
            gameBoardController.setCharterFlightBehavior(new CharterFlightBehaviorNormal());
        }
    }

    public void handleShuttleFlight(String cityName) {
        if (itIsYourTurn()) {
            try {
                City city = gameBoardController.getCity(cityName);
                Player currentPlayer = getCurrentPlayer();
                setShuttleFlightBehavior(currentPlayer);
                gameBoardController.handleShuttleFlight(currentPlayer, city);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setShuttleFlightBehavior(Player currentPlayer) {
        if (itIsYourTurn()) {
            if (gameBoardController.canShuttleFlightToAnyCity(currentPlayer)) {
                gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorToAnyCity());
            } else {
                gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorNormal());
            }
        }
    }

    public void handleBuildResearchStation() {
        if (itIsYourTurn()) {
            Player currentPlayer = getCurrentPlayer();
            City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
            setBuildResearchBehavior(currentPlayer);
            if (gameBoardController.canAddResearchStation()) {
                gameBoardController.handleBuildResearchStation(currentPlayer, currentCity);
            }
        }
    }

    private void setBuildResearchBehavior(Player currentPlayer) {
        if (itIsYourTurn()) {
            if (gameBoardController.canBuildResearchStationWithoutCard(currentPlayer)) {
                gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationWithoutCard());
            } else {
                gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationNormal());
            }
        }
    }

    public void handleShareKnowledge(String playerName, boolean giveCard) {
        if (itIsYourTurn()) {
            Player givingPlayer = null;
            Player receivingPlayer = null;

            try {
                if (giveCard) {
                    givingPlayer = getCurrentPlayer();
                    receivingPlayer = getPlayerByName(playerName);
                } else {
                    givingPlayer = getPlayerByName(playerName);
                    receivingPlayer = getCurrentPlayer();
                }
                setShareKnowledgeBehavior(givingPlayer, receivingPlayer);
                gameBoardController.handleShareKnowledge(givingPlayer, receivingPlayer);
            } catch (PlayerNotFoundException pnfe) {
                pnfe.printStackTrace();
            }
        }
    }

    private Player getPlayerByName(String playerName) throws PlayerNotFoundException {
        for(Player player : game.getPlayers()) {
            if(player.getPlayerName().equals(playerName)) {
                return player;
            }
        }

        throw new PlayerNotFoundException("Player with " + playerName + " does not exist");
    }

    private void setShareKnowledgeBehavior(Player givingPlayer, Player receivingPlayer) {
        if (itIsYourTurn()) {
            if(gameBoardController.canShareAnyCard(givingPlayer, receivingPlayer)) {
                gameBoardController.setShareKnowledgeBehavior(new ShareKnowledgeOnAnyCityBehavior());
            } else {
                gameBoardController.setShareKnowledgeBehavior(new ShareKnowledgeOnSameCityBehavior());
            }
        }
    }

    public void handleTreatDisease() {
        if (itIsYourTurn()) {
            Player currentPlayer = getCurrentPlayer();
            City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
            setTreatDiseaseBehavior(currentPlayer, currentCity);
            gameBoardController.handleTreatDisease(currentPlayer, currentCity);
        }
    }

    private void setTreatDiseaseBehavior(Player currentPlayer, City currentCity) {
        if (itIsYourTurn()) {
            if (gameBoardController.canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
                gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubesWithoutActionDecrement());
            } else if (gameBoardController.canRemoveAllCubes(currentPlayer, currentCity)){
                gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubes());
            } else {
                gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseOneCube());
            }
        }
    }

    public void handleFindCure() {
        if (itIsYourTurn()) {
            Player currentPlayer = getCurrentPlayer();
            City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
            setFindCureBehavior(currentPlayer);
            gameBoardController.handleFindCure(currentPlayer, currentCity);
        }
    }

    private void setFindCureBehavior(Player currentPLayer) {
        if (itIsYourTurn()) {
            if(gameBoardController.canFindCureWithFourCards(currentPLayer)) {
                gameBoardController.setFindCureBehavior(new FindCureWithFourCardsBehavior());
            } else {
                gameBoardController.setFindCureBehavior(new FindCureWithFiveCardsBehavior());
            }
        }
    }

    public void checkCardInHand(PlayerCard card, Player player) {
        playerController.checkCardInHand(card, player);
    }

    public void handleGiveCard(PlayerCard card, Player player1, Player player2) {
        if (itIsYourTurn()) {
            playerController.removeCard(card, player1);
            playerController.addCard(card, player2);
        }
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    private boolean itIsYourTurn() {
        String currentPlayerName = game.getPlayers().get(game.getCurrentPlayerIndex()).getPlayerName();
        return playerController.getYourPlayerName().equals(currentPlayerName);
    }

    public synchronized void update(DatabaseData data) {
        game.updatePlayers(data.getPlayers());
        game.setCurrentPlayerIndex(data.getCurrentPlayerIndex());
    }

    public void registerPlayerObserver(GameObserver observer) {
        game.register(observer);
    }

    public void registerGameBoardObserver(GameBoardObserver observer) {
        gameBoardController.registerObserver(observer);
    }

    public void notifyGameBoardObserver() {
        gameBoardController.notifyGameBoardObserver();
    }

    public void notifyGameObserver() {
        game.notifyAllObservers();
    }

    public void updateServer(int index) {
        databaseController.updateIndexInDatabase(index);
    }
}
