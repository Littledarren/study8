//个人信息

package mybean.data;

public class PersonalInfo {
    private String email = "test";
    private String password = "test";
    private String nickname = "test";
    private String sex = "test";
    private String university = "test";
    private String major = "test";
    private int score = 0;
    private int rank = 0;

    int numArticles = 0, numFans = 0, numLikes = 0, numComments = 0, numReads = 0;
    Post[] posts = new Post[3]; //个人发布的博文的ID与题目

    {
        posts[0] = new Post();
        posts[1] = new Post();
        posts[2] = new Post();
    }


    String[] groupIDs = {"test"}, groupNames = {"test"};//所在的groups的名字与ID
    String[] classes = {"test"}; //个人分类
    String backNews;

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getNumArticles() {
        return numArticles;
    }

    public void setNumArticles(int numArticles) {
        this.numArticles = numArticles;
    }

    public int getNumFans() {
        return numFans;
    }

    public void setNumFans(int numFans) {
        this.numFans = numFans;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public int getNumReads() {
        return numReads;
    }

    public void setNumReads(int numReads) {
        this.numReads = numReads;
    }

    public String[] getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(String[] groupIDs) {
        this.groupIDs = groupIDs;
    }

    public String[] getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String[] groupNames) {
        this.groupNames = groupNames;
    }

    public String[] getClasses() {
        return classes;
    }

    public void setClasses(String[] classes) {
        this.classes = classes;
    }

    public String getBackNews() {
        return backNews;
    }

    public void setBackNews(String backNews) {
        this.backNews = backNews;
    }
}