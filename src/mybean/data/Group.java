
package mybean.data;

public class Group {
    String groupName, groupID; //group名字与ID
    String myName; //我在本group中的备注名
    String[] noticeIDs, noticeTitles; //公告ID与题目
    String[] members;  //group成员
    String[] postID, postTitle, postAuthors; //博文ID,题目，作者
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

    public String[] getNoticeIDs() {
        return noticeIDs;
    }

    public void setNoticeIDs(String[] noticeIDs) {
        this.noticeIDs = noticeIDs;
    }

    public String[] getNoticeTitles() {
        return noticeTitles;
    }

    public void setNoticeTitles(String[] noticeTitles) {
        this.noticeTitles = noticeTitles;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String[] getPostID() {
        return postID;
    }

    public void setPostID(String[] postID) {
        this.postID = postID;
    }

    public String[] getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String[] postTitle) {
        this.postTitle = postTitle;
    }

    public String[] getPostAuthors() {
        return postAuthors;
    }

    public void setPostAuthors(String[] postAuthors) {
        this.postAuthors = postAuthors;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}