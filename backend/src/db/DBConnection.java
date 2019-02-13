package db;

import java.util.List;

public interface DBConnection {
    public void setFavoriteItems(String userId, List<String> interestIds);

    public void unsetFavoriteItems(String userId, List<String> interestIds);
}
