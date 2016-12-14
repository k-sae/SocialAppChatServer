package SocialServer;

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
            setId();
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
    private void setId() {
        try {
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            Command command = Command.fromString( dataInputStream.readUTF());
            if (command != null)
            {
               loggedUserID = command.getObjectStr();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
