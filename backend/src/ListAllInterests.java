import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.DBConnection;
import db.DBConnectionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import entity.Interest;
import entity.Interest.InterestBuilder;

public class ListAllInterests {
//    private static final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.730610,-73.935242&radius=2000&type=museum";
//    private static final String DEFAULT_KEYWORD = ""; // no restriction

    public List<Interest> search(JSONObject obj) {
        List<Interest> allInterest = new ArrayList<>();
        DBConnection connection = DBConnectionFactory.getConnection();
        return allInterest;
    }

    private List<Interest> getItemList(JSONArray results) throws JSONException {
        List<Interest> itemList = new ArrayList<>();
        for (int i = 0; i < results.length(); ++i) {
            JSONObject result = results.getJSONObject(i);
            InterestBuilder builder = new InterestBuilder();
            if (!result.isNull("id")) {
                builder.setLocationId(result.getString("id"));
            }
            if (!result.isNull("name")) {
                builder.setName(result.getString("name"));
            }
            if (!result.isNull("place_id")) {
                builder.setPlaceId(result.getString("place_id"));
            }
            builder.setLat(getLat(result));
            builder.setLng(getLng(result));
            builder.setFormattedAddress(getFormattedAddress(result));
            builder.setCategories(getCategories(result));
            itemList.add(builder.build());
        }
        return itemList;
    }

    /**
     * Helper methods
     */
    private List<Interest> getItemListByCategory(List<Interest> list, String category) {
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

    private String getFormattedAddress(JSONObject result) throws JSONException {
        if (!result.isNull("vicinity")) {
            return result.getString("vicinity");
        }
        return "";
    }

    private double getLat(JSONObject result) throws JSONException {
        if (!result.isNull("geometry")) {
            JSONObject geometry = result.getJSONObject("geometry");
            if (!geometry.isNull("location")) {
                JSONObject location = geometry.getJSONObject("location");
                if (!location.isNull("lat")) {
                    return location.getDouble("lat");
                }
            }
        }
        return 0.0;
    }

    private double getLng(JSONObject result) throws JSONException {
        if (!result.isNull("geometry")) {
            JSONObject geometry = result.getJSONObject("geometry");
            if (!geometry.isNull("location")) {
                JSONObject location = geometry.getJSONObject("location");
                if (!location.isNull("lng")) {
                    return location.getDouble("lng");
                }
            }
        }
        return 0.0;
    }

    private Set<String> getCategories(JSONObject result) throws JSONException {
        Set<String> categories = new HashSet<>();
        if (!result.isNull("types")) {
            JSONArray types = result.getJSONArray("types");
            for (int i = 0; i < types.length(); i++) {
                categories.add(types.getString(i));
            }
        }
        return categories;
    }

//    private void queryAPI(double lat, double lon) {
//        List<Interest> results = search(lat, lon, null);
//        for (Interest result : results) {
//            System.out.println(result.toJSONObject());
//        }
//
//        System.out.println("=========================================================");
//        try {
//            List<Interest> byCategory = getItemListByCategory(results, "art_gallery");
//            for (Interest result : byCategory) {
//                System.out.println(result.toJSONObject());
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Main entry for sample GoogleMaps API requests.
//     */
//    public static void main(String[] args) {
//        GoogleMapsAPI tmApi = new GoogleMapsAPI();
//        // Mountain View, CA
//        // tmApi.queryAPI(37.38, -122.08);
//        // London, UK
//        // tmApi.queryAPI(51.503364, -0.12);
//        // Houston, TX
//        tmApi.queryAPI(29.682684, -95.295410);
//    }
}