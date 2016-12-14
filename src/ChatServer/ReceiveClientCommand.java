package ChatServer;

import SocialAppGeneral.Command;
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

    }
}
