package mybean.data;


public class Index {
    String[] messageID = {"1", "2", "3"}; //消息ID
    String[] messageName = {"test1", "test2", "test3"}; //消息名
    String postType = "recommended";  //列出的博文类型

    Post[] posts = new Post[3];

    {
        posts[0] = new Post();
        posts[1] = new Post();
        posts[2] = new Post();
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public String[] getMessageID() {
        return messageID;
    }

    public void setMessageID(String[] messageID) {
        this.messageID = messageID;
    }

    public String[] getMessageName() {
        return messageName;
    }

    public void setMessageName(String[] messageName) {
        this.messageName = messageName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }


}