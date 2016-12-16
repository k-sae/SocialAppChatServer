package ChatServer;

import FileManagment.FilesManager;
import SocialAppGeneral.Command;
import SocialAppGeneral.Message;
import SocialAppGeneral.SocialArrayList;

/**
 * Created by kemo on 13/12/2016.
 */
class ServerManager implements Files {
    static Command loadMessages(String id1, String id2)
    {
        String smallerId = getTheSmaller(id1, id2)
                , largerId = getTheLarger(id1, id2);
        SocialArrayList socialArrayList = new SocialArrayList(
                FilesManager.readAllLines(USERS_CHAT_DATA + smallerId + "-" + largerId));
        Command command = new Command();
        command.setKeyWord(Message.FETCH_MESSAGES);
        command.setSharableObject(socialArrayList);
        return command;
    }
    private static String getTheSmaller(String id1, String id2)
    {
        return Long.parseLong(id1) > Long.parseLong(id2) ? id2 : id1;
    }

    private static String getTheLarger(String id1, String id2)
    {
        return Long.parseLong(id1) < Long.parseLong(id2) ? id2 : id1;
    }
    private static synchronized void saveMessage(Message message)
    {
        String smallerId = getTheSmaller(message.getSender(), message.getReceiver()),
                largerId = getTheLarger(message.getSender(), message.getReceiver());

        FilesManager.AddLine(USERS_CHAT_DATA + smallerId + "-" + largerId, message.convertToJsonString());
    }
    static void processMessage(Message message)
    {
        ClientConnection.sendMessage(message);
        SecondaryConnection.sendNotification(message.getSender(), message.getReceiver());
        ServerManager.saveMessage(message);
        FilesManager.AddLine(USERS_HISTORY + message.getReceiver(), message.getSender());
    }
    static Command getHistory(String id)
    {
        Command command = new Command();
        command.setKeyWord(Message.FETCH_MESSAGES);
        command.setSharableObject
                (new SocialArrayList(
                        FilesManager.readAllLines(USERS_HISTORY + id)));
        return command;
    }
}
