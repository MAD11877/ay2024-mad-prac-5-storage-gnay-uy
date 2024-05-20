package sg.edu.np.mad.madpractical5;

public class User {
    String name;
    String description;
    int id;
    boolean followed;

    public User() {
        name = "default";
        description = "default";
        id = 0;
        followed = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getFollowed() {
        return followed;
    }

    public User(String n, String d, int i, boolean f) {
        name = n;
        description = d;
        id = i;
        followed = f;
    }
}