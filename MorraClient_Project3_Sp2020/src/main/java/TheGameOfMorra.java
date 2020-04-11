
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;

public class TheGameOfMorra extends Application {

	public static void main(String[] args) 
	{
		launch(args);
	}

	Client client;
	int countclient = 0;
	HashMap<String, Scene> sceneMap;
	Scene startScene;
	ListView<String>  listItems;
	Button TurnOnClient, GuessButton, finger1,finger2,finger3,finger4,finger5;
	TextField tf, port, ipAdd;
	ImageView imageOne, imageTwo, imageThree, imageFour, imageFive; 
	MorraInfo morraInfo;
	VBox move = new VBox();
	HBox fingersBox;
	VBox clientBox, whoWon;

	
	
	
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("(Client) Let's Play Morra!!!");
		
		morraInfo = new MorraInfo();
		TurnOnClient = new Button("Connect to Server");
		port = new TextField("5555");
		ipAdd = new TextField("127.0.0.1"); 
		listItems = new ListView<String>();
		tf = new TextField();
		GuessButton = new Button("Guess"); 
		whoWon = new VBox();
		whoWon.setTranslateX(100); 
		  
		
		port.setPrefWidth(120);
		port.setMaxWidth(120);
		ipAdd.setPrefWidth(120);
		ipAdd.setMaxWidth(120);
		//listItems2.setPrefSize(10, 10);
		tf.setPrefWidth(120);
		tf.setMaxWidth(120);
		tf.setPromptText("Enter your guess"); 
		
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        
        ColorAdjust colorAdjust1 = new ColorAdjust();
        colorAdjust1.setBrightness(0);
        
        move.setTranslateX(100); 
		
        //imageDisable() ; 
        
        
		Image finger1 = new Image("one.png");
		imageOne = new ImageView(finger1);
        
		Image finger2 = new Image("two.png");
		imageTwo = new ImageView(finger2);

		Image finger3 = new Image("three.png");
		imageThree = new ImageView(finger3);
		
		Image finger4 = new Image("four.jpeg");
		imageFour = new ImageView(finger4);
		
		Image finger5 = new Image("five.jpeg");
		imageFive = new ImageView(finger5);
		
		TurnOnClient.setOnAction(e-> 
		{
				primaryStage.setScene(sceneMap.get("client"));
				primaryStage.setTitle("This is a client");
				String ipString = ipAdd.getText();
				int port1 = Integer.parseInt(port.getText()); 

				
				client = new Client(ipString, port1,morraInfo, data->
				{
					countclient++;
					
					Platform.runLater(()->
					{	
						if((data.have2players == true) && (data.winner() == 0 || data.winner() == 1 || data.winner() == 2))
						{
							if( (data.getP1Guess() !=0) && (data.getp1Plays() ==0 ))
							{
								
								listItems.getItems().add("Player 1 guessed "+ data.getP1Guess());
							}
							
							else if( (data.getP2Guess() !=0) && (data.getp2Plays() ==0 ))
							{
								listItems.getItems().add("Player 2 guessed "+ data.getP2Guess());
							}
							
							else if((data.getp2Plays() != 0) && (data.getP2() == 3))
							{
								listItems.getItems().add("Player 2 played "+data.getp2Plays());
								//int x = data.getp2Plays();
//								Label label1 = new Label();
//								label1.setPrefSize(20, 20);
//								label1.setGraphic(new ImageView(finger1));
								//whoWon.getChildren().add(label1);
								
//								if(data.winner() == 1)
//								{
////									Label label = new Label("Player 1 WON");
////								    label.setTextFill(Color.web("Blue", 1.0));
////									label.setFont(Font.font("Times", FontWeight.BOLD,40)); 
//									listItems.getItems().add("Player 1 WON");
//									data.player1Winn.add(1);
//									
//									if(data.player1Winn.size()==2) 
//									{
//										listItems.getItems().add("Player 1 WON THE GAME");
//									}
//									//whoWon.getChildren().add(label);
//									GuessButton.setDisable(false);
//									imageDisableFalse();
//								}
//								
//								else if(data.winner() == 2)
//								{
////									Label label = new Label("Player 2 WON");
////								    label.setTextFill(Color.web("Blue", 1.0));
////									label.setFont(Font.font("Times", FontWeight.BOLD,20));
//									listItems.getItems().add("Player 2 WON");
//									data.player2Winn.add(1);
//									
//									if(data.player2Winn.size()==2) 
//									{
//										listItems.getItems().add("Player 1 WON THE GAME");
//									}
//									GuessButton.setDisable(false);
//									imageDisableFalse();
//									//whoWon.getChildren().add(label);
//								}
//								
//								else 
//								{
////									Label label = new Label("No one WON");
////								    label.setTextFill(Color.web("Black", 1.0));
////									label.setFont(Font.font("Times", FontWeight.BOLD,20)); 
//									listItems.getItems().add("No one WON");
//									GuessButton.setDisable(false);
//									imageDisableFalse();
//									//whoWon.getChildren().add(label);
//								}
							} 
							
							else if((data.getp1Plays() != 0) && (data.getP1() == 1)) 
							{
								listItems.getItems().add("Player 1 played "+data.getp1Plays());
								//int x = data.getp2Plays();
//								Label label1 = new Label();
//								label1.setPrefSize(4, 4);
//								label1.setGraphic(new ImageView(finger1));
								//whoWon.getChildren().add(label1);
							}
						}
					}); 
				}); 
				client.start();
				

		});

		imageOne.setDisable(true);
		imageTwo.setDisable(true);
		imageThree.setDisable(true);
		imageFour.setDisable(true);
		imageFive.setDisable(true);
		

	
		GuessButton.setOnAction(e->
		{
			
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				int x = Integer.parseInt(tf.getText());
				
				client.morraInfo.setP1Guess(x);
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				int x = Integer.parseInt(tf.getText());
				
				client.morraInfo.setP2Guess(x);
			}
			
			imageOne.setDisable(false);
			imageTwo.setDisable(false);
			imageThree.setDisable(false);
			imageFour.setDisable(false);
			imageFive.setDisable(false);
			
			imageOne.setEffect(colorAdjust1);
			imageTwo.setEffect(colorAdjust1);
			imageThree.setEffect(colorAdjust1);
			imageFour.setEffect(colorAdjust1);
			imageFive.setEffect(colorAdjust1);
			
			
	 		client.send(client.morraInfo); 	
	 		
	 		GuessButton.setDisable(true);
		});
		
		


		//finger 1
		imageOne.setFitHeight(100);
		imageOne.setFitWidth(150);
		imageOne.setPreserveRatio(true);
		imageOne.setDisable(true);
		imageOne.setEffect(colorAdjust);
		imageOne.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				client.morraInfo.setp1Plays(1);
//				Label label = new Label("You played: " + client.morraInfo.getp1Plays());
//			    label.setTextFill(Color.web("Blue", 1.0));
//				label.setFont(Font.font("Times", FontWeight.BOLD,40)); 
//				move.getChildren().add(label);
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				client.morraInfo.setp2Plays(1);
//				Label label = new Label(client.morraInfo.getP2() - 1 +" played: " + client.morraInfo.getp2Plays());
//			    label.setTextFill(Color.web("Blue", 1.0));
//				label.setFont(Font.font("Times", FontWeight.BOLD,40)); 
//				move.getChildren().add(label);
			}
	 		client.send(client.morraInfo); 
	 		imageOne.setEffect(colorAdjust);
	 		imageDisable() ;
		}); 
		 
		
		//finger 2
		imageTwo.setFitHeight(100);
		imageTwo.setFitWidth(150);
		imageTwo.setPreserveRatio(true);
		imageTwo.setDisable(true);
		imageTwo.setEffect(colorAdjust);
		imageTwo.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				client.morraInfo.setp1Plays(2);
				
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				client.morraInfo.setp2Plays(2);
			}
	 		client.send(client.morraInfo); 
	 		imageTwo.setEffect(colorAdjust);
	 		imageDisable() ;
		});
		
		
		//finger 3
		imageThree.setFitHeight(100);
		imageThree.setFitWidth(150);
		imageThree.setPreserveRatio(true);
		imageThree.setDisable(true);
		imageThree.setEffect(colorAdjust);
		imageThree.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				client.morraInfo.setp1Plays(3);	
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{	
				client.morraInfo.setp2Plays(3);
			}
	 		client.send(client.morraInfo);	
	 		imageThree.setEffect(colorAdjust);
	 		imageDisable() ;
		});
		
		
		//finger 4
		imageFour.setFitHeight(100);
		imageFour.setFitWidth(150);
		imageFour.setPreserveRatio(true);
		imageFour.setDisable(true);
		imageFour.setEffect(colorAdjust);
		imageFour.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				client.morraInfo.setp1Plays(4);
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				client.morraInfo.setp2Plays(4);
			}
	 		client.send(client.morraInfo);
	 		imageFour.setEffect(colorAdjust);
	 		imageDisable() ;
		});
		
		
		//finger 5
		imageFive.setFitHeight(100);
		imageFive.setFitWidth(150);
		imageFive.setPreserveRatio(true);
		imageFive.setDisable(true);
		imageFive.setEffect(colorAdjust);
		imageFive.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{	
				client.morraInfo.setp1Plays(5);	
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				client.morraInfo.setp2Plays(5);
			}
	 		client.send(client.morraInfo);	
	 		imageFive.setEffect(colorAdjust); 
	 		imageDisable() ;
		});
		
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("client", createClientGui());
		
		//getting image for background scene 1
		Image image = new Image("back.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		VBox clientV = new VBox(port, ipAdd,TurnOnClient);
		
		BorderPane pane = new BorderPane(clientV);
		pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		Scene scene = new Scene(pane,831,530);
		primaryStage.setScene(scene); 
		primaryStage.show();
	}

	public void imageDisable() 
	{
		imageOne.setDisable(true);
		imageTwo.setDisable(true);
		imageThree.setDisable(true);
		imageFour.setDisable(true);
		imageFive.setDisable(true);
	}
	
	public void imageDisableFalse() 
	{
		imageOne.setDisable(false);
		imageTwo.setDisable(false);
		imageThree.setDisable(false);
		imageFour.setDisable(false);
		imageFive.setDisable(false);
	}
	
	
	
	public Scene createClientGui() 
	{
		fingersBox = new HBox(imageOne,imageTwo, imageThree,imageFour, imageFive);
		 
		clientBox = new VBox(10, tf,GuessButton,fingersBox,move);
		//clientBox.setStyle("-fx-background-color: blue");
		
		listItems.setPrefSize(50, 100); 
		//listItems.setBlendMode(BlendMode.ADD);
		BorderPane pane1 = new BorderPane();
		pane1.setTop(clientBox);
		pane1.setBottom(listItems);
		//pane1.setCenter(whoWon);
		//pane1.setBottom(move); 
		Image image = new Image("back2.jpeg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane1.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		
		return new Scene(pane1, 600, 600);
		
	}
}