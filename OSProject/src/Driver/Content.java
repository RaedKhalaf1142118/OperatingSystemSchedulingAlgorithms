package Driver;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Content extends BorderPane {
	private Stage window;
	private ProcessFactory processFactory;
	private ResultSet fcfsResultSet, rrResultSet, sjfResultSet, priorityResultSet, mlfbqResultSet;
	private ArrayList<ResultPair> fcfsResultPair, rrResultPair, sjfResultPair, priorityResultPair, mlfbqResultPair;

	public Content(Stage window) {
		this.window = window;
		this.processFactory = new ProcessFactory();
		fcfsResultSet = getFcfsResultSet();
		rrResultSet = getRoundRobinResultSet();
		sjfResultSet = getSjfResultSet();
		priorityResultSet = getPriorityResultSet();
		mlfbqResultSet = getMultiLFBQResultSet();
		craeteBackground();
		createContent();
	}

	private void craeteBackground() {
		Background background = new Background(new BackgroundFill(Color.LIGHTCYAN, null, null));
		setBackground(background);
	}

	private ResultSet getFcfsResultSet() {
		FCFS fcfs = new FCFS(processFactory);
		ResultSet resultSet = fcfs.getResultSet();
		this.fcfsResultPair = fcfs.getResults();
		return resultSet;
	}

	private ResultSet getSjfResultSet() {
		SJF sjf = new SJF(processFactory);
		ResultSet resultSet = sjf.getResultSet();
		this.sjfResultPair = sjf.getResults();
		return resultSet;
	}

	private ResultSet getPriorityResultSet() {
		Priority priority = new Priority(processFactory);
		ResultSet resultSet = priority.getResultSet();
		this.priorityResultPair = priority.getResults();
		return resultSet;
	}

	private ResultSet getRoundRobinResultSet() {
		RoundRobin robin = new RoundRobin(processFactory);
		ResultSet resultSet = robin.getResultSet();
		this.rrResultPair = robin.getResults();
		return resultSet;
	}

	private ResultSet getMultiLFBQResultSet() {
		MultiLevelQueue multiLevelQueue = new MultiLevelQueue(processFactory);
		ResultSet resultSet = multiLevelQueue.getResultSet();
		this.mlfbqResultPair = multiLevelQueue.getResults();
		return resultSet;
	}

	public Stage getWindow() {
		return window;
	}

	public void setWindow(Stage window) {
		this.window = window;
	}

	private void createContent() {
		promptUser();
	}

	private BorderPane viewRandom() {
		BorderPane root = new BorderPane();
		root.setRight(createTable("ATT"));
		root.setLeft(createTable("AWT"));
		return root;
	}

	private void promptUser() {
		StackPane random = createSelection("Generate Random");
		HBox hbox = new HBox(100, random);
		hbox.setAlignment(Pos.CENTER);

		random.setOnMouseClicked(e -> {
			random.setOnMouseClicked(null);
			BorderPane randomPane = viewRandom();
			randomPane.setOpacity(0);
			FadeTransition ft = new FadeTransition(Duration.seconds(1), this.getCenter());
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.play();
			ft.setOnFinished(e1 -> {
				setCenter(randomPane);
				FadeTransition ft2 = new FadeTransition(Duration.seconds(1), this.getCenter());
				ft2.setFromValue(0);
				ft2.setToValue(1);
				ft2.play();
			});
		});
		setCenter(hbox);
	}

	private StackPane createSelection(String title) {
		StackPane pane = new StackPane();
		Label titleLabel = new Label(title);
		titleLabel.setFont(Font.font("Comic Sans MS", 22));
		Rectangle rect = new Rectangle(200, 200, Color.LIGHTGREEN);
		rect.setStroke(null);
		pane.getChildren().addAll(rect, titleLabel);
		pane.setAlignment(Pos.CENTER);
		pane.setOnMouseEntered(e -> {
			FillTransition ft = new FillTransition(Duration.seconds(1.5), rect, (Color) rect.getFill(),
					Color.INDIANRED);
			ft.play();
			this.window.getScene().setCursor(Cursor.HAND);
		});
		pane.setOnMouseExited(e -> {
			FillTransition ft = new FillTransition(Duration.seconds(1.5), rect, (Color) rect.getFill(),
					Color.LIGHTGREEN);
			ft.play();
			this.window.getScene().setCursor(Cursor.DEFAULT);
		});
		return pane;
	}

	private BorderPane createTable(String type) {
		BorderPane rootPane = new BorderPane();
		GridPane root = new GridPane();

		/* Add Data To Table */

		root.add(createHeaderCell(""), 0, 0);
		root.add(createHeaderCell("100"), 1, 0);
		root.add(createHeaderCell("1000"), 2, 0);
		root.add(createHeaderCell("10000"), 3, 0);
		root.add(createHeaderCell("100000"), 4, 0);

		root.add(createHeaderCell("FCFS"), 0, 1);
		root.add(createHeaderCell("SJF"), 0, 2);
		root.add(createHeaderCell("Priority"), 0, 3);
		root.add(createHeaderCell("RR"), 0, 4);
		root.add(createHeaderCell("MLFQ"), 0, 5);

		ResultSet[] tempSets = { this.fcfsResultSet, this.sjfResultSet, this.priorityResultSet, this.rrResultSet,
				this.mlfbqResultSet };
		ArrayList<ArrayList<ResultPair>> tempPairs = new ArrayList<>();
		tempPairs.add(this.fcfsResultPair);
		tempPairs.add(this.sjfResultPair);
		tempPairs.add(this.priorityResultPair);
		tempPairs.add(this.rrResultPair);
		tempPairs.add(this.mlfbqResultPair);

		
		for (int i = 1; i < 6; i++) {
			ResultSet tempSet = tempSets[i - 1];
			int key = 100;
			for (int j = 1; j < 5; j++) {
				root.add(createNormalCell(tempSet.getData(type + key) + "", tempPairs.get(i-1), key,type), j, i);
				key *= 10;
			}
		}

		/* End-Add data */
		root.setAlignment(Pos.CENTER);

		rootPane.setPadding(new Insets(0, 100, 0, 100));
		rootPane.setCenter(root);

		rootPane.setTop(createHeaderTitle(type));

		return rootPane;
	}

	private StackPane createHeaderTitle(String title) {
		StackPane root = new StackPane();
		Label header = new Label(title);
		header.setFont(Font.font(30));
		root.getChildren().add(header);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100, 0, 0, 0));
		return root;
	}

	private StackPane createHeaderCell(String data) {
		StackPane stackPane = new StackPane();
		Rectangle rect = new Rectangle(100, 40, Color.INDIANRED);
		rect.setStroke(Color.BLACK);
		Label label = new Label(data);
		stackPane.getChildren().addAll(rect, label);
		stackPane.setAlignment(Pos.CENTER);
		stackPane.setOnMouseEntered(e -> {
			rect.setFill(Color.GHOSTWHITE);
		});

		stackPane.setOnMouseExited(e -> {
			rect.setFill(Color.INDIANRED);
		});
		return stackPane;
	}

	private StackPane createNormalCell(String data, ArrayList<ResultPair> resultPair, double times , String type) {
		StackPane pane = new StackPane();
		Rectangle rect = new Rectangle(100, 40, Color.LIGHTGREEN);
		rect.setStroke(Color.BLACK);
		Label label = new Label(data);
		pane.getChildren().addAll(rect, label);
		pane.setAlignment(Pos.CENTER);
		pane.setOnMouseEntered(e -> {
			rect.setFill(Color.GHOSTWHITE);
			this.window.getScene().setCursor(Cursor.HAND);
		});

		pane.setOnMouseExited(e -> {
			rect.setFill(Color.LIGHTGREEN);
			this.window.getScene().setCursor(Cursor.DEFAULT);
		});

		pane.setOnMouseClicked(e -> {
			displayAnalysis(resultPair, times , type);
		});
		return pane;
	}

	private void displayAnalysis(ArrayList<ResultPair> resultPair, double times , String type) {
		CustomAreaChart customerAreaChart = new CustomAreaChart(resultPair, times , type);
		setCenter(customerAreaChart);
	}

}
