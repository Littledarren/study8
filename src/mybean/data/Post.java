package mybean.data;

import java.sql.Timestamp;

public class Post {
    private long ID = -1;
    private String mail = "testMail";
    private String title = "testTitle";
    private String content = "test";
    private Timestamp post_timestamp;
    private short predefined_classification = 7;
    private short type = 1;
    private short share_type = 1;
    private int numReads = 0, numLikes = 0, numComments = 0, numFavorites = 0;
    private String author = "testAuthor";

    private Comment[] comments = new Comment[0];



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Timestamp getPost_timestamp() {
        return post_timestamp;
    }

    public void setPost_timestamp(Timestamp post_timestamp) {
        this.post_timestamp = post_timestamp;
    }

    public int getNumFavorites() {
        return numFavorites;
    }

    public void setNumFavorites(int numFavorites) {
        this.numFavorites = numFavorites;
    }

    public short getPredefined_classification() {
        return predefined_classification;
    }

    public void setPredefined_classification(short predefined_classification) {
        this.predefined_classification = predefined_classification;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getShare_type() {
        return share_type;
    }

    public void setShare_type(short share_type) {
        this.share_type = share_type;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumReads() {
        return numReads;
    }

    public void setNumReads(int numReads) {
        this.numReads = numReads;
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
}
