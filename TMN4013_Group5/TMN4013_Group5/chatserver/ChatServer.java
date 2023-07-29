/* HelloServer.java*/
import java.rmi.*;
import java.util.Scanner;
public class ChatServer { 
    public static void main(String args[]) { 
        try { 
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter server IP address: ");
            String serverIp = scanner.nextLine();
            System.out.print("Enter server port: ");
            String serverPort = scanner.nextLine();
            
            System.setProperty("java.rmi.server.hostname", serverIp);
            // Instantiating the implementation class 
            ImpleExample obj = new ImpleExample(); 
            Naming.rebind("rmi://"+serverIp+":"+serverPort+"/Chat",obj);
            System.err.println("Server is ready, waiting for client to connect..."); 
        } catch (Exception e) { 
            System.err.println("Server exception: " + e.toString()); 
        } 
    } 
} 