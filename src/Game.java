import javax.swing.*;

import static java.lang.System.exit;

public class Game {

    static JFrame frame;
    static Player[] players;
    static Deck deck;
    static StartScreen startScreen;
    static GameScreen gameScreen;
    static GameState gameState;

    static int firstPlayer;
    static int proposingPlayer;
    static int activePlayer;
    static int lostPlayer;
    static Motive proposed = Motive.BACK;
    static Motive claim = Motive.BACK;

    static boolean playerAccepts;
    static boolean playerCorrect;
    static boolean playerNewClaim;

    static final int playerIndex = 3;

    public static void main(String[] args) throws Exception {
        Game g = new Game();
    }

    public Game() {
        setupGameStates();
        setupPlayers();
        setupJFrame();
    }

    public static void setupGameStates() {
        gameState = GameState.NEW_ROUND;
        deck = new Deck();
        firstPlayer = (int) (Math.random() * Constants.NUM_PLAYERS);
        proposingPlayer = firstPlayer;
        activePlayer = firstPlayer;

        if (activePlayer != playerIndex) {
            proposed = Helpers.getRandomMotive();
            if (Helpers.flipCoin()) claim = proposed;
            else claim = Helpers.getRandomMotive();
        }
    }

    public static void setupPlayers() {
        players = new Player[Constants.NUM_PLAYERS];
        for (int i = 0; i < Constants.NUM_PLAYERS; i++) {
            players[i] = new Player(Position.values()[i]);
            players[i].addToHand(deck.deal(deck.getSize() / Constants.NUM_PLAYERS));
        }
    }

    private static void setupJFrame() {
        frame = new JFrame("Cockroach Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        startScreen = new StartScreen();
        gameScreen = new GameScreen();
        frame.getContentPane().add(startScreen);
        frame.getContentPane().add(gameScreen);
        startScreen.setVisible(true);
        gameScreen.setVisible(false);
        updateGameScreen();

        frame.setVisible(true);
    }

    public static void updateGameText() {
        String text = "";
        switch (gameState) {
            case NEW_ROUND -> text = "Player " + proposingPlayer + " will start a new round.";
            case PLAYER_PROPOSE -> text = "Select a card \nfrom your hand.";
            case PLAYER_CLAIM -> text = "Select your claim.";
            case PLAYER_TURN -> {
                if (playerIndex == getPreviousPlayer(firstPlayer)) text = "Since you are the last player,\nyou must accept.";
                else text = "Would you like to accept or pass?";
            }
            case PLAYER_ACCEPT -> text = "Is the claim true or false?";
            case PLAYER_REVEAL -> {
                if (playerCorrect) text = "You were right!\nPlayer " + proposingPlayer + " will keep the card.";
                else text = "You were wrong.\nYou will keep the card.";
            }
            case PLAYER_PASS -> text = "Here is the card.\nWould you like to make a new claim?";
            case AI_PROPOSE -> text = "Player " + activePlayer + " has proposed a card.";
            case AI_TURN -> {
                if (playerAccepts) text = "Player " + activePlayer + " has accepted the card.";
                else text = "Player " + activePlayer + " has passed the card.";
            }
            case AI_ACCEPTS -> {
                if (playerCorrect) text = "Player " + activePlayer + " was right!\nPlayer " + proposingPlayer + " will keep the card.";
                else text = text = "Player " + activePlayer + " was wrong.\nPlayer " + activePlayer + " will keep the card.";
            }
            case END -> {
                text = "Player " + lostPlayer + " has lost.";
                if (lostPlayer == playerIndex) text += "\nSorry. Try again next time.";
                else text += "\nCongratulations! You won.";
            }
            default -> {
                System.out.println("ERROR: Game state switch statement reached default state.");
                exit(1);
            }
        }
        gameScreen.setText(text);
    }

    public static void updateGameState() {
        for (int i = 0; i < Constants.NUM_PLAYERS; i++) {
            if (players[i].hasLost()) {
                lostPlayer = i;
                gameState = GameState.END;
            }
        }

        switch(gameState) {
            case NEW_ROUND -> {
                firstPlayer = proposingPlayer;
                if (activePlayer == playerIndex) gameState = GameState.PLAYER_PROPOSE;
                else {
                    simulateAIPropose();
                    gameState = GameState.AI_PROPOSE;
                }
            }
            case PLAYER_PROPOSE -> {
                gameState = GameState.PLAYER_CLAIM;
                claim = Motive.BACK;
            }
            case PLAYER_CLAIM -> {
                gameState = GameState.AI_TURN;
                incrementActivePlayer();
                simulateAIChoices();
            }
            case PLAYER_TURN -> {
                if (playerAccepts) gameState = GameState.PLAYER_ACCEPT;
                else gameState = GameState.PLAYER_PASS;
            }
            case PLAYER_ACCEPT -> {
                gameState = GameState.PLAYER_REVEAL;
            }
            case PLAYER_REVEAL, AI_ACCEPTS -> {
                if (playerCorrect) {
                    players[proposingPlayer].collected.addCard(proposed);
                    activePlayer = proposingPlayer;
                }
                else {
                    players[activePlayer].collected.addCard(proposed);
                    proposingPlayer = activePlayer;
                }
                gameState = GameState.NEW_ROUND;
            }
            case PLAYER_PASS -> {
                if (playerNewClaim) gameState = GameState.PLAYER_CLAIM;
                else {
                    incrementActivePlayer();
                    simulateAIChoices();
                    gameState = GameState.AI_TURN;
                }
                proposingPlayer = playerIndex;
            }
            case AI_PROPOSE -> {
                incrementActivePlayer();
                if (activePlayer == playerIndex) gameState = GameState.PLAYER_TURN;
                else {
                    simulateAIChoices();
                    gameState = GameState.AI_TURN;
                }
            }
            case AI_TURN -> {
                if (playerAccepts) gameState = GameState.AI_ACCEPTS;
                else {
                    incrementActivePlayer();
                    if (activePlayer == playerIndex) gameState = GameState.PLAYER_TURN;
                    else {
                        simulateAIChoices();
                        gameState = GameState.AI_TURN;
                    }
                }
            }
            case END -> {
                System.out.println("The game has ended.");
            }
            default -> {
                System.out.println("ERROR: Game state switch statement reached default state.");
                exit(1);
            }
        }

        updateGameScreen();
    }

    private static void simulateAIChoices() {
        if (activePlayer == getPreviousPlayer(firstPlayer)) playerAccepts = true;
        else playerAccepts = Helpers.flipCoin();
        if (playerAccepts) {
            playerCorrect = Helpers.flipCoin();
        } else {
            proposingPlayer = activePlayer;
            playerNewClaim = Helpers.flipCoin();
            if (playerNewClaim) claim = Helpers.getRandomMotive();
        }
    }

    private static int getPreviousPlayer(int index) {
        if (index == 0) return Constants.NUM_PLAYERS - 1;
        return index - 1;
    }

    private static void simulateAIPropose() {
        proposed = players[proposingPlayer].hand.removeRandomCard();
        if (Helpers.flipCoin()) claim = proposed;
        else claim = Helpers.getRandomMotive();
    }

    public static void updateGameScreen() {
        updateGameText();
        updateGameCard();
        updateGameClaim();
        updateGameInputs();
    }

    private static void updateGameCard() {
        switch (gameState) {
            case NEW_ROUND, PLAYER_PROPOSE, END -> updateCard(false,true);
            case PLAYER_CLAIM, PLAYER_REVEAL, PLAYER_PASS, AI_ACCEPTS -> updateCard(true,false);
            case PLAYER_TURN, PLAYER_ACCEPT, AI_PROPOSE, AI_TURN -> updateCard(false,false);
            default -> {
                System.out.println("ERROR: Game state switch statement reached default state.");
                exit(1);
            }
        }
    }

    private static void updateGameInputs() {
        // new round, ai propose, and ai accepts do not require update
        switch(gameState) {
            case PLAYER_PROPOSE, END -> gameScreen.setNextVisible(false);
            case PLAYER_CLAIM -> {
                gameScreen.setYesNoVisible(false);
                gameScreen.setClaimSelectionVisible(true);
            }
            case PLAYER_TURN -> {
                gameScreen.setNextVisible(false);
                if (playerIndex == getPreviousPlayer(firstPlayer)) gameScreen.setAcceptVisible(true);
                else gameScreen.setAcceptPassVisible(true);
            }
            case PLAYER_ACCEPT -> {
                gameScreen.setAcceptPassVisible(false);
                gameScreen.setTrueFalseVisible(true);
            }
            case PLAYER_REVEAL -> {
                players[playerIndex].hand.addCard(proposed);
                gameScreen.setTrueFalseVisible(false);
                gameScreen.setNextVisible(true);
            }
            case PLAYER_PASS -> {
                gameScreen.setAcceptPassVisible(false);
                gameScreen.setYesNoVisible(true);
            }
            case AI_TURN -> {
                gameScreen.setYesNoVisible(false);
                gameScreen.setClaimSelectionVisible(false);
                gameScreen.setNextVisible(true);
            }
        }
    }

    public static void setPlayerAccepts(boolean playerAccepts) {
        Game.playerAccepts = playerAccepts;
    }

    public static void setPlayerCorrect(boolean playerCorrect) {
        Game.playerCorrect = playerCorrect;
    }

    public static void setPlayerNewClaim(boolean playerNewClaim) {
        Game.playerNewClaim = playerNewClaim;
    }

    private static void incrementActivePlayer() {
        activePlayer = ++activePlayer % Constants.NUM_PLAYERS;
    }

    private static void updateCard(boolean front, boolean grey) {
        if (front) gameScreen.displayCard(proposed, grey);
        else gameScreen.displayCard(Motive.BACK, grey);
    }

    private static void updateGameClaim() {
        if (claim.getString().equals("back") || gameScreen.card.getGrey()) gameScreen.setClaim("");
        else gameScreen.setClaim(claim.getString());
    }

}