/*
 * Het Banker NetID: hbanke2 
 * Ria Gupta NetID: rgupta40
 */

import javafx.scene.control.Label;
import java.io.IOException;
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

public class TheGameOfMorra extends Application 
{
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	int P1TotalWin = 0;
	int P2TotalWin = 0;
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
	VBox clientBox, whoWon, scoreBox, scoreBox2;
	Button playNextRoound;
	Button playNextGame;
	Button exit;
	boolean p1played = false;
	boolean p2played = false;
	boolean isPrinted = false;
	boolean isQuit = false;
	
	Label labelP1; 
	Label labelP2; 
	Label winnerLabelP1;
	Label winnerLabelP2;
	ColorAdjust colorAdjust;
	ColorAdjust colorAdjust1;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//The title of the scene
		primaryStage.setTitle("(Client) Let's Play Morra!!!");
		
		//initializing variables and widgets
		morraInfo = new MorraInfo();
		TurnOnClient = new Button("Connect to Server");
		port = new TextField("5555");  //text field to take in user port number 
		ipAdd = new TextField("127.0.0.1");  // text field to take in use ipaddress
		listItems = new ListView<String>(); // list view to show the contents of the game
		tf = new TextField();
		GuessButton = new Button("Guess"); 
		whoWon = new VBox();
		whoWon.setTranslateX(100); 
		
		//setting the size of the text fields 
		port.setPrefWidth(120);
		port.setMaxWidth(120);
		ipAdd.setPrefWidth(120);
		ipAdd.setMaxWidth(120);
		tf.setPrefWidth(120);
		tf.setMaxWidth(120);
		tf.setPromptText("Enter your guess"); 
		
		//color adjust for the finger images
        colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        
        colorAdjust1 = new ColorAdjust();
        colorAdjust1.setBrightness(0);
       
        move.setTranslateX(100); 
		
        //fetching the finger images from the folder  
		Image finger1 = new Image("one.png");
		Image finger2 = new Image("two.png");
		Image finger3 = new Image("three.png");
		Image finger4 = new Image("four.jpeg");
		Image finger5 = new Image("five.jpeg");
		
		//creating the image view to show them on GUI
		imageOne = new ImageView(finger1);		
		imageTwo = new ImageView(finger2);		
		imageThree = new ImageView(finger3);
		imageFour = new ImageView(finger4);
		imageFive = new ImageView(finger5);

		//button to play next round
		playNextRoound = new Button("Play next round");
		playNextRoound.setDisable(true);
		
		//button to play next game
		playNextGame = new Button("Play next game");
		playNextGame.setDisable(true);
		
		//button to exit the game 
		exit = new Button("Exit game");
		
		//labels to show the the score of both players
		winnerLabelP1 = new Label("Player1: " + P1TotalWin);
		winnerLabelP2 = new Label("Player2: " + P2TotalWin);
		
		scoreBox = new VBox();
		scoreBox2  = new VBox(winnerLabelP1,winnerLabelP2, scoreBox);  
	    
		//setting different color and  size to the labels
		winnerLabelP1.setText("Player1 points: " + P1TotalWin);
	    winnerLabelP1.setTextFill(Color.web("RED", 1.0));
	    winnerLabelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
		scoreBox.getChildren().addAll(winnerLabelP1);
		
		winnerLabelP2.setText("Player2 points: " + P2TotalWin);
	    winnerLabelP2.setTextFill(Color.web("CYAN", 1.0));
	    winnerLabelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
		scoreBox.getChildren().addAll(winnerLabelP2);
		
		//action when play next round is clicked
		playNextRoound.setOnAction(h->
		{
			myclear(); 
		});
		
		//action when play next game is clicked
		playNextGame.setOnAction(e->
		{
			myclear();
			winnerLabelP1.setText("Player1 points: 0");
			winnerLabelP1.setText("Player2 points: 0");
			primaryStage.show();
		});
		
		//action when exit button is clicked
		exit.setOnAction(e->
		{			
			try {
				client.morraInfo.have2players=false;
				listItems.getItems().add("Opponent has Quit");
				client.socketClient.close();
				
				primaryStage.close(); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Client closed");
			}
		});
		
		//button to turn on the client and connect it to the server
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
						//checking if there are two player in the game in order to start the game
						if(data.have2players == true )
						{
							//Notifying the players that they have an opponent ready t play
							if(isPrinted == false) 
							{
								listItems.getItems().add("Opponent has joined");
								isPrinted = true;
							}
							
							if( (data.getP1Guess() != 0) && 
								(data.getP2Guess() != 0) && 
								(p1played==false)        &&
								(data.getp2Plays() != 0) && 
								(data.getP2() == 3)      && 
								(data.getp1Plays() !=0)  && 
								(data.getP1() == 1))
							{
								//Printing the information taken in from the palyers
								listItems.getItems().add("Player 1 guessed "+ data.getP1Guess());
								listItems.getItems().add("Player 1 played "+data.getp1Plays());
								listItems.getItems().add("Player 2 guessed "+ data.getP2Guess());
								listItems.getItems().add("Player 2 played "+data.getp2Plays());
								p1played=true;
								
								playNextRoound.setDisable(false);
								
								//checking who picked which finger and displaying
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
								
								//checking who won the game/round and displaying it on the GUI
								if(data.winner() == 1)
								{
									listItems.getItems().add("Player 1 WON THIS ROUND");
									client.morraInfo.player1Winn.add(1);

									P1TotalWin++;
									
									winnerLabelP1.setText("Player1 points: " + P1TotalWin);
								    winnerLabelP1.setTextFill(Color.web("RED", 1.0));
								    winnerLabelP1.setFont(Font.font("Times", FontWeight.BOLD,25));
									try {
										 scoreBox.getChildren().add(winnerLabelP1); 
									} catch (Exception e2) {
										System.out.println("Two labes");
									}
								    

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
									
									winnerLabelP2.setText("Player2 points: " + P2TotalWin);
								    winnerLabelP2.setTextFill(Color.web("CYAN", 1.0));
								    winnerLabelP2.setFont(Font.font("Times", FontWeight.BOLD,25));
									try {
										 scoreBox.getChildren().add(winnerLabelP2); 
									} catch (Exception e2) {
										System.out.println("Two labes");
									}
									
								
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
						if((data.have2players == false)  && (isQuit==false))
						{
							listItems.getItems().add("Only one player...waiting for other player");
							isQuit = true;
						}
						
					}); 
				}); 
				
				client.start();
		});

		imageDisable();

		//action when guess button is clicked
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
			
			imageDisableFalse() ;
			
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
		
		//setting everything on the border pane
		BorderPane pane = new BorderPane(clientV);
		pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		Scene scene = new Scene(pane,831,530);
		primaryStage.setScene(scene); 
		primaryStage.show();
	}

	//function for setting size for the new image view
	public void imageViewFunc(ImageView imageView)
	{
		imageView.setFitHeight(100);
		imageView.setFitWidth(150);
		imageView.setPreserveRatio(true);
	}
	
	//function to disable the images
	public void imageDisable() 
	{
		imageOne.setDisable(true);
		imageTwo.setDisable(true);
		imageThree.setDisable(true);
		imageFour.setDisable(true);
		imageFive.setDisable(true);
	}
	
	//function to enable the images
	public void imageDisableFalse() 
	{
		imageOne.setDisable(false);
		imageTwo.setDisable(false);
		imageThree.setDisable(false);
		imageFour.setDisable(false);
		imageFive.setDisable(false);
	}
	
	//function to clear everything for the next game/round
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
	
	
	//creating new scene for the client gui
	public Scene createClientGui() 
	{
		fingersBox = new HBox(imageOne,imageTwo, imageThree,imageFour, imageFive);
		 
		clientBox = new VBox(10, tf,GuessButton,fingersBox,move,playNextRoound,playNextGame,exit);
		 
		listItems.setPrefSize(50, 100); 
		
		whoWon.setTranslateX(0);
		scoreBox.setTranslateX(-55);
		scoreBox.setTranslateY(-5);
		
		BorderPane pane1 = new BorderPane();
		pane1.setTop(clientBox);
		pane1.setBottom(listItems);
		pane1.setCenter(whoWon);
		pane1.setRight(scoreBox2); 
		
		
		Image image = new Image("back2.jpeg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane1.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		
		return new Scene(pane1, 600, 600);
		
	}
}