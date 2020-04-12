/*
 * Het Banker NetID: hbanke2 
 * Ria Gupta NetID: rgupta40
 */

import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TheGameOfMorra extends Application {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	//keeping track of which player won which rounds
	int P1TotalWin = 0;
	int P2TotalWin = 0;
	Server server;
	MorraInfo morraInfo;	//instance of morraInfo
	Button turnOnServer;	//button to turn on the server
	HashMap<String, Scene> sceneMap;	//for the new scene 
	ListView<String> listItems;	//list view to print out the stuff in the server
	TextField portText;		//text field enter the port number
	VBox whoWon, vBoxRules;
	
	//flags to keep track of which player is which
	boolean printedP1 = false;
	boolean printedP2 = false;
	boolean p1Guessed = false;
	boolean p2Guessed = false;
	boolean p1Played  = false;
	boolean p2Played  = false;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		BorderPane pane = new BorderPane();
		turnOnServer    = new Button("Turn on Server");		//button to turn on the server
		portText        = new TextField("5555");			//border to enter the textField
		sceneMap        = new HashMap<String, Scene>();
		listItems       = new ListView<String>();	//list view to prin out the items
		whoWon = new VBox();
		whoWon.setTranslateX(100); 
		portText.setPrefWidth(100);
		portText.setMaxWidth(100);
		turnOnServer.setMinHeight(50);
		turnOnServer.setMinWidth(100);
		vBoxRules = new VBox();
		
		//title for the server gui
		primaryStage.setTitle("(Server) Let's Play Morra!!!");
		
		
		this.turnOnServer.setOnAction(e->
		{
			primaryStage.setScene(sceneMap.get("server"));
			primaryStage.setTitle("This is the Server");	//title
			listItems.getItems().add("Server is ON");		
			
			//print out the rules for the game in the server
			Label rules = new Label( );
			rules.setText("Rules: \n" +
						  "* Turn on 2 players first \n" +
						  "* Enter your guess number from 1-10 \n" +
						  "* Click on the finger image to play \n" +
						  "* Once the round is complete, press the play \n "
						  + " next round button for both players \n");
			
			rules.setTextFill(Color.web("GREENYELLOW", 1.0));
			rules.setFont(Font.font("Times", FontWeight.BOLD,20));
			vBoxRules.getChildren().addAll(rules);
					
			server = new Server(data -> 
			{
				Platform.runLater(()->
				{
					//check if the player 1 has joined
					if((server.morraInfo.getP1() == 1) && (printedP1 == false))
					{
						listItems.getItems().add("Player 1 joined ");
						listItems.getItems().add("Waiting for player 2.... ");
						printedP1 = true;
					}
					
					//check if the player 2 has joined
					if ((server.morraInfo.getP2() == 3) && (printedP2 == false))
					{
						listItems.getItems().add("Player 2 joined ");
						printedP2 = true;
					}
					
					//print out what each player played on the server
					if(data.have2players == true)
					{
						if((data.getP1Guess() !=0) && (data.getp1Plays()==0) && (p1Guessed==false)) 
						{
							listItems.getItems().add("Player 1 guessed "+ data.getP1Guess());
							p1Guessed=true;
						}
						
						else if((data.getp1Plays() != 0) && (data.getP1() == 1) && (p1Played==false)) 
						{
							listItems.getItems().add("Player 1 played "+data.getp1Plays());
							p1Played=true;
						}
					   
						else if( (data.getP2Guess() !=0) && (data.getp2Plays() ==0 ) && (p2Guessed==false))
						{
							listItems.getItems().add("Player 2 guessed "+ data.getP2Guess());
							p2Guessed=true;
						}
						
						else if((data.getp2Plays() != 0) && (data.getP2() == 3) && (p2Played==false))
						{
							listItems.getItems().add("Player 2 played "+data.getp2Plays());
							p2Played=true;
						} 

					}
				
					//check to see the winner of the game
					if((p1Guessed==true) && (p2Guessed==true) && (p1Played==true) && (p2Played==true))
					{
							//mark them as false
						 	p1Guessed=false;
						 	p2Guessed=false;
						 	p1Played=false;
						 	p2Played=false;
							
						 	//player 1 wins the round
						 	if(data.winner() == 1)
							{
								listItems.getItems().add("Player 1 WON THIS ROUND");	
								data.player1Winn.add(1);	//add 1 to the total winnings for player 2
								P1TotalWin++; 	//increment the players total winnings
								
								//player 1 wins the game
								if(P1TotalWin == 2) 
								{
									listItems.getItems().add("Player 1 WON THE GAME");
								}	
							}
							
						 	//player 2 wins the round
							else if(data.winner() == 2)
							{
								listItems.getItems().add("Player 2 WON THIS ROUND");
								data.player2Winn.add(1);	//add 1 the winnings for player 2
								
								//player 2 wins the game
								if(data.player2Winn.size()==2) 
								{
									listItems.getItems().add("Player 2 WON THE GAME");
								}	
							}
							
						 	//if no one wins
							else if(data.winner()==0)
							{
								listItems.getItems().add("No one WON THIS ROUND");		
							}
						}
					
				}); 

			});
		
		});
		
	
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
            @Override
            public void handle(WindowEvent t) 
            {
                Platform.exit();
                System.exit(0);
            }
        });
		
		//name of the second scene
		sceneMap.put("server", createServerGui());
		
		//vbox for the first scene
		VBox voBox = new VBox(portText,turnOnServer); 
		pane.setCenter(voBox);
		
		//for the background
		Image image = new Image("back.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		Scene scene = new Scene(pane,631,430);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene createServerGui( ) 
	{
		BorderPane pane = new BorderPane();
		
		//background for scene 2
		Image image = new Image("back22.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		
		
		listItems.setStyle("-fx-background-insets: 0 ;");
		listItems.setPrefSize(170, 100);
		
		pane.setLeft(listItems);
		pane.setRight(vBoxRules);
		pane.setCenter(whoWon);
		
		return new Scene(pane, 600, 400);
		
	}	
}