package mybean.data;


public class Index {
    String[] messageIDs = {"1", "2", "3"}; //消息ID
    String[] messages = {"test1", "test2", "test3"}; //消息内容
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

    public String[] getMessageIDs() {
        return messageIDs;
    }

    public void setMessageIDs(String[] messageID) {
        this.messageIDs = messageIDs;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] message) {
        this.messages = messages;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }


}