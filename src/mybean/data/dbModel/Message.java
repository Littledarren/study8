package mybean.data.dbModel;

import java.sql.Timestamp;

public class Message {
    private long mid;
    private String mail;
    private String msg_content;
    private Timestamp recvtime;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public Timestamp getRecvtime() {
        return recvtime;
    }

    public void setRecvtime(Timestamp recvtime) {
        this.recvtime = recvtime;
    }
}
