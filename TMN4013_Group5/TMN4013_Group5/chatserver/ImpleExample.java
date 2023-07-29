/* ImpleExample.java*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.*;
import java.rmi.server.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.*;

public class ImpleExample extends UnicastRemoteObject implements Chat {
    private Map<String, String> users; // username -> password
    private List<String> onlineUsers;
    private Map<String, List<String>> directMessages; // username -> list of messages
    
    public ImpleExample() throws RemoteException {
        super();
        users = new HashMap<>();
        onlineUsers = new ArrayList<>();
        directMessages = new HashMap<>();
    }

    public static void anime(){
        String[] spinner = {"|", "/", "-", "\\"};
        int i = 0;
        long startTime = System.currentTimeMillis();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (true) {
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
    }

    public void com_join(String username, String password) throws RemoteException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while((line = br.readLine()) != null) {
                String[] user = line.split(",");
                users.put(user[0],user[1]);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.toString()); 
        }

        if (users.containsKey(username)) {
            if (users.get(username).equals(password)) {
                // Add user to online users
                onlineUsers.add(username);
                System.out.println("User " + username + " logged in.");
            }
            else{
                throw new RemoteException("Invalid password for user: " + username);
            }
        } else {
            // Register new user
            users.put(username, password);
            try {
                FileOutputStream fos = new FileOutputStream("users.txt",true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                bw.write(username + "," + password);
                bw.newLine();
                bw.close();
                fos.close();
                System.out.println("User " + username + " registered.");
                // Add user to online users
                onlineUsers.add(username);
                System.out.println("User " + username + " logged in.");
            } catch (IOException e) {
                System.err.println("users.txt will be created, Error: " + e.toString()); 
                //e.printStackTrace();
            }
        }

        directMessages.put(username, new ArrayList<>());
    }
    
    public void com_quit(String username) throws RemoteException {
        // Remove user from online users
        onlineUsers.remove(username);
        System.out.println("User " + username + " logged out.");
    }

    public List<String> com_getDirectMessages(String username) throws RemoteException {
        return directMessages.get(username);
    }

    public void dat_broadcast(String username, String message) throws RemoteException {
        anime();
        for (String onlineUser : onlineUsers) {
            // if (!onlineUser.equals(username)) {
                directMessages.get(onlineUser).add("[" + LocalDate.now().atStartOfDay().toLocalDate() + "] [" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "] Broadcast message from " + username + ": " + message + System.lineSeparator());
                //directMessages.get(onlineUser).add(username + ": " + message);
            // }
        }
    }

    public void dat_direct(String username, String targetUsername, String message) throws RemoteException {
        anime();
        if (onlineUsers.contains(targetUsername)) {
            directMessages.get(targetUsername).add("[" + LocalDate.now().atStartOfDay().toLocalDate() + "] [" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "] Direct message from " + username + " to " + targetUsername + ": " + message + System.lineSeparator());
        } else {
            throw new RemoteException("Target user is not online: " + targetUsername);
        }
    }

    public List<String> com_onlineUsers() throws RemoteException {
        return onlineUsers;
    }
}
