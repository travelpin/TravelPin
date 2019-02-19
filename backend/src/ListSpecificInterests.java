import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import entity.Interest;

public class ListSpecificInterests {
    public List<Interest> ListSpecificInterests(List<Interest> list, String category) {
        List<Interest> itemList = new ArrayList<>();
        Set<String> types;
        for (int i = 0; i < list.size(); i++) {
            types = list.get(i).getCategories();
            if (types.contains(category)) {
                itemList.add(list.get(i));
            }
        }
        return itemList;
    }
}