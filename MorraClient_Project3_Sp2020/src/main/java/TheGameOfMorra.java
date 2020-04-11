import javafx.scene.control.Label;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;

public class TheGameOfMorra extends Application {

	int P1TotalWin = 0;
	int P2TotalWin = 0;
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
	
	ImageView imageOne1, imageTwo2, imageThree3, imageFour4, imageFive5; 
	//ImageView imageOne11,imageTwo22,imageThree33,image,imageTwo22 ;
	
	MorraInfo morraInfo;
	VBox move = new VBox();
	HBox fingersBox;
	VBox clientBox, whoWon, scoreBox;
	Button playNextRoound;
	Button playNextGame;
	Button exit;
	boolean p1played = false;
	boolean p2played = false;
	Label labelP1; 
	Label labelP2; 
//	Label winnerLabelP1;
//	Label winnerLabelP2;
	
	
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
		scoreBox = new VBox(); 
		
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
		
        
		Image finger1 = new Image("one.png");
		Image finger2 = new Image("two.png");
		Image finger3 = new Image("three.png");
		Image finger4 = new Image("four.jpeg");
		Image finger5 = new Image("five.jpeg");
		
		imageOne = new ImageView(finger1);		
		imageTwo = new ImageView(finger2);		
		imageThree = new ImageView(finger3);
		imageFour = new ImageView(finger4);
		imageFive = new ImageView(finger5);
		
//		imageOne1 = new ImageView(finger1);
//		imageTwo2 = new ImageView(finger2);
//		imageThree3 = new ImageView(finger3);
//		imageFour4 = new ImageView(finger4);
//		imageFive5 = new ImageView(finger5);
		
//		imageOne11 = new ImageView(finger1);
//		imageTwo22 = new ImageView(finger2);
//		imageThree33 = new ImageView(finger3);
//		imageFour44 = new ImageView(finger4);
//		imageFive55 = new ImageView(finger5);
		
//		eRatio(true);
		
		playNextRoound = new Button("Play next round");
		playNextRoound.setDisable(true);
		
		playNextGame = new Button("Play next game");
		playNextGame.setDisable(true);
		
		exit = new Button("Exit game");
		
		
		playNextRoound.setOnAction(h->
		{
			client.morraInfo.setp1Plays(0);
			client.morraInfo.setp2Plays(0);
			client.morraInfo.setP1Guess(0);
			client.morraInfo.setP2Guess(0);
			p1played=false;
			p2played=false;
			tf.clear();
			tf.setDisable(false);
			GuessButton.setDisable(false);
			listItems.getItems().clear();
			playNextRoound.setDisable(true);
			imageOne.setEffect(colorAdjust);
			imageTwo.setEffect(colorAdjust);
			imageThree.setEffect(colorAdjust);
			imageFour.setEffect(colorAdjust);
			imageFive.setEffect(colorAdjust);
			imageOne.setDisable(true);
			imageTwo.setDisable(true);
			imageThree.setDisable(true);
			imageFour.setDisable(true);
			imageFive.setDisable(true);
			labelP1.setText("");
			labelP2.setText("");
			labelP1.setGraphic(null);
			labelP2.setGraphic(null);
			
		});
		
		playNextGame.setOnAction(e->
		{
			myclear();
			primaryStage.show();
		});
		
		exit.setOnAction(e->
		{
			System.out.println("Client is closed");
			primaryStage.close();
		});
		
		
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
						if(data.have2players == true )
						{
							if( (data.getP1Guess() != 0) && 
								(data.getP2Guess() != 0) && 
								(p1played==false)        &&
								(data.getp2Plays() != 0) && 
								(data.getP2() == 3)      && 
								(data.getp1Plays() !=0)  && 
								(data.getP1() == 1))
							{
								
								listItems.getItems().add("Player 1 guessed "+ data.getP1Guess());
								listItems.getItems().add("Player 1 played "+data.getp1Plays());
								listItems.getItems().add("Player 2 guessed "+ data.getP2Guess());
								listItems.getItems().add("Player 2 played "+data.getp2Plays());
								p1played=true;
								
								playNextRoound.setDisable(false);
								
								if(data.getp1Plays() == 1)
								{
									ImageView imageView1 = new ImageView(finger1);
									imageViewFunc(imageView1); 
									labelP1 = new Label("<-- player 1 ",imageView1); 
									labelP1.setTextFill(Color.web("Red", 1.0));
									labelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP1);
								}
								
								else if(data.getp1Plays() == 2)
								{
									ImageView imageView2 = new ImageView(finger2);
									imageViewFunc(imageView2); 
								    labelP1 = new Label("<-- player 1  ",imageView2); 
									labelP1.setTextFill(Color.web("Red", 1.0));
									labelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP1);
								}
								
								else if(data.getp1Plays() == 3)
								{
									ImageView imageView3 = new ImageView(finger3);
									imageViewFunc(imageView3);
								    labelP1 = new Label("<-- player 1  ",imageView3); 
									labelP1.setTextFill(Color.web("Red", 1.0));
									labelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP1);
								}
								
								else if(data.getp1Plays() == 4)
								{
									ImageView imageView4 = new ImageView(finger4);
									imageViewFunc(imageView4);
								    labelP1 = new Label("<-- player 1  ",imageView4); 
									labelP1.setTextFill(Color.web("Red", 1.0));
									labelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP1);
								}
								
								else if(data.getp1Plays() == 5)
								{
									ImageView imageView5 = new ImageView(finger5);
									imageViewFunc(imageView5);
								    labelP1 = new Label("<-- player 1  ",imageView5); 
									labelP1.setTextFill(Color.web("Red", 1.0));
									labelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP1);
								}
								
								if(data.getp2Plays() == 1)
								{
									ImageView imageView1 = new ImageView(finger1);
									imageViewFunc(imageView1); 
								    labelP2 = new Label("<-- player 2 ",imageView1); 
									labelP2.setTextFill(Color.web("CYAN", 1.0));
									labelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP2);
								}
								
								else if(data.getp2Plays() == 2)
								{
									ImageView imageView2 = new ImageView(finger2);
									imageViewFunc(imageView2);
								    labelP2 = new Label("<-- player 2 ",imageView2); 
									labelP2.setTextFill(Color.web("CYAN", 1.0));
									labelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP2);
								}
								
								else if(data.getp2Plays() == 3)
								{
									ImageView imageView3 = new ImageView(finger3);
									imageViewFunc(imageView3);
								    labelP2 = new Label("<-- player 2 ",imageView3); 
									labelP2.setTextFill(Color.web("CYAN", 1.0));
									labelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP2);
								}
								
								else if(data.getp2Plays() == 4)
								{
									ImageView imageView4 = new ImageView(finger4);
									imageViewFunc(imageView4);
								    labelP2 = new Label("<-- player 2 ",imageView4); 
									labelP2.setTextFill(Color.web("CYAN", 1.0));
									labelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP2);
								}
								
								else if(data.getp2Plays() == 5)
								{
									ImageView imageView5 = new ImageView(finger5);
									imageViewFunc(imageView5);
								    labelP2 = new Label("<-- player 2 ",imageView5); 
									labelP2.setTextFill(Color.web("CYAN", 1.0));
									labelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									whoWon.getChildren().addAll(labelP2);
								}
								
								if(data.winner() == 1)
								{
									listItems.getItems().add("Player 1 WON THIS ROUND");
									client.morraInfo.player1Winn.add(1);
									System.out.println("arraySize P1: "+ client.morraInfo.player1Winn.size());
									System.out.println("arraySize P1 data: "+ data.player1Winn.size());
									P1TotalWin++;
//								    winnerLabelP1 = new Label("Player1: 1"); 
//								    winnerLabelP1.setTextFill(Color.web("RED", 1.0));
//								    winnerLabelP1.setFont(Font.font("Times", FontWeight.BOLD,15));
//									scoreBox.getChildren().addAll(winnerLabelP1);
//									
									
									if(P1TotalWin==2) 
									{
										listItems.getItems().add("Player 1 WON THE GAME");
										playNextRoound.setDisable(true); 
										playNextGame.setDisable(false);
									}
									
									GuessButton.setDisable(true);
									imageDisable() ;
								}
								
								else if(data.winner() == 2)
								{
									listItems.getItems().add("Player 2 WON THE ROUND");
									P2TotalWin++;
//								    winnerLabelP2 = new Label("Player2: 1"); 
//								    winnerLabelP2.setTextFill(Color.web("RED", 1.0));
//								    winnerLabelP2.setFont(Font.font("Times", FontWeight.BOLD,15));
//									scoreBox.getChildren().addAll(winnerLabelP2); 
									
									System.out.println("arraySize P2: "+ data.player2Winn.size());
									if(P2TotalWin ==2) 
									{
										listItems.getItems().add("Player 2 WON THE GAME");
										playNextRoound.setDisable(true); 
										playNextGame.setDisable(false);
									}
									GuessButton.setDisable(true);
									imageDisable() ;
								}
								
								else 
								{
									listItems.getItems().add("No one WON THIS ROUND");
									GuessButton.setDisable(true);
									imageDisable() ;
								}
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
	 		tf.setDisable(true);
	 		GuessButton.setDisable(true);
			
	 		client.send(client.morraInfo); 	

		});
		
		


		//finger 1
		imageViewFunc(imageOne);
//		imageOne.setFitHeight(100);
//		imageOne.setFitWidth(150);
//		imageOne.setPreserveRatio(true);
		imageOne.setDisable(true);
		imageOne.setEffect(colorAdjust);
		imageOne.setOnMouseClicked(e->
		{
			if(client.morraInfo.getP1() == client.clientNumber)
			{
				client.morraInfo.setp1Plays(1);
			}
			else if(client.morraInfo.getP2() == client.clientNumber)
			{
				client.morraInfo.setp2Plays(1);
			}

	 		client.send(client.morraInfo); 
	 		imageOne.setEffect(colorAdjust);

	 		
	 		imageDisable() ;
	 		tf.setDisable(true);
		}); 
		 
		
		//finger 2
		imageViewFunc(imageTwo);
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
	 		tf.setDisable(true);
	 
		});
		
		
		//finger 3
		imageViewFunc(imageThree);
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
	 		tf.setDisable(true);
		});
		
		
		//finger 4
		imageViewFunc(imageFour);
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
	 		tf.setDisable(true);
	 		
		});
		
		
		//finger 5
		imageViewFunc(imageFive);
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
	 		tf.setDisable(true);
	 		
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

	
	public void imageViewFunc(ImageView imageView)
	{
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);
		imageView.setPreserveRatio(true);
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
	
	public void myclear()
	{
		client.morraInfo.setp1Plays(0);
		client.morraInfo.setp2Plays(0);
		client.morraInfo.setP1Guess(0);
		client.morraInfo.setP2Guess(0);
		p1played=false;
		p2played=false;
		tf.clear();
		tf.setDisable(false);
		GuessButton.setDisable(false);
		listItems.getItems().clear();
		playNextRoound.setDisable(true);
		ColorAdjust colorAdjust = new ColorAdjust();
	    colorAdjust.setBrightness(-0.5);
		imageOne.setEffect(colorAdjust);
		imageTwo.setEffect(colorAdjust);
		imageThree.setEffect(colorAdjust);
		imageFour.setEffect(colorAdjust);
		imageFive.setEffect(colorAdjust);
		imageOne.setDisable(true);
		imageTwo.setDisable(true);
		imageThree.setDisable(true);
		imageFour.setDisable(true);
		imageFive.setDisable(true);
		labelP1.setText("");
		labelP2.setText("");
		labelP1.setGraphic(null);
		labelP2.setGraphic(null);
	}
	
	
	
	public Scene createClientGui() 
	{
		fingersBox = new HBox(imageOne,imageTwo, imageThree,imageFour, imageFive);
		 
		clientBox = new VBox(10, tf,GuessButton,fingersBox,move,playNextRoound,playNextGame,exit);
		
		
		listItems.setPrefSize(50, 100); 
		
		whoWon.setTranslateX(0);
		scoreBox.setTranslateX(-25);
		scoreBox.setTranslateY(-55);
		
		
		
		BorderPane pane1 = new BorderPane();
		pane1.setTop(clientBox);
		pane1.setBottom(listItems);
		pane1.setCenter(whoWon);
		pane1.setRight(scoreBox);
		
		
		Image image = new Image("back2.jpeg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane1.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		
		return new Scene(pane1, 600, 600);
		
	}
}