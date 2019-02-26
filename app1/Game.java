package app1;

import java.util.ArrayList;

public class Game implements Finals {
	/** array of ALL PLAYERS */
	private ArrayList<Player> playersList = new ArrayList<Player>();
	/** array only for ACTIVE PLAYERS */
	private ArrayList<Player> activePlayerList = new ArrayList<Player>();
	/** how many games played */
	private int roundNumber = 1;
	/** who is starting */
	private Player startPlayer;
	/** points limit per game */
	private int pointsLimit;

	public int getPointsLimit() {
		return pointsLimit;
	}

	public void setPointsLimit(int pointsLimit) {
		this.pointsLimit = pointsLimit;
	}

	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

	public void addToPlayersList(Player newPlayer) {
		this.playersList.add(newPlayer);
		addToActivePlayerList(newPlayer);
	}

	public ArrayList<Player> getActivePlayerList() {
		return activePlayerList;
	}

	public int getNumOfActivePlayers() {
		return activePlayerList.size();
	}

	public void addToActivePlayerList(Player newPlayer) {
		this.activePlayerList.add(newPlayer);
	}

	public void removeFromActivePlayerList(Player player) {
		activePlayerList.remove(player);
	}

	public void clearActivePlayerList() {
		activePlayerList.clear();
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void zeroRoundNumber() {
		this.roundNumber = 1;
	}

	public void increseRoundNumber() {
		this.roundNumber++;
	}

	public void decreaseRoundNumber() {
		this.roundNumber--;
	}

	public Player getStartPlayer() {
		return startPlayer;
	}

	public void setStartPlayer(Player startPlayer) {
		this.startPlayer = startPlayer;
	}

}
