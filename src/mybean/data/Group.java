
package mybean.data;

public class Group {
    //group名字与ID
    private long gid;
    private String gname;

    //    private String myName; //我在本group中的备注名 //forbid
    private String noticeContent; //公告ID与题目

    private Post[] posts = new Post[1];

    String[] memberIDs, memberNames;  //group成员
    boolean isAdmin = false; //是否为管理员

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}