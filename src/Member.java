import java.util.ArrayList;

/**
 * Created by Toby on 2/12/2015.
 *
 * makes member class
 */
public class Member {
    private String name, email;
    private int mid;

    public static ArrayList<Member> AllMembers;

    public Member(int m, String n, String e)
    {
        mid = m;
        name = n;
        email = e;
    }

    public int getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void AddToMembers() {
        AllMembers.add(this);
        //TODO: Add to database
    }

    public void RemoveFromMember() {
        AllMembers.remove(this);
        //TODO: Remove from database
    }
}
