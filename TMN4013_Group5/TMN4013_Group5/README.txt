//Link to the demo video
https://youtu.be/O3c_uOS9hDE

//Listing of the included file names
1. chatclient folder
    Chat.java
    ChatClient.java
    Chat.class
    ChatClient.class
    README.txt
2. chatserver folder
    Chat.java
    ChatServer.java
    ImpleExample.java
    Chat.class
    ChatServer.class
    ImpleExample.class
    users.txt
3. README.txt

// Example command of Step to compile, execute and run this program. 
    Open new terminal (machine Server)
        cd into chatserver directory
            command: rmiregistry <port number>

    Open new terminal (machine Server)
        cd into chatclient directory
            command: javac *.java
            command: java ChatServer
                enter server <ip address>
                enter server <rmiregistry port number>
                enter username
                enter password
                enter operation
 
    Open new terminal (machine A)
        cd into chatclient directory
            command: javac *.java
            command: java ChatRoomClient
                enter server <ip address>
                enter server <rmiregistry port number>
                enter username
                enter password
                enter operation

    Open new terminal (machine B)
        cd into chatclient directory
            command: javac *.java
            command: java ChatRoomClient
                enter server <ip address>
                enter server <rmiregistry port number>
                enter username
                enter password
                enter operation

    Open new terminal (machine C)
        cd into chatclient directory
            command: javac *.java
            command: java ChatRoomClient
                enter server <ip address>
                enter server <rmiregistry port number>
                enter username
                enter password
                enter operation