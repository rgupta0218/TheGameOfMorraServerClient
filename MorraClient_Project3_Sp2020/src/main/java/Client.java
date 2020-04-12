/*
 * Het Banker NetID: hbanke2 
 * Ria Gupta NetID: rgupta40
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;


public class Client extends Thread{

	Socket socketClient;
	ObjectOutputStream out;
	ObjectInputStream in;
	MorraInfo morraInfo;
	int clientNumber = 0 ;
	String ipadss;	//string for the ip address
	int portnum;	//portnumber
	private Consumer<MorraInfo> callback;
	
	//constructor
	Client(String ip, Integer Port, MorraInfo morra, Consumer<MorraInfo> call)
	{
		this.ipadss = ip;
		this.portnum = Port;
		this.morraInfo = morra;
		this.callback = call;
	}
	
	//code given in the class
	public void run() {
		
		try 
		{
			socketClient= new Socket(this.ipadss,this.portnum);
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		     
		    
			try 
			{
				this.morraInfo = (MorraInfo) in.readObject();
				clientNumber=morraInfo.clientHolder.lastIndexOf(1);
				callback.accept(morraInfo);
			}
			catch(Exception e) 
			{
				
			}
			send(morraInfo);	//send the information
			
		}
		catch(Exception e) 
		{
			
		}
			
			while(true)
			{
				try 
				{
					MorraInfo morraInfo2 = (MorraInfo)in.readObject();
					this.morraInfo = morraInfo2;
					callback.accept(morraInfo2);					
				}
				catch (Exception e) 
				{
					
				}
			}
	
    }
	
	//send the information to server, type object
	public void send(MorraInfo morraInfo) 
	{  
		try 
		{	
			out.writeObject(morraInfo);
			out.reset();
		} 
		catch (IOException e) 
		{	
			System.out.println("Problem sending the information through the send function");
		}
	}


}
