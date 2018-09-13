package Ex2;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CenterDisplay extends GridPane {
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
	
	private void buildPane() {		
		bandNameLabel = new Label("Band");
		bandNameLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(bandNameLabel, 0, 0);
		
		bandNameTxt = new TextField();
		GridPane.setConstraints(bandNameTxt, 0, 1);
		
		fansLabel = new Label("Fans");
		fansLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(fansLabel, 1, 0);
		
		fansTxt = new TextField();
		GridPane.setConstraints(fansTxt, 1, 1);
		
		formedLabel = new Label("Formed");
		formedLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(formedLabel, 2, 0);
		
		formedTxt = new TextField();
		GridPane.setConstraints(formedTxt, 2, 1);
		
		originLabel = new Label("Origin");
		originLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(originLabel, 3, 0);
		
		originTxt = new TextField();
		GridPane.setConstraints(originTxt, 3, 1);
		
		DidTheySplitLabel = new Label("Did they split");
		DidTheySplitLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(DidTheySplitLabel, 4, 0);
		
		DidTheySplitBox = new CheckBox();
		GridPane.setConstraints(DidTheySplitBox, 4, 1);
		
		styleLabel = new Label("Origin");
		styleLabel.setStyle("-fx-text-fill: RED");
		GridPane.setConstraints(styleLabel, 5, 0);
		
		styleTxt = new TextField();
		GridPane.setConstraints(styleTxt, 5, 1);
		
	}
	
//	import java.io.IOException;
//
//	import javafx.geometry.Insets;
//	import javafx.geometry.Pos;
//	import javafx.scene.control.Button;
//	import javafx.scene.control.Label;
//	import javafx.scene.image.ImageView;
//	import javafx.scene.layout.BorderPane;
//	import javafx.scene.layout.GridPane;
//	import javafx.scene.layout.HBox;
//	import javafx.scene.layout.StackPane;
//	import javafx.scene.layout.VBox;
//	import javafx.scene.paint.Color;
//	import javafx.scene.shape.Circle;
//
//	public class TeamDisplay extends BorderPane {
//		private FifaWorldCupFileManager fifa;
//
//		private Color tempPrmColor;
//		private Color tempSecColor;
//		private Team theTeam;
//
//		private Button leftButton;
//		private Button rightButton;
//		private ImageView countryImage;
//		private Label labelName;
//		private Circle firstColorCircle;
//		private Circle secondColorCircle;
//		private Label labelPrmColor;
//		private Label labelSecColor;
//		private String name;
//		private GridPane colorPane;
//
//		private HBox tempPane;
//
//		public TeamDisplay() {
//			try {
//				fifa = new FifaWorldCupFileManager(".\\teams.bin", ".\\games.bin");
//				theTeam = fifa.nextTeam();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//
//			buildPane();
//
//		}
//
//		private void buildPane() {
//
//			name = new String(theTeam.getCountryName());
//
//			leftButton = new Button("<");
//			previouseButtonAction();
//			rightButton = new Button(">");
//			nextButtonAction();
//			countryImage = new ImageView("file:.\\photos\\" + name + ".png");
//			labelName = new Label((name.toUpperCase()));
//			labelPrmColor = new Label("First Color");
//			labelSecColor = new Label("Second Color");
//			tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
//					theTeam.getPrimaryColor().getBlue());
//			tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
//					theTeam.getSecendryColor().getBlue());
//			firstColorCircle = new Circle(8, 8, 8);
//			secondColorCircle = new Circle(8, 8, 8);
//
//			firstColorCircle.setFill(tempPrmColor);
//			secondColorCircle.setFill(tempSecColor);
//
//			colorPane = new GridPane();
//			colorPane.add(labelPrmColor, 0, 0);
//			colorPane.add(labelSecColor, 0, 1);
//			colorPane.add(firstColorCircle, 1, 0);
//			colorPane.add(secondColorCircle, 1, 1);
//
//			tempPane = new HBox();
//			tempPane.getChildren().add(colorPane);
//
//			setStyle("-fx-background-color: blue");
//			setAlignment(leftButton, Pos.CENTER_LEFT);
//			setAlignment(rightButton, Pos.CENTER_RIGHT);
//			setAlignment(labelName, Pos.TOP_CENTER);
//			tempPane.setAlignment(Pos.CENTER);
//
//			setCenter(countryImage);
//			setTop(labelName);
//			setLeft(leftButton);
//			setRight(rightButton);
//			setBottom(tempPane);
//
//			setPadding(new Insets(15));
//
//		}
//
//		private void nextButtonAction() {
//
//			rightButton.setOnAction(e -> {
//				try {
//					theTeam = fifa.nextTeam();
//					name = new String(theTeam.getCountryName());
//					labelName.setText(name.toUpperCase());
//					countryImage = new ImageView("file:.\\photos\\" + name + ".png");
//					setCenter(countryImage);
//					tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
//							theTeam.getPrimaryColor().getBlue());
//					tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
//							theTeam.getSecendryColor().getBlue());
//					firstColorCircle.setFill(tempPrmColor);
//					secondColorCircle.setFill(tempSecColor);
//
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			});
//
//		}
//
//		private void previouseButtonAction() {
//
//			leftButton.setOnAction(e -> {
//				try {
//					theTeam = fifa.previousTeam();
//					name = new String(theTeam.getCountryName());
//					labelName.setText(name.toUpperCase());
//					countryImage = new ImageView("file:.\\photos\\" + name + ".png");
//					setCenter(countryImage);
//					tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
//							theTeam.getPrimaryColor().getBlue());
//					tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
//							theTeam.getSecendryColor().getBlue());
//					firstColorCircle.setFill(tempPrmColor);
//					secondColorCircle.setFill(tempSecColor);
//
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			});
//
//		}
//
//		public String getName() {
//			return theTeam.toString();
//		}
//
//	}


}
