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
    public static Member currentUser;

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

    public static void EditMemberData(int memberIndex, Member newMemberData) {
        if (memberIndex >= AllMembers.size()) {
            Validator.messageBox("Member index out of range", "Error");
        } else {
            AllMembers.set(memberIndex, newMemberData);
        }
    }

    public static ArrayList<Member> getAllMembers() {
        Collections.sort(AllMembers);
        return AllMembers;
    }

    public static Member getMember(String email) {
        for (Member m : AllMembers) {
            if (m.getEmail().equals(email)) {
                return m;
            }
        }
        Validator.messageBox("Member not found", "Error");
        return null;
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

    public static void ClearAllMemberData() {
        AllMembers.clear();
        UpdateMemberData();
    }

    public int compareTo(Member otherMember) {
        return this.getName().compareTo(otherMember.getName());
    }
}
