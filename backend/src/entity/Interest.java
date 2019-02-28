package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Interest {
    private String locationId;
    private String name;
    private double lat;
    private double lng;
    private double rating;
    private double openTime;
    private double closeTime;
    private double suggestVisitTime;
    private String formattedAddress;
    private String imageUrl;
    private double price;
    private Set<String> categories;
    private String placeId;

    private Interest(InterestBuilder builder) {
        this.locationId = builder.locationId;
        this.name = builder.name;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.rating = builder.rating;
        this.openTime = builder.openTime;
        this.closeTime = builder.closeTime;
        this.suggestVisitTime = builder.suggestVisitTime;
        this.formattedAddress = builder.formattedAddress;
        this.imageUrl = builder.imageUrl;
        this.price = builder.price;
        this.categories = builder.categories;
        this.placeId = builder.placeId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getRating() {
        return rating;
    }

    public double getOpenTime() {
        return openTime;
    }

    public double getCloseTime() {
        return closeTime;
    }

    public double getSuggestVisitTime() {
        return suggestVisitTime;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getImageUrl() { return imageUrl; }

    public double getPrice() {
        return price;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public String getPlaceId() {
        return placeId;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("location_id", locationId);
            obj.put("name", name);
            obj.put("lat", lat);
            obj.put("lng", lng);
            obj.put("rating", rating);
            obj.put("open_time", openTime);
            obj.put("close_time", closeTime);
            obj.put("suggest_visit_time", suggestVisitTime);
            obj.put("formattedAddress", formattedAddress);
            obj.put("imageUrl", imageUrl);
            obj.put("price", price);
            obj.put("categories", new JSONArray(categories));
            obj.put("placeId", placeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static class InterestBuilder {
        private String locationId;
        private String name;
        private double lat;
        private double lng;
        private double rating;
        private double openTime;
        private double closeTime;
        private double suggestVisitTime;
        private String formattedAddress;
        private String imageUrl;
        private double price;
        private Set<String> categories;
        private String placeId;

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public void setOpenTime(double openTime) {
            this.openTime = openTime;
        }

        public void setCloseTime(double closeTime) {
            this.closeTime = closeTime;
        }

        public void setSuggestVisitTime(double suggestVisitTime) {
            this.suggestVisitTime = suggestVisitTime;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setCategories(Set<String> categories) {
            this.categories = categories;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public Interest build() {
            return new Interest(this);
        }
    }
}

