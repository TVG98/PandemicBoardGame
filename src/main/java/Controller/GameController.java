package Controller;

import Controller.Behavior.*;
import Exceptions.CardNotFoundException;
import Exceptions.CityNotFoundException;
import Exceptions.PlayerNotFoundException;
import Model.*;
import Observers.GameBoardObserver;
import Observers.GameObserver;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

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
        String localPlayerName = playerController.getCurrentPlayerName();
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
        // Ik weet niet zo goed hoe we de acties gaan vormgeven in een beurt.
        // ik ook niet man
        // nice xD
        // helemaal mooi

        if (getCurrentPlayer().actionsPlayed()) {  // Zodra de acties gespeeld zijn
            gameBoardController.handlePlayerCardDraw(getCurrentPlayer(), game.getPlayerAmount());  // Pak twee spelerkaarten
            gameBoardController.handleInfectionCardDraw();
            getCurrentPlayer().endTurn();
            changeTurn();
        }

    }

    public void changeTurn(){
        game.nextTurn();
    }

    public void checkLoss() {
        if (gameBoardController.lossByCubeAmount()) {
            game.setLost();
        } else if (gameBoardController.lossByEmptyPlayerCardStack()) {
            game.setLost();
        } else if (gameBoardController.lossByOutbreakCounter()) {
            game.setLost();
        }
    }
    // Misschien dat we de checkLoss en de checkWin method kunnen samenvoegen
    public void checkWin() {
        if (gameBoardController.winByCures()) {
            game.setWon();
        }
    }

    public void handleDrive(String cityName) {
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

    private void setDriveBehavior() {
        gameBoardController.setDriveBehavior(new DriveBehaviorNormal());
    }

    public void handleDirectFlight(String cityName) {
        try {
            City city = gameBoardController.getCity(cityName);
            Player currentPlayer = getCurrentPlayer();
            gameBoardController.handleDirectFlight(currentPlayer, city);
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    private void setDirectFlightBehavior() {
        gameBoardController.setDirectFlightBehavior(new DirectFlightBehaviorNormal());
    }

    public void handleCharterFlight(String cityName) {
        try {
            City city = gameBoardController.getCity(cityName);
            Player currentPlayer = getCurrentPlayer();
            setCharterFlightBehavior();
            gameBoardController.handleCharterFlight(currentPlayer, city);
        } catch(CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    private void setCharterFlightBehavior() {
        gameBoardController.setCharterFlightBehavior(new CharterFlightBehaviorNormal());
    }

    public void handleShuttleFlight(String cityName) {
        try {
            City city = gameBoardController.getCity(cityName);
            Player currentPlayer = getCurrentPlayer();
            setShuttleFlightBehavior(currentPlayer);
            gameBoardController.handleShuttleFlight(currentPlayer, city);
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    private void setShuttleFlightBehavior(Player currentPlayer) {
        if (gameBoardController.canShuttleFlightToAnyCity(currentPlayer)) {
            gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorToAnyCity());
        } else {
            gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorNormal());
        }
    }

    public void handleBuildResearchStation() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setBuildResearchBehavior(currentPlayer);
        if (gameBoardController.canAddResearchStation()) {
            gameBoardController.handleBuildResearchStation(currentPlayer, currentCity);
        }
    }

    private void setBuildResearchBehavior(Player currentPlayer) {
        if (gameBoardController.canBuildResearchStationWithoutCard(currentPlayer)) {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationWithoutCard());
        } else {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationNormal());
        }
    }

    public void handleShareKnowledge(String playerName, boolean giveCard) {
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
            setShareKnowledgeBehavior(givingPlayer);
            gameBoardController.handleShareKnowledge(givingPlayer, receivingPlayer, giveCard);
        } catch (PlayerNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
    }

    private Player getPlayerByName(String playerName) throws PlayerNotFoundException {
        for(Player player : game.getPlayers()) {
            if(player.getPlayerName().equals(playerName)) {
                return player;
            }
        }

        throw new PlayerNotFoundException("Player with name: " + playerName + " does not exist");
    }

    private void setShareKnowledgeBehavior(Player givingPlayer) {
        if(gameBoardController.canShareAnyCard(givingPlayer)) {
            gameBoardController.setShareKnowledgeBehavior(new ShareKnowledgeOnAnyCityBehavior());
        } else {
            gameBoardController.setShareKnowledgeBehavior(new ShareKnowledgeOnSameCityBehavior());
        }
    }

    public void finishShareKnowledge(String chosenPlayerName, String cityName, boolean giveCard) {

        try {
            Player chosenPlayer = getPlayerByName(chosenPlayerName);
            City city = gameBoardController.getCity(cityName);
            PlayerCard chosenCard = chosenPlayer.getCardFromHandByCity(city);

            if(giveCard) {
                getCurrentPlayer().removeCardFromHand(chosenCard);
                chosenPlayer.addCardToHand(chosenCard);
            } else {
                chosenPlayer.removeCardFromHand(chosenCard);
                getCurrentPlayer().addCardToHand(chosenCard);
            }
        } catch (PlayerNotFoundException pnfe) {
            pnfe.printStackTrace();
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (CardNotFoundException cardnfe) {
            cardnfe.printStackTrace();
        }
    }

    public void handleTreatDisease() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setTreatDiseaseBehavior(currentPlayer, currentCity);
        gameBoardController.handleTreatDisease(currentPlayer, currentCity);
    }

    private void setTreatDiseaseBehavior(Player currentPlayer, City currentCity) {
        if (gameBoardController.canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubesWithoutActionDecrement());
        } else if (gameBoardController.canRemoveAllCubes(currentPlayer, currentCity)){
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubes());
        } else {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseOneCube());
        }
    }

    public void handleFindCure() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setFindCureBehavior(currentPlayer);
        gameBoardController.handleFindCure(currentPlayer, currentCity);
    }

    private void setFindCureBehavior(Player currentPLayer) {
        if(gameBoardController.canFindCureWithFourCards(currentPLayer)) {
            gameBoardController.setFindCureBehavior(new FindCureWithFourCardsBehavior());
        } else {
            gameBoardController.setFindCureBehavior(new FindCureWithFiveCardsBehavior());
        }
    }

    public void checkCardInHand(PlayerCard card, Player player) {
        playerController.checkCardInHand(card, player);
    }

    public void handleGiveCard(PlayerCard card, Player player1, Player player2) {
        playerController.removeCard(card, player1);
        playerController.addCard(card, player2);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public synchronized void updatePlayersInGame(DatabaseData data) {
        game.updatePlayers(data.getPlayers());
//        for (int i = 0; i < 4; i++) {
//            tryToUpdatePlayerInGame(data);
//        }
    }

//    private void tryToUpdatePlayerInGame(DatabaseData data, int i) {
//        Object object = snapshot.get("Player" + (i + 1));
//
//        if (object != null) {
//            updatePlayerInGame(object, i);
//        }
//
//    }

//    private void updatePlayerInGame(Object object, int i) {
//        String playerString = object.toString();
//        Player player = playerController.createPlayerFromDocData(playerString);
//        game.updatePlayer(i, player);
//    }

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
}
