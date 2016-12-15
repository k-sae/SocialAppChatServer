package ChatServer;

import SocialAppGeneral.Command;
import SocialAppGeneral.Message;
import SocialAppGeneral.SocialArrayList;
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
        new ReceiveClientCommand(clientSocket).start();
        System.out.println(receiverId+":"+getLoggedUserID());
        Command command = new Command();
        command.setKeyWord(Message.FETCH_MESSAGES);
        SocialArrayList socialArrayList = new SocialArrayList();
        Message message = new Message();message.setMessage("hello"); message.setSender("4");
        socialArrayList.getItems().add(message.convertToJsonString());
        command.setSharableObject(socialArrayList.convertToJsonString());
        sendCommand(command);
        //Send last Messages from here
        clientConnections.add(ClientConnection.this);
    }
    public static void sendMessage( Message message)
    {
        Command command = new Command();
        command.setKeyWord(Message.RECEIVE_MESSAGE);
        command.setSharableObject(message);
        new Thread(() -> {
//                clientConnections.stream()
//                .filter(clientConnection ->(
//                        message.getSender().equals(clientConnection.receiverId))
//                        &&message.getReceiver().equals(clientConnection.getLoggedUserID()))
//                .forEach(clientConnection -> clientConnection
//                        .sendCommand(command)))
//                .start();
            for (ClientConnection clientConnection : clientConnections
                    ) {
                if (message.getReceiver().equals(clientConnection.getLoggedUserID()) && message.getSender().equals(clientConnection.receiverId)) {
                    clientConnection.sendCommand(command);
                }
            }
        }).start();
    }

}
