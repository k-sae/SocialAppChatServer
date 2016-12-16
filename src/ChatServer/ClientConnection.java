package ChatServer;

import SocialAppGeneral.Command;
import SocialAppGeneral.Message;
import SocialServer.GeneralServer;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by kemo on 11/12/2016.
 */
class ClientConnection extends GeneralServer
{
    private String receiverId;
    private static ArrayList<ClientConnection> clientConnections;
    ClientConnection(Socket clientSocket) {
        super(clientSocket);
        if (clientConnections==null)
        clientConnections = new ArrayList<ClientConnection>();
    }

    @Override
    public void startConnection() {
        receiverId = getId();
        ReceiveClientCommand receiveClientCommand = new ReceiveClientCommand(clientSocket){
            @Override
            public void onUserDisconnection() {
                clientConnections.remove(ClientConnection.this);
            }
        };
        receiveClientCommand.start();
        sendCommand(ServerManager.loadMessages(receiverId, getLoggedUserID()));
        //Send last Messages from here
        clientConnections.add(ClientConnection.this);
    }
    public static void sendMessage( Message message)
    {
        Command command = new Command();
        command.setKeyWord(Message.RECEIVE_MESSAGE);
        command.setSharableObject(message);
        new Thread(() -> clientConnections.stream().filter(clientConnection -> message.getReceiver().equals(clientConnection.getLoggedUserID()) && message.getSender().equals(clientConnection.receiverId)).forEach(clientConnection -> {
            clientConnection.sendCommand(command);
        })).start();
    }

}
