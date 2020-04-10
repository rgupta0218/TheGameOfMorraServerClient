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

	
	Button turnOnServer;
	HashMap<String, Scene> sceneMap;
	Server server;
	ListView<String> listItems;
	MorraInfo morraInfo ;
	//TextField tf;
	TextField portText;
	VBox whoWon ;
	boolean printedP1 = false;
	boolean printedP2 = false;
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		BorderPane pane = new BorderPane();
		///tf              = new TextField();
		
		turnOnServer    = new Button("Turn on Server");	
		portText        = new TextField("5555");
		sceneMap        = new HashMap<String, Scene>();
		listItems       = new ListView<String>();
		whoWon = new VBox();
		whoWon.setTranslateX(100); 
		
		portText.setPrefWidth(100);
		portText.setMaxWidth(100);
		turnOnServer.setMinHeight(50);
		turnOnServer.setMinWidth(100);

		
		//-fx-background-color: transparent;

		primaryStage.setTitle("(Server) Let's Play Morra!!!");
		
		
		this.turnOnServer.setOnAction(e->
		{
			primaryStage.setScene(sceneMap.get("server"));
			primaryStage.setTitle("This is the Server");
			listItems.getItems().add("Server is ON");
			server = new Server(data -> 
			{
				Platform.runLater(()->
				{
					//listItems.getItems().add(data.toString());
					if((server.morraInfo.getP1() == 1) && (printedP1 == false))
					{
						listItems.getItems().add("Player 1 joined ");
						printedP1 = true;
					}
					
					if ((server.morraInfo.getP2() == 3) && (printedP2 == false))
					{
						listItems.getItems().add("Player 2 joined ");
						printedP2 = true;
					}
					
					if(data.have2players == true)
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
							
							if(server.morraInfo.winner() == 1)
							{
								Label label = new Label("Player 1 WON");
							    label.setTextFill(Color.web("Blue", 1.0));
								label.setFont(Font.font("Times", FontWeight.BOLD,40)); 
								listItems.getItems().add("Player 1 WON");
								//whoWon.getChildren().add(label);
							}
							
							else if(server.morraInfo.winner() == 2)
							{
								Label label = new Label("Player 2 WON");
							    label.setTextFill(Color.web("Blue", 1.0));
								label.setFont(Font.font("Times", FontWeight.BOLD,20));
								listItems.getItems().add("Player 2 WON");
								//whoWon.getChildren().add(label);
							}
							
							else 
							{
								Label label = new Label("No one WON");
							    label.setTextFill(Color.web("Black", 1.0));
								label.setFont(Font.font("Times", FontWeight.BOLD,20));
								listItems.getItems().add("No one WON");
								//whoWon.getChildren().add(label);
							}
						} 
						
						else if((data.getp1Plays() != 0) && (data.getP1() == 1)) 
						{
							listItems.getItems().add("Player 1 played "+data.getp1Plays());
						}
					}
					//listItems.getItems().add("Current Hand Winner:  "+ data.evaluate());
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
		
		
		sceneMap.put("server", createServerGui());
		
		VBox voBox = new VBox(portText,turnOnServer);
		pane.setCenter(voBox);
		
		//for the back ground
		Image image = new Image("back.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.CENTER,bSize)));
		Scene scene = new Scene(pane,831,530);
		
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
		
		listItems.setStyle("-fx-background-color: Red;");
		listItems.setStyle("-fx-background-insets: 0 ;");
		
		listItems.setPrefSize(120, 100);
		
		
		pane.setLeft(listItems);
		//pane.setRight(tf);
		//pane.setLeft(evaluate);
		pane.setCenter(whoWon);
		return new Scene(pane, 600, 400);
		
	}	
}
