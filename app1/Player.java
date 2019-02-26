package app1;

public class Player {
	private String name;
	private int points;
	private int wins;
	private int winRate;

	public Player(String name) {
		this.name = name;
		this.points = 0;
		this.wins = 0;
		this.winRate = 0;
	}

	public int getWinRate() {
		return this.winRate;
	}

	public void setWinRate(int winRate) {
		this.winRate = winRate;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
