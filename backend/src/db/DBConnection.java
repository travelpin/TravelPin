package db;

import java.util.List;

public interface DBConnection {
    public void likeInterests(String userId, List<String> interestIds);

    public void dislikeInterests(String userId, List<String> interestIds);
}
