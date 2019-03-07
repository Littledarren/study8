
package mybean.data;

public class Group {
    private String groupName, groupID; //group名字与ID
    private String myName; //我在本group中的备注名
    private String noticeContent; //公告ID与题目
    private Post[] posts = new Post[3];

    {
        posts[0] = new Post();
        posts[1] = new Post();
        posts[2] = new Post();
    }

    String[] memberIDs, memberNames;  //group成员
    boolean isAdmin = false; //是否为管理员

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String[] getMemberIDs() {
        return memberIDs;
    }

    public void setMemberIDs(String[] memberIDs) {
        this.memberIDs = memberIDs;
    }

    public String[] getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(String[] memberNames) {
        this.memberNames = memberNames;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}