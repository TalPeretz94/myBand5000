package Ex2;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainGui extends Application {
	private BandsDataControllerImpl controller;
	private Band theBand;
	private Button leftBt;
	private Button rightBt;

	private BorderPane mainPane;
	private BorderPane centerPane;
	private BorderPane upPane;
	private HBox downPane;

	private Label bandNameLabel;
	private Label fansLabel;
	private Label formedLabel;
	private Label originLabel;
	private Label styleLabel;
	private Label DidTheySplitLabel;
	private CheckBox DidTheySplitBox;
	private TextField bandNameTxt;
	private TextField fansTxt;
	private TextField formedTxt;
	private TextField originTxt;
	private TextField styleTxt;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage window = primaryStage;
		controller = BandsDataControllerImpl.getInstance();
		theBand = controller.next();

		mainPane = new BorderPane();
		centerPane = new BorderPane();
		upPane = new BorderPane();
		downPane = new HBox();

		centerPane = centerPane();
		upPane = upPane();
		downPane = downPane();

		downPane.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(centerPane, Pos.CENTER);
		mainPane.setStyle("-fx-background-color: black");

		mainPane.setCenter(centerPane);
		mainPane.setTop(upPane);
		mainPane.setBottom(downPane);

		Scene scene = new Scene(mainPane, 700, 700);
		window.setScene(scene);

		KeyCombination keyCombinationWinUndo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			if (keyCombinationWinUndo.match(event)) {
				controller.undo();

				RefreshGUI(controller.getTheLastBand());

			}
		});

		KeyCombination keyCombinationWinSave = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			if (keyCombinationWinSave.match(event)) {
				try {
					controller.save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				RefreshGUI(controller.getTheLastBand());

			}
		});
		window.show();

	}

	public BorderPane centerPane() {
		BorderPane theBorderPane = new BorderPane();
		GridPane theGrid = new GridPane();

		theGrid.setPadding(new Insets(10, 10, 10, 10));
		theGrid.setVgap(20);
		theGrid.setHgap(10);

		bandNameLabel = new Label("Band");
		bandNameLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(bandNameLabel, 0, 0);

		bandNameTxt = new TextField(theBand.getName());
		GridPane.setConstraints(bandNameTxt, 1, 0);

		fansLabel = new Label("Fans");
		fansLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(fansLabel, 0, 1);

		fansTxt = new TextField(Integer.toString(theBand.getNumOfFans()));
		GridPane.setConstraints(fansTxt, 1, 1);

		formedLabel = new Label("Formed");
		formedLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(formedLabel, 0, 2);

		formedTxt = new TextField(Integer.toString(theBand.getFormedYear()));
		GridPane.setConstraints(formedTxt, 1, 2);

		originLabel = new Label("Origin");
		originLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(originLabel, 0, 3);

		originTxt = new TextField(theBand.getOrigin());
		GridPane.setConstraints(originTxt, 1, 3);

		DidTheySplitLabel = new Label("Did they split");
		DidTheySplitLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(DidTheySplitLabel, 0, 4);

		DidTheySplitBox = new CheckBox();
		DidTheySplitBox.setSelected(false);
		if (theBand.hasSplit()) {
			DidTheySplitBox.setSelected(true);
		}

		GridPane.setConstraints(DidTheySplitBox, 1, 4);

		styleLabel = new Label("Origin");
		styleLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(styleLabel, 0, 5);

		styleTxt = new TextField(theBand.getStyle());
		GridPane.setConstraints(styleTxt, 1, 5);

		theGrid.getChildren().addAll(bandNameLabel, fansLabel, formedLabel, originLabel, styleLabel, DidTheySplitLabel,
				DidTheySplitBox, bandNameTxt, fansTxt, formedTxt, originTxt, styleTxt);
		theGrid.setAlignment(Pos.CENTER);

		leftBt = new Button("<");
		previouseButtonAction();
		rightBt = new Button(">");
		nextButtonAction();
		BorderPane.setAlignment(leftBt, Pos.CENTER_LEFT);
		BorderPane.setAlignment(rightBt, Pos.CENTER_RIGHT);

		theBorderPane.setCenter(theGrid);
		theBorderPane.setLeft(leftBt);
		theBorderPane.setRight(rightBt);

		return theBorderPane;

	}

	private void nextButtonAction() {

		rightBt.setOnAction(e -> {
			theBand = controller.next();
			RefreshGUI(theBand);

		});

	}

	private void previouseButtonAction() {
		leftBt.setOnAction(e -> {
			theBand = controller.previous();
			RefreshGUI(theBand);

		});

	}

	private ComboBox<String> combo;

	public BorderPane upPane() {
		BorderPane theBorderPane = new BorderPane();
		combo = new ComboBox<>();
		comboAction();
		theBorderPane.setPadding(new Insets(10, 10, 10, 10));

		BorderPane.setMargin(combo, new Insets(15, 15, 15, 15));
		ClockPane clockAndDate = new ClockPane();
		combo.setPromptText("Sort by...");
		combo.getItems().addAll("Sort By Name", "Sort By Fans", "Sort By Origin");
		theBorderPane.setBottom(combo);

		theBorderPane.setTop(clockAndDate);

		BorderPane.setAlignment(clockAndDate, Pos.TOP_CENTER);
		BorderPane.setAlignment(combo, Pos.BOTTOM_CENTER);

		return theBorderPane;
	}

	public void comboAction() {
		combo.setOnAction(e -> {
			howToSort();
		});
	}

	public void howToSort() {
		String how = combo.getValue();
		if (how != null) {
			switch (how) {

			case "Sort By Name":
				SortByName();
				break;
			case "Sort By Origin":
				SortByOrign();
				break;

			case "Sort By Fans":
				SortByFans();
				break;

			}
			RefreshGUI(controller.getTheLastBand());
		}

	}

	public void SortByName() {

		controller.sort((o1, o2) -> {
			return (o1.getName().compareToIgnoreCase(o2.getName())) * -1;
		});
	}

	public void SortByOrign() {

		controller.sort((o1, o2) -> {
			return (o1.getOrigin().compareToIgnoreCase(o2.getOrigin())) * -1;
		});
	}

	public void SortByFans() {

		controller.sort((o1, o2) -> {

			return (((Integer) (o1.getNumOfFans())).compareTo((Integer) (o2.getNumOfFans())));
		});
	}

	private Button btSave;
	private Button btRemove;
	private Button btUndo;
	private Button btRevert;

	public HBox downPane() {
		HBox downMenu = new HBox();
		btSave = new Button("Save");
		btRemove = new Button("Remove Band");
		btUndo = new Button("Undo");
		btRevert = new Button("Revert");
		buttonsAction();
		downMenu.setPadding(new Insets(10, 10, 10, 10));
		downMenu.setSpacing(15);

		downMenu.getChildren().addAll(btSave, btRemove, btUndo, btRevert);
		return downMenu;
	}

	public void buttonsAction() {
		btSave.setOnAction(e -> {
			try {
				controller.save();
				try {
					controller.setBandArr(controller.getBandData().readAllBands());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RefreshGUI(controller.getTheLastBand());
		});
		btRemove.setOnAction(e -> {
			controller.remove();

			RefreshGUI(controller.getTheLastBand());

		});
		btUndo.setOnAction(e -> {
			controller.undo();
			if (!controller.getCommandsStackMem().isEmpty()) {
				RefreshGUI(controller.getTheLastBand());
			} else {
				System.out.println("asfasf");
			}

		});
		btRevert.setOnAction(e -> {
			controller.revert();
			theBand = controller.next();
			RefreshGUI(theBand);
		});

	}

	public void RefreshGUI(Band band) {

		if (band != null) {
			bandNameTxt.setText(band.getName());
			fansTxt.setText(Integer.toString(band.getNumOfFans()));
			formedTxt.setText(Integer.toString(band.getFormedYear()));
			originTxt.setText(band.getOrigin());
			if (band.hasSplit()) {
				DidTheySplitBox.setSelected(true);
			} else
				DidTheySplitBox.setSelected(false);
			styleTxt.setText(band.getStyle());

		}

	}

}

class ClockPane extends HBox {
	private final int DURATION = 100;
	private Format formatter;
	private String s;
	private final int START_POS = -200;
	private final int END_POS = 200;
	private Date date = new Date();

	private Timeline timelineClock;
	private Timeline timelineMove;

	// Text timeDisplay;
	private Text timeDisplay = new Text(getFormattedTime());

	public ClockPane() {
		setupTimeline();
		getChildren().add(timeDisplay);
		layoutSetup();
		this.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> stopAndPlay());
		this.addEventFilter(MouseEvent.MOUSE_EXITED, e -> stopAndPlay());
	}

	private void layoutSetup() {
		setAlignment(Pos.CENTER);
		timeDisplay.setFill(Color.RED);
		timeDisplay.setFont(Font.font(15));

	}

	private void setupTimeline() {
		timelineClock = new Timeline();
		timelineClock.setCycleCount(Timeline.INDEFINITE);
		timelineClock.setAutoReverse(true);

		timelineClock.getKeyFrames().add(new KeyFrame(Duration.millis(DURATION), event -> {
			timeDisplay.setText(getFormattedTime());
		}));

		KeyValue start = new KeyValue(translateXProperty(), START_POS);
		KeyValue end = new KeyValue(translateXProperty(), END_POS);

		KeyFrame startFrame = new KeyFrame(Duration.ZERO, start);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(5), end);

		timelineMove = new Timeline(startFrame, endFrame);
		timelineMove.setAutoReverse(true);
		timelineMove.setCycleCount(Timeline.INDEFINITE);
		timelineMove.play();

		timelineClock.setCycleCount(Animation.INDEFINITE);
		timelineClock.play();

	}

	private String getFormattedTime() {
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		s = formatter.format(date);

		return s + LocalTime.now().format(DateTimeFormatter.ofPattern(" HH:mm:ss")) + "  METAL HALL OF FAME";
	}

	public void stopAndPlay() {
		if (timelineMove.getStatus() == Animation.Status.PAUSED) {
			timelineMove.play();
		} else {
			timelineMove.pause();
		}

	}

}
