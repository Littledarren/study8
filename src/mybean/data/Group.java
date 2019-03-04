
package mybean.data;

public class Group {
    String groupName, groupID; //group名字与ID
    String myName; //我在本group中的备注名
    String[] noticeIDs, noticeTitles; //公告ID与题目
    String[] members;  //group成员
    String[] postID, postTitle, postAuthors; //博文ID,题目，作者
    boolean isAdmin = false; //是否为管理员

}