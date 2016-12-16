package SocialAppGeneral;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kemo on 04/12/2016.
 */
public class SocialArrayList implements Shareable {   // update it latter
    private List<String> items;
    public SocialArrayList(List<String> items)
    {
        this.items = items;
    }
    public SocialArrayList()
    {
        items = new ArrayList<>();
    }

    public String convertToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static SocialArrayList fromJsonString(String jsonStr)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, SocialArrayList.class);
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
