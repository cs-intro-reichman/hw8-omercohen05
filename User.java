/**
 * Represents a user in a social network. A user is characterized by a name,
 * a list of user names that s/he follows, and the list's size.
 */
public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name; // name of this user
    private String[] follows; // array of user names that this user follows
    private int fCount; // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount];
        fCount = 0;
    }

    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    public String getName() {
        return name;
    }

    public String[] getfFollows() {
        return follows;
    }

    public int getfCount() {
        return fCount;
    }

    /**
     * If this user follows the given name, returns true; otherwise returns false.
     */
    public boolean follows(String name) {
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes this user follow the given name. If successful, returns true.
     */
    public boolean addFollowee(String name) {
        if (name == null)
            return false; // optional safety
        if (follows(name) || fCount == maxfCount)
            return false;

        follows[fCount] = name;
        fCount++;
        return true;
    }

    /**
     * Removes the given name from the follows list of this user. If successful,
     * returns true. If the name is not in the list, returns false.
     *
     * We keep the array compact by shifting left, so valid followees stay in
     * [0..fCount-1].
     */
    public boolean removeFollowee(String name) {
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                // shift left to fill the gap
                for (int j = i; j < fCount - 1; j++) {
                    follows[j] = follows[j + 1];
                }
                follows[fCount - 1] = null;
                fCount--;
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of users that both this user and the other user follow.
     * (size of intersection of the two follows lists)
     */
    public int countMutual(User other) {
        if (other == null)
            return 0;

        int mutual = 0;

        // iterate over the smaller follow list for efficiency
        User smaller = (this.fCount <= other.fCount) ? this : other;
        User larger = (smaller == this) ? other : this;

        for (int i = 0; i < smaller.fCount; i++) {
            String candidate = smaller.follows[i];
            if (candidate != null && larger.follows(candidate)) {
                mutual++;
            }
        }
        return mutual;
    }

    /**
     * Checks if this user is a friend of the other user.
     * (if two users follow each other, they are "friends.")
     */
    public boolean isFriendOf(User other) {
        if (other == null)
            return false;
        return this.follows(other.name) && other.follows(this.name);
    }

    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}
