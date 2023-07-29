/*Hello.java */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//Creating Remote interface for our application 
public interface Chat extends Remote { 
    public void com_join(String username, String password) throws RemoteException;
    public void com_quit(String username) throws RemoteException;
    public List<String> com_getDirectMessages(String username) throws RemoteException;
    public void dat_broadcast(String username, String message) throws RemoteException;
    public void dat_direct(String username, String targetUsername, String message) throws RemoteException;
    public List<String> com_onlineUsers() throws RemoteException;
}