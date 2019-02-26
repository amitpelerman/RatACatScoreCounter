package app1;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface Finals {
	/** default number of players. */
	final int defaultPlayers = 3;
	/** set maximum players for game. */
	final int maxPlayers = 5;
	/** Label limit points for a game. */
	final Label labelLimit = new Label("Limit for a game");
	/** limit points for a game. */
	final String DefaultPointsLimit = "200"; // set by user, default = 200
	final TextField textLimit = new TextField(DefaultPointsLimit);
	final Label labelNumOfPlayers = new Label("Number of Players ");
	/** Size of window. */
	final int heightSize = 550;
	final int widthSize = 1250;
	/** Max points player can get per round. */
	final int maxPointsToAdd = 56;
	/** Max player's name length */
	final int maxNameLength = 12;
	/** Backgrounds */
	final String mainBg = "mainBackground.jpg";
	final String secBg = "secondBackground.jpg";
	final String menuBg = "4.jpg";
	/** Messages: */
	final String fullMsg = "Please set points to all the players";
	final String maxMsg = "The max points to add is: " + maxPointsToAdd;
	final String longNameMsg = "Max name's length is: " + maxNameLength;
	final String digitSettedMsg = "Please set numbers only";
	/** File to write last round: */
	final static String FILE_NAME = "address.dat";
	final static String FILE_MODE = "rw";

}
