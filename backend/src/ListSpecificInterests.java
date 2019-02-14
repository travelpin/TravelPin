import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import entity.Interest;
import entity.Interest.InterestBuilder;

public class ListSpecificInterests {
    public List<Interest> ListSpecificInterests(List<Interest> list, String category) throws JSONException {
        List<Interest> itemList = new ArrayList<>();
        Set<String> types = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            types = list.get(i).getCategories();
            if (types.contains(category)) {
                itemList.add(list.get(i));
            }
        }
        return itemList;
    }
}