package ChatServer;

import SocialAppGeneral.Command;
import SocialAppGeneral.Message;
import SocialAppGeneral.ReceiveCommand;

import java.net.Socket;

/**
 * Created by kemo on 12/12/2016.
 */
class ReceiveClientCommand extends ReceiveCommand {

    ReceiveClientCommand(Socket remote) {
        super(remote);
    }

    @Override
    public void Analyze(Command command) {
        if (command.getKeyWord().equals(Message.SEND_MESSAGE))
        {
            System.out.println("Message :)");
            ServerManager.processMessage( Message.FromJson(command.getObjectStr()));
        }
    }
}
