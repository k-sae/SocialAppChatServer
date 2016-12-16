package ChatServer;

import FileManagment.FilesManager;
import SocialServer.ServerInitializer;

import javax.swing.*;
import java.net.Socket;

/**
 * Created by kemo on 11/12/2016.
 */
public class ChatServer extends JFrame {
//
    private ChatServer()
    {
        setSize(300,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Server");
        setVisible(true);
    }
    public static void main(String[] args)
    {
        final int startport = 6020;
        final int secondaryStartPort = 6030;
//        clientConnections = new ArrayList<ClientConnection>();
        initialize();
        new ChatServer();
        new Thread(() -> new ServerInitializer(secondaryStartPort) {
            @Override
            public void onClientConnection(Socket client) {
                new SecondaryConnection(client);
            }
        }).start();
        new ServerInitializer(startport) {
            @Override
            public void onClientConnection(Socket client) {
                new ClientConnection(client);
            }
        };
    }
    private static void initialize()
    {
        FilesManager.CreateFolder(Files.DATA);
       FilesManager.CreateFolder(Files.USERS_CHAT_DATA);
        FilesManager.CreateFolder(Files.USERS_HISTORY);
    }
}
