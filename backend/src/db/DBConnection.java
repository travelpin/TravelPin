package db;

import entity.Interest;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import entity.Item;

/*
Author: Debbie Liang
Date: Feb. 2019
 */

public interface DBConnection {

    /**
     * Close the connection.
     */
    public void close();

    /**
     * Insert the favorite items for a user.
     *
     * @param user_id
     * @param liked_locations
     */
    public void setFavoriteInterests(String user_id, List<String> liked_locations);

    /**
     * Delete the favorite items for a user.
     *
     * @param user_id
     * @param liked_locations
     */
    public void unsetFavoriteInterests(String user_id, List<String> liked_locations);


    /**
     * Insert the favorite items for a user.
     *
     * @param user_id
     * @param liked_locations
     */
    //public void setFavoritePlans(String user_id, List<String> liked_locations);

    /**
     * Delete the favorite items for a user.
     *
     * @param user_id
     * @param liked_locations
     */
    //public void unsetFavoritePlans(String user_id, List<String> liked_locations);


    /**
     * Get the favorite item id for a user.
     *
     * @param user_id
     * @return itemIds
     */
    public Set<String> getFavoriteInterestIds(String user_id);

    /**
     * Get the favorite items for a user.
     *
     * @param userId
     * @return items
     */

    public Set<Interest> getFavoriteInterests(String userId);

    public Set<Interest> getAllInterests();

    public Set<String> getCategories(String itemId);

    public Interest getInterestInfo(String interestId);

    public List<Interest> searchByName(String name);

    public void saveItem(Interest interest);

    public boolean registration(String username, String password, String firstName, String lastName);


//    public Set<Item> getFavoriteItems(String userId);
//
//    /**
//     * Gets categories based on item id
//     *
//     * @param itemId
//     * @return set of categories
//     */
//    public Set<String> getCategories(String itemId);
//
//    /**
//     * Search items near a geolocation and a term (optional).
//     *
//     * @param userId
//     * @param lat
//     * @param lon
//     * @param term
//     *            (Nullable)
//     * @return list of items
//     */
//    public List<Item> searchItems(double lat, double lon, String term);
//
//    /**
//     * Save item into db.
//     *
//     * @param item
//     */
//    public void saveItem(Item item);
//
//    /**
//     * Get full name of a user. (This is not needed for main course, just for demo
//     * and extension).
//     *
//     * @param userId
//     * @return full name of the user
//     */
    public String getFullname(String userId);


    /**
     * Return whether the credential is correct. (This is not needed for main
     * course, just for demo and extension)
     *
     * @param userId
     * @param password
     * @return boolean
     */
    public boolean verifyLogin(String userId, String password);

    public List<Interest> getInterestsByCategory(String category);

    public List<Interest> getInterestsByLocationId(JSONArray inputJSONArray);

}


