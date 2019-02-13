package db;

import java.util.List;

public interface DBConnection {
    public void likeInterests(String userId, List<String> interestIds);

    public void dislikeInterests(String userId, List<String> interestIds);

    public Interest getInterestInfo(String interestId);

    public Set<Interest> getFavoriteInterests(String userId);

    public Set<String> getFavoriteInterestIds(String userId);


}
