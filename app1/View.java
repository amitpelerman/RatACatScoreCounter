package app1;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends GridPane implements styleLabels, Finals {
	/** Title of who's starting */
	private Label nextPlayerStartLabel = new Label("Starting:");
	/** NAME of starting player */
	private Label nextPlayerStartName = new Label("");
	/** Warning for wrong input */
	private Label warningMsg = new Label("");
	/** Limit label */
	private Label limitLabel = new Label("  Limit");
	/** Show what is the limit */
	private Label limitText = new Label("");
	/** Button to press at end of round(to calculate points) */
	private Button calcPoints = new Button("Calc points");
	/** Back to last round's points */
	private Button backButton = new Button("back");
	/** How many points to add each player */
	private TextField pointsAddToPlayer[];
	/** Name of player (1st column) */
	private Label playerNameLabel[];
	/** Player's points (2nd column) */
	private Label playerPointsLabel[];

	/** Player's wins (3rd column) */
	private Label playerWinsLabel[];
	/** Player WinRate (4th column) */
	private Label playerWinRateLabel[];
	private Controller controller;
	private ComboBox<Object> cbNumberOfPlayers = new ComboBox<>(FXCollections.observableArrayList());
	private Image backG = new Image(getClass().getResourceAsStream(secBg));
	private Image backG2 = new Image(getClass().getResourceAsStream(menuBg));
	private Label labelPlayers[];
	private TextField textPlayers[];
	private VBox vbox = new VBox();
	private Button startButton = new Button("START PLAY");
	private Stage viewStage = new Stage();

	public View() {
		this.setHgap(6);
		this.setVgap(7);
		this.setPadding(new Insets(6, 6, 6, 6));
	}

	protected void setController(final Controller controller) {
		this.controller = controller;
	}

	@SuppressWarnings("unchecked")
	protected void updateView(int part) {
		if (part == 1) {
			Scene sceneView1 = new Scene(this, (widthSize / 2) + 130, heightSize / 2 + 60);
			viewStage.setScene(sceneView1);
			viewStage.setOnCloseRequest(e -> {
				e.consume();
			});
			viewStage.setResizable(false);
			setCb(5);
			setPadding(new Insets(3));
			setVgap(7);
			setHgap(2);
			labelNumOfPlayers.setStyle(titleStyle2);
			labelLimit.setStyle(titleStyle2);
			this.add(labelNumOfPlayers, 1, 0);
			this.add(cbNumberOfPlayers, 2, 0);
			this.add(labelLimit, 4, 0);
			this.add(textLimit, 5, 0);
			this.cbNumberOfPlayers.setOnAction(controller);
			startButton.setPrefSize(150, 100);
			startButton.setStyle(buttonsStyle);
			vbox.getChildren().add(startButton);
			this.add(vbox, 4, 7);
			BackgroundImage myBI = new BackgroundImage(backG2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
					BackgroundPosition.CENTER, null);
			this.setBackground(new Background(myBI));
			labelPlayers = new Label[defaultPlayers];
			textPlayers = new TextField[defaultPlayers];
			warningMsg.setStyle(warningStyle);
			this.add(warningMsg, 4, 4, 3, 3);
			warningMsg.setVisible(false);
			paintView2(defaultPlayers);
			viewStage.show();
			startButton.setOnAction(controller);
		} else {
			Scene sceneView2 = new Scene(this, widthSize - 10, heightSize - 30);
			viewStage.setScene(sceneView2);
			viewStage.setHeight(heightSize);
			viewStage.setWidth(widthSize);
			viewStage.setTitle(" round number " + controller.getGame().getRoundNumber());
			viewStage.setFullScreen(true);
			viewStage.show();
			this.paint();
			this.paintPlayers("");
		}
	}

	@SuppressWarnings("deprecation")
	public int getLimitText() {
		return (new Integer(textLimit.getText()).intValue());
	}

	/** paint view2 */
	void paintView2(int selectedNumber) {
		this.getChildren().removeAll(textPlayers);
		this.getChildren().removeAll(labelPlayers);
		labelPlayers = new Label[selectedNumber];
		textPlayers = new TextField[selectedNumber];
		for (int i = 0; i < selectedNumber; i++) {
			labelPlayers[i] = new Label("Player " + (int) (i + 1));// initializing labels
			this.add(labelPlayers[i], 1, 2 + i);
			textPlayers[i] = new TextField();
			this.add(textPlayers[i], 2, 2 + i);
			this.labelPlayers[i].setStyle(titleStyle2);
		}
	}

	public void setCb(int maxPlayers) {
		for (int i = 1; i < maxPlayers; i++) {
			cbNumberOfPlayers.getItems().add(i + 1);
		}
		cbNumberOfPlayers.getSelectionModel().select(1);
	}

	public TextField getTextLimit() {
		return textLimit;
	}

	public int getNumberOfPlayers() {
		int numberOfPlayers = (int) this.cbNumberOfPlayers.getValue();
		return numberOfPlayers;
	}

	public String getPlayerName(int j) {
		String names = textPlayers[j].getText().toString();
		return names;
	}

	// ----------------------------------- LAST VIEW ( paint the second window)
	public void paint() {
		/** TITLES(FIRST ROW) */
		Label titlePlayer = new Label(String.format(" %-10s", "Player"));
		Label titlePoints = new Label(String.format(" %-8s", "Points"));
		Label titleWins = new Label("Wins   ");
		Label titleWinRate = new Label("Win Rate    ");
		Label pointsAddToPlayerLabel = new Label("Add Points");
		this.add(limitLabel, 5, 0);
		limitLabel.setStyle(titleStyle);
		this.add(limitText, 5, 1);
		limitText.setStyle(titleStyle);
		titleWinRate.setStyle(titleStyle);
		this.add(titleWinRate, 3, 0);
		titlePlayer.setStyle(titleStyle);
		this.add(pointsAddToPlayerLabel, 4, 0);
		pointsAddToPlayerLabel.setStyle(titleStyle);
		this.add(titlePlayer, 0, 0);
		titlePoints.setStyle(titleStyle);
		this.add(titlePoints, 1, 0);
		titleWins.setStyle(titleStyle);
		this.add(titleWins, 2, 0);/** Finish to add and edit titles */
		BackgroundImage myBI = new BackgroundImage(backG, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, null);
		this.setBackground(new Background(myBI));

	}

	@SuppressWarnings("unchecked")
	public void paintPlayers(String listKind) {
		ArrayList<Player> p;
		if (listKind == "active") {
			p = this.controller.getGame().getActivePlayerList();
		} else {
			p = this.controller.getGame().getPlayersList();
		}
		int i = 0;
		pointsAddToPlayer = new TextField[p.size()];
		playerNameLabel = new Label[p.size()];
		playerPointsLabel = new Label[p.size()];
		playerWinsLabel = new Label[p.size()];
		playerWinRateLabel = new Label[p.size()];
		for (Player player : p) {
			playerNameLabel[i] = new Label(String.format(" %-15s", player.getName()));
			playerPointsLabel[i] = new Label("  " + String.valueOf(player.getPoints()));
			playerWinsLabel[i] = new Label(" " + String.valueOf(player.getWins()));
			playerWinRateLabel[i] = new Label(" " + String.valueOf(controller.getWinRate(player)) + "%");
			pointsAddToPlayer[i] = new TextField();
			playerNameLabel[i].setStyle(titleStyle);
			playerPointsLabel[i].setStyle(titleStyle);
			playerWinsLabel[i].setStyle(titleStyle);
			playerWinRateLabel[i].setStyle(titleStyle);
			pointsAddToPlayer[i].setStyle(namesStyle);
			this.add(playerNameLabel[i], 0, i + 1);
			this.add(playerPointsLabel[i], 1, i + 1);
			this.add(playerWinsLabel[i], 2, i + 1);
			this.add(playerWinRateLabel[i], 3, i + 1);
			this.add(pointsAddToPlayer[i], 4, i + 1);
			i += 1;
		}
		this.add(nextPlayerStartLabel, 0, i + 2);
		this.add(nextPlayerStartName, 1, i + 2);

		nextPlayerStartLabel.setStyle(buttonStyle);
		nextPlayerStartName.setStyle(buttonStyle);
		calcPoints.setStyle(buttonStyle);
		warningMsg.setStyle(warningStyle);
		backButton.setStyle(buttonStyle);
		this.add(backButton, 5, i + 2);
		int size = 3 + controller.getGame().getNumOfActivePlayers();
		this.add(warningMsg, 1, size, size, size);
		setWarningUnvisible();
		this.add(calcPoints, 4, i + 2);
		limitText.setText(String.valueOf("   " + controller.getGame().getPointsLimit()));
		nextPlayerStartName.setText(controller.getGame().getStartPlayer().getName());
		calcPoints.setOnAction(controller);
		backButton.setOnAction(controller);
	}

	public void clearTable() {
		this.getChildren().removeAll(playerNameLabel);
		this.getChildren().removeAll(playerPointsLabel);
		this.getChildren().removeAll(playerWinsLabel);
		this.getChildren().removeAll(playerWinRateLabel);
		this.getChildren().removeAll(nextPlayerStartLabel);
		this.getChildren().removeAll(nextPlayerStartName);
		this.getChildren().removeAll(pointsAddToPlayer);
		this.getChildren().remove(calcPoints);
		this.getChildren().remove(warningMsg);
		this.getChildren().remove(backButton);
	}

	public TextField[] getPointsList() {
		return pointsAddToPlayer;
	}

	protected String getPlayerLabel(int i) {
		return (String) playerNameLabel[i].getText();
	}

	protected void zeroPointsToAdd() {
		for (TextField label : pointsAddToPlayer) {
			label.setText("");
		}
	}

	protected void setDetailsAfterRound() {
		ArrayList<Player> players = controller.getGame().getActivePlayerList();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			playerNameLabel[i].setText(String.format(" %-15s", player.getName()));
			this.setPlayerPointsLabel(i, String.valueOf(player.getPoints()));
			this.setPlayerWinsLabel(i, String.valueOf(player.getWins()));
			this.setPlayerWinRateLabel(i, String.valueOf(player.getWinRate()));
		}
	}

	public void updateNextPlayerStart() {
		nextPlayerStartName.setText(controller.getGame().getStartPlayer().getName());
	}

	public void setPlayerPointsLabel(int index, String points) {
		this.playerPointsLabel[index].setText("  " + points);

	}

	public void setPlayerWinsLabel(int index, String wins) {
		this.playerWinsLabel[index].setText("  " + wins);
	}

	public void setPlayerWinRateLabel(int index, String winRate) {
		this.playerWinRateLabel[index].setText("  " + winRate.concat("%"));
	}

	protected void restartGame() {
		ButtonType buttonStartNew = new ButtonType("Start new game");
		ButtonType buttonChangePlayers = new ButtonType("Change players");
		ButtonType buttonExitGame = new ButtonType("Exit game");
		String msg = controller.announceWinner();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("GAME OVER");
		alert.setHeaderText(msg);
		alert.setContentText("Please select from the following options:");
		alert.getButtonTypes().setAll(buttonStartNew, buttonChangePlayers, buttonExitGame);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonStartNew) {
			controller.finishGame();
		} else if (result.get() == buttonChangePlayers) {
			controller.finishGame();
			// controller.changePlayers();
		} else if (result.get() == buttonExitGame) {
			System.exit(0);
		}
	}

	public Button getStartButton() {
		return startButton;
	}

	public Button getCalcPoints() {
		return calcPoints;
	}

	public ComboBox<Object> getCbNumberOfPlayers() {
		return cbNumberOfPlayers;
	}

	public void setWarinig(String str) {
		String msg = null;
		switch (str) {
		case "max":
			msg = maxMsg;
			break;
		case "notFull":
			msg = fullMsg;
			break;
		case "name":
			msg = longNameMsg;
			break;
		case "digit":
			msg = digitSettedMsg;
			break;
		}
		warningMsg.setText(msg);
		warningMsg.setVisible(true);
	}

	public void setWarningUnvisible() {
		warningMsg.setVisible(false);
	}

	public String pointsSetCorrect() {
		TextField[] list = this.getPointsList();
		for (int i = 0; i < list.length; i++) {
			String point = list[i].getText();
			try {
				Double.parseDouble(point);
			} catch (NumberFormatException nfe) {
				return "digit";
			}
			if (point.equals("")) {
				return "notFull";
			} else if (Integer.parseInt(point) > maxPointsToAdd) {
				return "max";
			}
		}
		return "";
	}

	public boolean checkNamesLength(int numberOfPlayers) {
		for (int j = 0; j < numberOfPlayers; j++) {
			if (getPlayerName(j).length() > maxNameLength) {
				return false;
			}
		}
		return true;
	}

	public Button getBackButton() {
		return backButton;
	}

	public Stage getStage() {
		return this.viewStage;
	}

	public void closeStage() {
		this.getStage().close();
	}
}
