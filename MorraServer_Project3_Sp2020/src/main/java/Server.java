import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;


public class Server
{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	
	TheServer server;
	private Consumer<MorraInfo> callback;
	MorraInfo morraInfo;
	int totalClients;
	
	
	Server(Consumer<MorraInfo> call){
	
		callback = call;
		morraInfo = new MorraInfo();
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);)
			{
			    System.out.println("Server is waiting for a client!"); 
			    
			    while(true) 
			    {
			        threadCheck();
					ClientThread c = new ClientThread(mysocket.accept(), count);
					clients.add(c);
					morraInfo.clientHolder.add(clients.size());
					
					if(count == 1)
					{
						morraInfo.setP1(1);
					}
					else if(count == 2)
					{
						morraInfo.setP2(3);
						morraInfo.have2players= true;
					}
					
					c.start();

					
					count++;
				}
			}//end of try
				catch(Exception e) {}
			}//end of while
		}
	
	public void threadCheck()
	{
		for(int i = 0; i<clients.size(); i++) 
		{
			ClientThread t = clients.get(i); 

			System.out.println("Client #: " + t.count+ " is " + 
								t.getState() + " and " + t.isAlive());
		}
	}

	
	/*****************************/
		class ClientThread extends Thread
		{
		
			Socket connection;
			int count =1;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			
			ClientThread(Socket s, int clientCounter){
				this.connection = s;
				this.count = clientCounter;	
			}
			
			public void updateClients(MorraInfo morraInfo) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try 
					{
						t.out.writeObject(morraInfo);
					}
					catch(Exception e) {}
				}
			}
			 
			
			public void updateConnection(MorraInfo morraInfo)
			{
//				int i=0;
//				while(i!=count)
//				{
//					i++;
//				}
				morraInfo.clientHolder.add(1);
			}
			
			
			void update2Players(MorraInfo game)
			{
				 try
				 {
					clients.get(game.getP1()).out.writeObject(game);
					clients.get(game.getP2()).out.writeObject(game);
					//MorraInfo rest = new MorraInfo();
					//rest.clientHolder.addAll(game.clientHolder);
					//updateClients(rest);
					
				 }
				 catch(Exception e) 
				 {
					 
				 }
				
			}
			
			public void run( ) 
			{
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
					
					updateConnection(morraInfo);
					updateClients(morraInfo);
					
				}
				catch(Exception e) {
					System.out.println("Streams not open clientCounter:" + count);
				}
				
					
				//play only if there are 2 clients available 
				
				 while(true) 
				 { 
						    try 
						    {
						    	MorraInfo morraInfoClient = (MorraInfo)in.readObject();
						    	morraInfo = morraInfoClient;
						    	callback.accept(morraInfo);
						   
	
						     	//update2Players(morraInfo); 
						     	updateClients(morraInfo); 
						    	connection.setTcpNoDelay(true); 
						    }
						    
						    catch(Exception e) 
						    {
						    	
						    	System.out.println("Client has left");
						    	morraInfo.have2players = false;
						    	updateClients(morraInfo); 
						    	clients.remove(this);
						    	break;
						    }

					}
				 
				}//end of run
			
		}//end of client thread
		
}