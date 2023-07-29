/* HelloClient.java*/
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ChatClient { 

    private ChatClient() {} 

    public static void anime(){
        String[] spinner = {"|", "/", "-", "\\"};
        int i = 0;
        long startTime = System.currentTimeMillis();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (true) {
            System.out.print("Loading" + spinner[i] + "\r");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("Server exception: " + e.toString());
            }
            i = (i + 1) % spinner.length;
            if (System.currentTimeMillis() - startTime >= 350) {
                break;
            }
        }
        System.out.print("\033[H\033[2J"); //is used to clear the console by printing an escape sequence
        System.out.flush(); //is used to ensure that the console is cleared immediately, by flushing the output buffer
    }
    public static void main(String[] args) { 
        String username;
        //List<String> directMessages;
        List<String> directMessages = new ArrayList<String>();
        
        try { 
            anime();
            System.out.println("\033[1;32m*****************************************");
            System.out.println("*                                       *");
            System.out.println("*         Welcome to Chat Room          *");
            System.out.println("*                                       *");
            System.out.println("*****************************************\033[0m\n");
            // Looking up the registry for the remote object
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter server IP address: ");
            String serverIp = scanner.nextLine();
            System.out.print("Enter server port: ");
            String serverPort = scanner.nextLine();
            Chat stub = (Chat) Naming.lookup("rmi://"+serverIp+":"+serverPort+"/Chat");
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            stub.com_join(username, password);

            // Calling the remote method using the obtained object 
            while (true) {
                anime();
                System.out.println("Hi, " + username);
                System.out.println("\nPress \033[34mP\033[0m to send a public message,");
                System.out.println("Press \033[34mD\033[0m to send a direct message,");
                System.out.println("Press \033[34mEnter\033[0m to view received messages,");
                System.out.println("Press \033[34mQ\033[0m to quit");
                System.out.print("\nPlease select operation: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("P")) {
                    anime();
                    System.out.println("Hi, " + username);
                    System.out.println("\n\033[34mBroadcast message\033[0m operation selected.");
                    int numberofOnlineUsers= stub.com_onlineUsers().size();
                    System.out.println("Number of online users: "+numberofOnlineUsers);
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    System.out.println("You are sending broadcast message to \033[33m"+numberofOnlineUsers+"\033[0m users. \nPress enter to \033[1;32mconfirm\033[0m or any other key to \033[31mcancel\033[0m.");
                    String confirmP = scanner.nextLine();
                    anime();
                    if(confirmP.equals("")){
                        stub.dat_broadcast(username, message);
                        System.out.println("\033[34mBroadcast message sent\033[0m, press any key to continue...\n");
                        System.console().readLine();
                    }else{
                        System.out.println("\033[31mBroadcast message not sent\033[0m, press any key to continue...\n");
                        System.console().readLine();
                    }
                } else if (input.equalsIgnoreCase("D")) {
                    int numberofOnlineUsers= stub.com_onlineUsers().size();
                    anime();
                    System.out.println("Hi, " + username);
                    System.out.println("\033[34mDirect message\033[0m operation selected.");
                    System.out.println("Number of online users: "+numberofOnlineUsers);
                    System.out.println("Online Users: ");
                    for (String username2 : stub.com_onlineUsers()) {
                        System.out.println(username2);
                    }
                    System.out.print("Select username: ");
                    String targetUsername = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    System.out.print("You are sending direct message to user: \033[33m"+targetUsername+"\033[0m. \nPress enter to \033[1;32mconfirm\033[0m or any other key to \033[31mcancel\033[0m.");
                    String confirmD = scanner.nextLine();
                    anime();
                    if(confirmD.equals("")){
                        stub.dat_direct(username, targetUsername, message);
                        System.out.println("\033[34mDirect message sent\033[0m, press any key to continue...\n");
                        System.console().readLine();
                    }else{
                        System.out.println("\033[31mDirect message not sent\033[0m, press any key to continue...\n");
                        System.console().readLine();
                    }
                } else if (input.equalsIgnoreCase("")){
                    if(directMessages!=null){
                        directMessages.addAll(stub.com_getDirectMessages(username));
                        for (String message : directMessages) {
                            System.out.println(message);
                        }
                        directMessages.clear();
                        System.out.println("Press any key to continue...");
                        System.console().readLine();
                    }
                } else if (input.equalsIgnoreCase("Q")) {
                    stub.com_quit(username);
                    anime();
                    System.out.println("Logout successful");
                    System.out.println("Program developed by: \033[1;32mGroup5_71680_72713_69847_69860_69385\033[0m");
                    System.out.println("Press any key to exit program...");
                    System.in.read();
                    anime();
                    break;
                } else {
                    System.out.println("Invalid input, press any key to continue");
                    System.console().readLine();
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); 
        } 
    } 
}