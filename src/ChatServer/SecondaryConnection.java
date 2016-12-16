package ChatServer;

import SocialAppGeneral.Command;
import SocialAppGeneral.Message;
import SocialServer.GeneralServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by kemo on 13/12/2016.
 */
class SecondaryConnection extends GeneralServer {
     private static ArrayList<SecondaryConnection> secondaryConnections;
    SecondaryConnection(Socket clientSocket) {
        super(clientSocket);
        if (secondaryConnections ==null)
        secondaryConnections = new ArrayList<SecondaryConnection>();

    }

    @Override
    public void startConnection() {
        secondaryConnections.add(this);
        new Thread(this::sendChatHistory).start();

    }
    public void sendCommand(Command command)
    {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendChatHistory()
    {

        sendCommand(ServerManager.getHistory(getLoggedUserID()));
    }
    static void sendNotification(String senderId, String ReceiverId)
    {
        Command command  = new Command();
        command.setKeyWord(Message.NEW_NOTIFICATION);
        new Thread(() -> {
            for (SecondaryConnection secondaryConnection: secondaryConnections
                    ) {
                if (secondaryConnection.getLoggedUserID().equals(ReceiverId))
                {
                    command.setSharableObject(senderId);
                    secondaryConnection.sendCommand(command);
                }
            }
        }).start();

    }
}
