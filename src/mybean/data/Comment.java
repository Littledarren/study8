package mybean.data;

public class Comment {
    private String ID;
    private String author;
    private String content;
    private String authorRepliedTo = null;
    private String contentRepliedTo;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorRepliedTo() {
        return authorRepliedTo;
    }

    public void setAuthorRepliedTo(String authorRepliedTo) {
        this.authorRepliedTo = authorRepliedTo;
    }

    public String getContentRepliedTo() {
        return contentRepliedTo;
    }

    public void setContentRepliedTo(String contentRepliedTo) {
        this.contentRepliedTo = contentRepliedTo;
    }
}
