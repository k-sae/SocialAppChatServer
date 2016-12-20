package SocialSecondaryServer;

import SocialAppGeneral.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by kemo on 11/12/2016.
 */
public abstract class GeneralServer implements Connection {
    protected Socket clientSocket;
    private String loggedUserID;
    public GeneralServer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        sendVerificationCode();
        new Thread(() -> {
            loggedUserID = getId();
            startConnection();
        }).start();

    }

    private void sendVerificationCode()
    {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.write(VERIFICATION.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLoggedUserID() {
        return loggedUserID;
    }
    protected String getId() {
        try {
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            return dataInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void sendCommand(Command command) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF(command.toString());
        } catch (
                IOException e)
        {
            //For debugging
            System.out.println("send Data\t" + e.getMessage());
        } catch (
                Exception e)
        {
            //TODO
            //Export to Log
            System.out.println("E: send Data\t" + e.getMessage());
        }

    }
}
