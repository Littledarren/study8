package mybean.data;

import java.sql.Timestamp;

public class Comment {
    private long ID = -1;
    private long post_id = -1;
    private String mail;
    private String comment_content;
    private Timestamp comment_timestamp;

    private long reply_id;

    private String author;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public Timestamp getComment_timestamp() {
        return comment_timestamp;
    }

    public void setComment_timestamp(Timestamp comment_timestamp) {
        this.comment_timestamp = comment_timestamp;
    }

    public long getReply_id() {
        return reply_id;
    }

    public void setReply_id(long reply_id) {
        this.reply_id = reply_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
