package app1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.event.Event;

@SuppressWarnings("rawtypes")
public class Controller implements javafx.event.EventHandler, Finals {
	private Model model;
	private View view;
	private int part;
	private ArrayList<String> playerNameList = new ArrayList<String>();
	private RandomAccessFile raf;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.part = 1;
		try {
			raf = new RandomAccessFile(FILE_NAME, FILE_MODE);
		} catch (IOException ex) {
			System.out.println(ex);
			System.exit(0);
		}
	}

	public Model getModel() {
		return model;
	}

	protected void updateView() {
		model.getGame().setPointsLimit(Integer.parseInt(DefaultPointsLimit));
		view.updateView(this.getPart());
	}

	protected void calcPushed() {
		this.writeLastRound(0);
		model.finishRound(view.getPointsList());
		model.updateStartPlayer(true);
		view.updateNextPlayerStart();
		view.setDetailsAfterRound();
		view.zeroPointsToAdd();
	}

	protected void setNewGame1() {
		view.restartGame();
	}

	protected void finishGame() {
		model.zeroPoints();
		this.playerLost("");
		model.clearPlayersLastRanks();
		model.getGame().zeroRoundNumber();
	}

	protected String announceWinner() {
		model.setGameFinished(true);
		return model.announceWinner();
	}

	protected void playerLost(String str) {
		view.clearTable();
		view.paintPlayers(str);
	}

	protected Game getGame() {
		return this.model.getGame();
	}

	protected void setNewGame(ArrayList<String> arrayPlayersName, int limit) {
		model.getGame().setPointsLimit(limit);
		this.model.setNewGame(arrayPlayersName);
	}

	protected int getWinRate(Player player) {
		return model.getWinRate(player);
	}

	public void setView(View view2) {
		this.view = view2;
	}

	/** handle clicking events from controller */
	@Override
	public void handle(Event e) {
		final Object source = e.getSource();
		if (this.getModel() == null) {
			return;
		}
		if (part == 1) {
			int numberOfPlayers = view.getNumberOfPlayers();
			/** user selected to change number of players */
			if (source.equals(view.getCbNumberOfPlayers())) {
				view.paintView2(numberOfPlayers);
			}
			/** user selected to start game */
			if (source.equals(view.getStartButton())) {
				if (view.checkNamesLength(numberOfPlayers)) {
					/** ( switch to view2- part2) */
					part = 2;
					for (int j = 0; j < numberOfPlayers; j++) {
						String name = new String(view.getPlayerName(j));
						playerNameList.add(name);
					}
					int limit = Integer.parseInt(textLimit.getText());
					this.setNewGame(playerNameList, limit);
					view.closeStage();
					View view2 = new View();
					this.setView(view2);
					view2.setController(this);
					view2.updateView(part);
				} else {
					view.setWarinig("name");
				}
			}
		} else if (part == 2) {
			/** user pushed calculator button */
			if (source.equals(view.getCalcPoints())) {
				/** check if all labels full */
				String str = view.pointsSetCorrect();
				if (str.equals("")) {
					view.setWarningUnvisible();
					this.calcPushed();
				} else {
					view.setWarinig(str);
				}
			} else if (source.equals(view.getBackButton())) {
				this.backPoints();
			}
		}
	}

	public RandomAccessFile getFile() {
		return raf;
	}

	public void writeLastRound(long position) {
		ArrayList<Player> players = this.getGame().getActivePlayerList();
		try {
			this.raf.setLength(0);
			FixedLengthStringIO.writeFixedLengthString(Integer.toString(this.getGame().getNumOfActivePlayers()), 1,
					getFile());
			position = raf.getFilePointer();
			for (int j = 0; j < players.size(); j++) {
				getFile().seek(position);
				FixedLengthStringIO.writeFixedLengthString(players.get(j).getName(), maxNameLength, getFile());
				FixedLengthStringIO.writeFixedLengthString(Integer.toString(players.get(j).getPoints()),
						DefaultPointsLimit.length(), getFile());
				FixedLengthStringIO.writeFixedLengthString(Integer.toString(players.get(j).getWins()), 3, getFile());
				FixedLengthStringIO.writeFixedLengthString(Integer.toString(players.get(j).getWinRate()), 3, getFile());
				position = raf.getFilePointer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readLastRound(long position) throws IOException {
		getFile().seek(position);
		Game game1 = this.getGame();
		boolean notLost = true;
		int numberOfPlayers = Integer.parseInt(FixedLengthStringIO.readFixedLengthString(1, getFile()));
		ArrayList<Player> aPlayers = game1.getActivePlayerList();
		/** if no player lost last round */
		if (numberOfPlayers != aPlayers.size()) {
			System.out.println("FALSE");
			notLost = false;
			this.view.clearTable();
			game1.clearActivePlayerList();
		}
		for (int i = 0; i < numberOfPlayers; i++) {
			String name = FixedLengthStringIO.readFixedLengthString(maxNameLength, getFile());
			String points = FixedLengthStringIO.readFixedLengthString(DefaultPointsLimit.length(), getFile());
			String wins = FixedLengthStringIO.readFixedLengthString(3, getFile());
			String winRate = FixedLengthStringIO.readFixedLengthString(3, getFile());
			if (!notLost) {
				Player player = new Player(name);
				player.setPoints(Integer.parseInt(points));
				player.setWins(Integer.parseInt(wins));
				player.setWinRate(Integer.parseInt(winRate));
				game1.addToActivePlayerList(player);
			}
			/** else: add it directly */
			else {
				aPlayers.get(i).setPoints(Integer.parseInt(points.trim()));
				aPlayers.get(i).setWins(Integer.parseInt(wins.trim()));
				aPlayers.get(i).setWinRate(Integer.parseInt(winRate.trim()));
				view.setPlayerWinsLabel(i, wins);
				view.setPlayerPointsLabel(i, points);
				view.setPlayerWinRateLabel(i, winRate);
			}
		}
		if (!notLost) {
			view.paintPlayers("active");
		} else {
			model.updateStartPlayer(false);
			view.updateNextPlayerStart();
		}
	}

	private void backPoints() {
		readFile();

	}

	public void readFile() {
		try {
			readLastRound(0);
			this.getGame().decreaseRoundNumber();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPart() {
		return this.part;
	}

	public void setPart(int i) {
		this.part = 1;
	}

	public void changePlayers() {
		this.setPart(1);
		this.view.closeStage();
		View view3 = new View();
		this.setView(view3);
		this.updateView();
	}
}
