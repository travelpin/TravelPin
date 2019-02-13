import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import entity.Interest;
import entity.Interest.InterestBuilder;

public class ListAllInterests {
//    private static final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.730610,-73.935242&radius=2000&type=museum";
//    private static final String DEFAULT_KEYWORD = ""; // no restriction
//    private static final String API_KEY = "AIzaSyCy-058HYzp9eeWBQI8qtZXMaYSOfV7fnM";

    public List<Interest> ListAllInterests(JSONObject obj) {
//        if (keyword == null) {
//            keyword = DEFAULT_KEYWORD;
//        }
//
//        try {
//            keyword = URLEncoder.encode(keyword, "UTF-8"); //"Rick Sun" => "Rick%20Sun"
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        // "apikey=qqPuP6n3ivMUoT9fPgLepkRMreBcbrjV&latlong=37,-120&keyword=event&radius=50"
//        String query = String.format("key=%s", API_KEY);
//        String url = URL + "&" + query;

        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            connection.setRequestMethod("GET");
//
//            int responseCode = connection.getResponseCode();
//            System.out.println("Sending request to url: " + url);
//            System.out.println("Response code: " + responseCode);
//
//            if (responseCode != 200) {
//                return new ArrayList<>();
//            }

//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            StringBuilder response = new StringBuilder();
//
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();
//            JSONObject obj = new JSONObject(response.toString());

            if (!obj.isNull("results")) {
                return getItemList(obj.getJSONArray("results"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    private List<Interest> getItemList(JSONArray results) throws JSONException {
        List<Interest> itemList = new ArrayList<>();
        for (int i = 0; i < results.length(); ++i) {
            JSONObject result = results.getJSONObject(i);
            InterestBuilder builder = new InterestBuilder();
            if (!result.isNull("id")) {
                builder.setId(result.getString("id"));
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
    private List<Interest> getItemListByCategory(List<Interest> list, String category) throws JSONException {
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