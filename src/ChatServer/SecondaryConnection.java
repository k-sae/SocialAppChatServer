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
    public SecondaryConnection(Socket clientSocket) {
        super(clientSocket);
        secondaryConnections = new ArrayList<SecondaryConnection>();
    }

    @Override
    public void startConnection() {
        secondaryConnections.add(this);
        sendChatHistory();
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
        Command command = new Command();
        command.setKeyWord(Message.FETCH_MESSAGES);
        command.setSharableObject(ServerManager.fetchMessages(getLoggedUserID()));
        sendCommand(command);
    }
}
