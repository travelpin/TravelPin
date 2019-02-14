package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Interest {
    private String id;
    private String name;
    private double lat;
    private String formattedAddress;
    private Set<String> categories;
    private String placeId;
    private double lng;

    private Interest(InterestBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.lat = builder.lat;
        this.formattedAddress = builder.formattedAddress;
        this.categories = builder.categories;
        this.placeId = builder.placeId;
        this.lng = builder.lng;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double getLng() {
        return lng;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("name", name);
            obj.put("lat", lat);
            obj.put("formattedAddress", formattedAddress);
            obj.put("categories", new JSONArray(categories));
            obj.put("placeId", placeId);
            obj.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static class InterestBuilder {
        private String id;
        private String name;
        private double lat;
        private String formattedAddress;
        private Set<String> categories;
        private String placeId;
        private double lng;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public void setCategories(Set<String> categories) {
            this.categories = categories;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public Interest build() {
            return new Interest(this);
        }


    }


}
