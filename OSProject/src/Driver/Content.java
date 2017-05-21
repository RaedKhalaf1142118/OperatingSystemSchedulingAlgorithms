package Driver;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Content extends BorderPane {
	private Stage window;
	private ProcessFactory processFactory;
	private ResultSet fcfsResultSet, rrResultSet, sjfResultSet, priorityResultSet, mlfbqResultSet;

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
		return fcfs.getResultSet();
	}

	private ResultSet getSjfResultSet() {
		SJF sjf = new SJF(processFactory);
		return sjf.getResultSet();
	}

	private ResultSet getPriorityResultSet() {
		Priority priority = new Priority(processFactory);
		return priority.getResultSet();
	}

	private ResultSet getRoundRobinResultSet() {
		RoundRobin robin = new RoundRobin(processFactory);
		return robin.getResultSet();
	}

	private ResultSet getMultiLFBQResultSet() {
		MultiLevelQueue multiLevelQueue = new MultiLevelQueue(processFactory);
		return multiLevelQueue.getResultSet();
	}

	public Stage getWindow() {
		return window;
	}

	public void setWindow(Stage window) {
		this.window = window;
	}

	private void createContent() {
		setRight(createTable("ATT"));
		setLeft(createTable("AWT"));
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

		ResultSet[] tempSets = {this.fcfsResultSet,this.sjfResultSet,this.priorityResultSet,this.rrResultSet,this.mlfbqResultSet};
		
		for (int i = 1; i < 5; i++) {
			ResultSet tempSet = tempSets[i-1];
			int key = 100;
			for (int j = 1; j < 6; j++) {
				System.out.println(type+" "+key);
				root.add(createNormalCell(tempSet.getData(type + key)+""), i, j);
				key *= 10;
			}
		}

		/* End-Add data */
		root.setAlignment(Pos.CENTER);
		
		rootPane.setPadding(new Insets(0,100,0,100));
		rootPane.setCenter(root);
		
		rootPane.setTop(createHeaderTitle(type));
		
		return rootPane;
	}
	
	private StackPane createHeaderTitle(String title){
		StackPane root = new StackPane();
		Label header = new Label(title);
		header.setFont(Font.font(30));
		root.getChildren().add(header);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100,0,0,0));
		return root;
	}

	private StackPane createHeaderCell(String data) {
		StackPane stackPane = new StackPane();
		Rectangle rect = new Rectangle(100, 40, Color.INDIANRED);
		rect.setStroke(Color.BLACK);
		Label label = new Label(data);
		stackPane.getChildren().addAll(rect, label);
		stackPane.setAlignment(Pos.CENTER);
		return stackPane;
	}

	private StackPane createNormalCell(String data) {
		StackPane pane = new StackPane();
		Rectangle rect = new Rectangle(100, 40, Color.LIGHTGREEN);
		rect.setStroke(Color.BLACK);
		Label label = new Label(data);
		pane.getChildren().addAll(rect, label);
		pane.setAlignment(Pos.CENTER);
		return pane;
	}

}
