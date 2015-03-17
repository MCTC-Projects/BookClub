import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Toby on 2/12/2015.
 *
 * makes member class
 */
public class Member implements Comparable<Member> {
    private String name, email;
    private int mid;

    private static ArrayList<Member> AllMembers = new ArrayList<Member>();

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

    public static void AddMember(Member member) {
        AllMembers.add(member);
        UpdateMemberData();
    }

    public static void RemoveMember(Member member) {
        AllMembers.remove(member);
        UpdateMemberData();
    }

    public static ArrayList<Member> getAllMembers() {
        Collections.sort(AllMembers);
        return AllMembers;
    }

    public static void UpdateMemberData() {
        Collections.sort(AllMembers);
        //TODO: Send AllMembers ArrayList to DB

        //Call this each time AllMembers ArrayList is updated
    }

    public static void GetLatestMemberData() {
        //TODO: Populate AllMembers ArrayList from DB
        //Call this at application start (in frmMain)
        Collections.sort(AllMembers);
    }

    public int compareTo(Member otherMember) {
        return this.getName().compareTo(otherMember.getName());
    }
}
