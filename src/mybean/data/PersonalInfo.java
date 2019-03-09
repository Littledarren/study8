//个人信息

package mybean.data;

import mybean.data.dbModel.User;

public class PersonalInfo {

    User user = new User();

    Post[] posts = new Post[0]; //个人发布的博文的ID与题目

    int numArticles = 0, numFans = 0, numLikes = 0, numComments = 0, numReads = 0;

    Group[] groups = new Group[0];
    String[] classes = {"test"}; //个人分类

    String backNews;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
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

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
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