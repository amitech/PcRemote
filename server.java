# Java file to be executed on remote pc


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import javax.swing.JOptionPane;

public class server {
 
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message,db;
 
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4444); // Server socket
 
		} catch (IOException e) {
		//	System.out.println("Could not listen on port: 4444");
		}
 
		//System.out.println("Server started. Listening to the port 4444");
 
		while (true) {
			try {
 
				clientSocket = serverSocket.accept(); // accept the client connection
				inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader); // get the client message
				message = bufferedReader.readLine();
				PrintWriter writer = new PrintWriter("test.bat", "UTF-8");
				//System.out.println(message);
				if(message==null)
				message="cls";
				else if(message.substring(0, 1).equals("?"))
				{
				db =message.substring(1);
				JOptionPane.showMessageDialog(null,db);
				message = "cls";
				}
				else if(message.equals("up"))
				{
				writer.println("/*");
				writer.println("cscript /e:jscript \"%~f0\" */");
				message = "var shl = new ActiveXObject(\"WScript.Shell\");for (var i=0; i<2; i++) {shl.SendKeys(String.fromCharCode(0xAF));}";
				}
				else if(message.equals("down"))
				{
				writer.println("/*");
				writer.println("cscript /e:jscript \"%~f0\" */");
				message = "var shl = new ActiveXObject(\"WScript.Shell\");for (var i=0; i<2; i++) {shl.SendKeys(String.fromCharCode(0xAE));}";
				}
				
				writer.println(message);
				writer.close();
				try{
					Process p = Runtime.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler test.bat");
					p.waitFor();
					} catch(Exception e){
			//		System.out.println("operation is failed.");
					}
		
				try {
					Thread.sleep(1000);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}

				
				try{
					File file = new File("test.bat");
					if(file.delete()){
				//		System.out.println(file.getName() + " is deleted!");
					}else{
					//		System.out.println("Delete operation is failed.");
						}
					}catch(Exception e){
							e.printStackTrace();
					}
				
				inputStreamReader.close();
				clientSocket.close();
 
			} catch (IOException ex) {
			//	System.out.println("Problem in message reading");
			}
		}
 
	}
 
}
