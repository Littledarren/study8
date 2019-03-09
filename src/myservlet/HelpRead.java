package myservlet;

import mybean.data.Comment;
import mybean.data.Login;
import mybean.data.PersonalInfo;
import mybean.data.Post;
import myutil.CommonHelper;
import myutil.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class HelpRead extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        String mail = CommonHelper.getLoginBean(request).getAccount();
        if (mail == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        long postID = Long.valueOf(request.getParameter("postID"));
        //simple comment
        String comment = request.getParameter("comment");
        //comment on comment
        String replyContent = request.getParameter("replyContent");
        long replyTo = -1l;
        if (replyContent != null) {
            try {
                replyTo = Long.parseLong(request.getParameter("replyTo"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        long rt = replyTo;

        if (comment != null) {
            dh.execSql(con -> {
                try {
                    PreparedStatement ps = con.prepareStatement("insert into comment (post_id, mail, comment_content, comment_timestamp) " +
                            "values (?, ?, ?, ?)");
                    ps.setLong(1, postID);
                    ps.setString(2, mail);
                    ps.setString(3, comment);
                    ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    ps.executeUpdate();
                    //update post
                    ps = con.prepareStatement("update post set numComments=numComments+1 where post_id=?");
                    ps.setLong(1, postID);
                    ps.executeUpdate();
                    return null;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            });
        } else dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("insert into comment (post_id, mail, comment_content, comment_timestamp, reply_id) " +
                        "values (?, ?, ?, ?, ?)");
                ps.setLong(1, postID);
                ps.setString(2, mail);
                ps.setString(3, replyContent);
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setLong(5, rt);
                ps.executeUpdate();
                //update post
                ps = con.prepareStatement("update post set numComments=numComments+1 where post_id=?");
                ps.setLong(1, postID);
                ps.executeUpdate();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        this.doGet(request, response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //parameter ->postID

        //->read.jsp
        long postID = Long.valueOf(request.getParameter("postID"));

        Login login = CommonHelper.getLoginBean(request);
        final String mail = login.getAccount();

        Post post = null;
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        post = (Post) dh.execSql(con -> {
            try {
                Post temp = new Post();
                PreparedStatement ps = con.prepareStatement("select * from post where post_id=?");
                ps.setLong(1, postID);
                ResultSet rs = ps.executeQuery();
                rs.next();
                CommonHelper.getPostFromRS(temp, rs);

                //get comment
                ps = con.prepareStatement("select cid, mail, uname, comment_content, comment_timestamp, reply_id from comment  natural join user where post_id=? order by comment_timestamp ");
                ps.setLong(1, postID);
                rs = ps.executeQuery();
                ArrayList<Comment> arrayList = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setID(rs.getLong(1));
                    comment.setMail(rs.getString(2));
                    comment.setAuthor(rs.getString(3));
                    comment.setComment_content(rs.getString(4));
                    comment.setComment_timestamp(rs.getTimestamp(5));
                    comment.setReply_id(rs.getLong(6));
                    arrayList.add(comment);

                }
                temp.setComments(arrayList.toArray(new Comment[0]));
                //update read info
                if (mail != null && !mail.equals(temp.getMail())) {
                    ps = con.prepareStatement("select * from user_read_post where post_id=? and mail=?");
                    ps.setLong(1, postID);
                    ps.setString(2, mail);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        ps = con.prepareStatement("insert into user_read_post values (?, ?)");
                        ps.setLong(1, postID);
                        ps.setString(2, mail);
                        ps.executeUpdate();
                        ps = con.prepareStatement("update post set numReads = numReads+1 where post_id=?");
                        ps.setLong(1, postID);
                        ps.executeUpdate();
                    }
                }


                return temp;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });
        if (post == null) {
            response.sendRedirect("/404.jsp");
            return;
        }

        //get actual content
        String url = post.getContent();
        Path file = Paths.get(url);
        Charset charset = Charset.forName("utf8");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(file, charset)) {
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setContent(sb.toString());

        final String post_mail = post.getMail();

        PersonalInfo pi = CommonHelper.getUserInfo(dh, post_mail);
        Post[] posts = (Post[]) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select * from post where mail=? and share_type=0");
                ps.setString(1, post_mail);
                ArrayList<Post> arrayList = new ArrayList<Post>();
                CommonHelper.getPostsFromSQLstatement(arrayList, ps);
                return arrayList.toArray(new Post[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });
        pi.setPosts(posts);
        request.setAttribute("personalInfo", pi);
        CommonHelper.getNums(request, dh, post_mail, pi);
        request.setAttribute("post", post);
        request.getRequestDispatcher("read.jsp").forward(request, response);
    }


}
