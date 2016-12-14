package ChatServer;

import SocialServer.GeneralServer;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by kemo on 11/12/2016.
 */
class ClientConnection extends GeneralServer
{
    private static ArrayList<ClientConnection> clientConnections;
    ClientConnection(Socket clientSocket) {
        super(clientSocket);
        clientConnections = new ArrayList<ClientConnection>();
    }

    @Override
    public void startConnection() {
        new ReceiveClientCommand(clientSocket);
        clientConnections.add(ClientConnection.this);
    }


}
