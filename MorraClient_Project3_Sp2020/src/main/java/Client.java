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
	String ipadss;
	int portnum;
	private Consumer<MorraInfo> callback;
	
	Client(String ip, Integer Port, MorraInfo morra, Consumer<MorraInfo> call)
	{
		this.ipadss = ip;
		this.portnum = Port;
		this.morraInfo = morra;
		this.callback = call;
	}
	
	public void run() {
		
		try 
		{
			//socketClient= new Socket("127.0.0.1",5555);
			socketClient= new Socket(this.ipadss,this.portnum);
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		     
			 
			try {
				   this.morraInfo = (MorraInfo) in.readObject();
				   clientNumber=morraInfo.clientHolder.lastIndexOf(1);
				   
				   callback.accept(morraInfo);
			}
			catch(Exception e) {}
			
			send(morraInfo);
			
		}
		catch(Exception e) {}
			
			while(true)
			{
				try {
					MorraInfo morraInfo2 = (MorraInfo)in.readObject();
					this.morraInfo = morraInfo2;
					callback.accept(morraInfo2);
					
					
				} catch (Exception e) {}
			}
	
    }
	
	public void send(MorraInfo morraInfo) {  
		
		try {
			System.out.println("Inside client send");
			out.writeObject(morraInfo);
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
