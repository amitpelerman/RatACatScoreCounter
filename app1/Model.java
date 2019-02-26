package app1;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.control.TextField;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Model implements Finals {
	/** is game finished. */
	private boolean gameFinished = false;
	private Game game;
	final URL resource = getClass().getResource("winning_sound.mp3");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	private Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/** start new game */
	public void setNewGame(ArrayList<String> arrayPlayersName) {
		for (int i = 0; i < arrayPlayersName.size(); i++) {
			Player player = new Player((String) arrayPlayersName.get(i));
			game.addToPlayersList(player);
		}
		game.setStartPlayer(game.getActivePlayerList().get(0));
	}

	/** remove player from game */
	public void removePlayer(Player player) {
		game.removeFromActivePlayerList(player);
	}

	/**
	 * after typing points -get points of round from user -calc new points -check
	 * limit + remove player + check if game finished
	 **/
	public void finishRound(TextField[] pointsAddToPlayer) {
		calcPoints(pointsAddToPlayer);
		game.increseRoundNumber();
		if (checkIfPlayerLost()) {
			this.controller.playerLost("active");
			if (checkIfGameFinished()) {
				/** game has been finish!, offer new game */
				mediaPlayer.play();
				controller.setNewGame1();
				mediaPlayer.stop();
			}
		}
	}

	/** check if game finished */
	private boolean checkIfGameFinished() {
		if (game.getNumOfActivePlayers() < 2) {
			setGameFinished(true);
			return true;
		}
		return false;
	}

	/** calculate points (after round finished) */
	public void calcPoints(TextField[] pointsAddToPlayer) {
		for (int j = 0; j < game.getActivePlayerList().size(); j++) {
			int newPoints = Integer.parseInt(pointsAddToPlayer[j].getText());
			Player player1 = game.getActivePlayerList().get(j);
			if (newPoints != 0) {
				player1.setPoints(player1.getPoints() + newPoints);
			} else {
				player1.setWins(player1.getWins() + 1);
			}
			player1.setWinRate(getWinRate(player1));
		}
	}

	/** check if some player lost */
	public boolean checkIfPlayerLost() {
		int points;
		boolean isRemoved = false;
		int size = game.getActivePlayerList().size();
		for (int i = 0; i < size; i++) {
			Player player = game.getActivePlayerList().get(i);
			points = player.getPoints();
			if (points >= game.getPointsLimit()) {
				removePlayer(player);
				i--;
				size = game.getActivePlayerList().size();
				isRemoved = true;
			}
		}
		return isRemoved;
	}

	/** announce winner */
	public String announceWinner() {
		Player winner = game.getActivePlayerList().get(0);
		String str = "The winner: " + winner.getName() + " with " + winner.getPoints() + " points";
		return str;
	}

	/** get is game finished */
	public boolean getIsGameFinished() {
		return this.gameFinished;
	}

	/** set game finished */
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	protected int getWinRate(Player player) {
		return (player.getWins() * 100 / game.getRoundNumber());
	}

	/** announce loser */
	public void zeroPoints() {
		for (Player player : game.getPlayersList()) {
			player.setPoints(0);
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return this.game;
	}

	public void updateStartPlayer(boolean isNext) {
		int indexOfActiveList = game.getActivePlayerList().indexOf(game.getStartPlayer());
		int index = 0;
		if (isNext) {
			if (indexOfActiveList == game.getNumOfActivePlayers() - 1) {
				index = 0;
			} else {
				index = indexOfActiveList + 1;
			}
		} else {
			if (indexOfActiveList == 0) {
				index = game.getNumOfActivePlayers() - 1;
			} else {
				index = indexOfActiveList - 1;
			}
		}
		game.setStartPlayer(game.getActivePlayerList().get(index));
	}

	/** clears statics for all players in both list(active & regular) */
	void clearPlayersLastRanks() {
		ArrayList<Player> players = game.getPlayersList();
		game.getActivePlayerList().removeAll(players);
		for (Player player : players) {
			player.setPoints(0);
			player.setWinRate(0);
			player.setWins(0);
		}
		game.getActivePlayerList().addAll(game.getPlayersList());
	}
}