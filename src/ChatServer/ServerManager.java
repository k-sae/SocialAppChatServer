package ChatServer;

import SocialAppGeneral.SocialArrayList;

/**
 * Created by kemo on 13/12/2016.
 */
class ServerManager implements Files {
    static String fetchMessages(String userId)
    {
       SocialArrayList socialArrayList = new SocialArrayList();
//        socialArrayList.getItems().addAll(FilesManager.readAllLines(USERS_HISTORY));
        socialArrayList.getItems().add("4");
       return socialArrayList.convertToJsonString();
    }

}
