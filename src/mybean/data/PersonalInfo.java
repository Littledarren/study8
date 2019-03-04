//个人信息

package mybean.data;

public class PersonalInfo {
    String email = "", password = "", nickname = "",
            sex = "", university = "", major = "";
    int score = 0, rank = 0;  //积分与排名
    int numArticles = 0, numFans = 0, numLikes = 0, numComments = 0, numReads = 0;
    String[] postID, postTitle; //个人发布的博文的ID与题目
    String[] groupIDs, groupNames;//所在的groups的名字与ID
    String[] classes; //个人分类
    String backNews;
}