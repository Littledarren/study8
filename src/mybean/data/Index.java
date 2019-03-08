package mybean.data;


import mybean.data.dbModel.Message;

public class Index {

    Message[] messages;

    short postType = -1;  //列出的博文类型
    Post[] posts;


    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    public short getPostType() {
        return postType;
    }

    public void setPostType(short postType) {
        this.postType = postType;
    }
}