import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        List<Integer> hello = List.of(1, 2, 3, 5, 6).stream().sorted(Comparator.comparingInt(s -> -s)).limit(10).collect(
            Collectors.toList());



        System.out.println(hello);
    }

    class Twitter {

        private Followage followage;
        private TweetStore tweetStore;

        public Twitter() {
            followage = new Followage();
            tweetStore = new TweetStore();
        }

        public void postTweet(int userId, int tweetId) {
            tweetStore.add(userId, new Tweet(tweetId));
        }

        public List<Integer> getNewsFeed(int userId) {
            return followage
                .getFollowing(userId)
                .stream()
                .map(followingId -> tweetStore.getTweets(followingId))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(t -> -t.getTime()))
                .map(Tweet::getId)
                .limit(10)
                .collect(Collectors.toList());
        }

        public void follow(int followerId, int followeeId) {
            followage.add(followeeId, followerId);
        }

        public void unfollow(int followerId, int followeeId) {
            followage.remove(followeeId, followerId);
        }

        class Followage {

            private Map<Integer, Set<Integer>> fo;

            public Followage() {
                fo = new HashMap<>();
            }

            public void add(int followingId, int followingById) {
                fo.getOrDefault(followingById, new HashSet<>()).add(followingId);
            }

            public void remove(int followingId, int followingById) {
                fo.getOrDefault(followingById, new HashSet<>()).remove(followingId);
            }

            public Set<Integer> getFollowing(int userId) {
                return fo.get(userId);
            }

        }


        class TweetStore {

            private int time;

            private Map<Integer, Set<Tweet>> ts;

            public TweetStore() {
                ts = new HashMap<>();
                time = 0;
            }

            public void add(int userId, Tweet tweet) {
                tweet.setTime(++time);
                ts.putIfAbsent(userId, new HashSet<>());
                ts.get(userId).add(tweet);
            }

            public Set<Tweet> getTweets(int userId) {
                return ts.get(userId);
            }
        }

        class Tweet {
            private int id;
            private int time;

            public Tweet(int id) {
                this.id = id;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getId() {
                return id;
            }

            public int getTime() {
                return time;
            }
        }
    }

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */


}
