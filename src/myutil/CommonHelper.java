package myutil;

import mybean.data.Login;
import mybean.data.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommonHelper {

    //get loginBean
    public static Login getLoginBean(HttpServletRequest req) {
        Login loginBean = null;
        HttpSession session = req.getSession(true);
        try {
            loginBean = (Login) session.getAttribute("login");
            if (loginBean == null) {
                loginBean = new Login();
                session.setAttribute("login", loginBean);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            loginBean = new Login();
            session.setAttribute("login", loginBean);
        }
        return loginBean;
    }

    //check email format
    public static boolean checkEmailFormat(String email) {
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return email.matches(pattern);
    }

    public static void getPostsFromSQLstatement(ArrayList<Post> arrayList, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            getPostFromRS(post, rs);
            arrayList.add(post);
        }
    }

    public static void getPostFromRS(Post post, ResultSet rs) throws SQLException {
        post.setID(rs.getLong(1));
        post.setMail(rs.getString(2));
        post.setTitle(rs.getString(3));
        post.setContent(rs.getString(4)); // actual url
        post.setPost_timestamp(rs.getTimestamp(5));
        post.setPredefined_classification(rs.getShort(6));
        post.setType(rs.getShort(7));
        post.setShare_type(rs.getShort(8));
        post.setNumReads(rs.getInt(9));
        post.setNumLikes(rs.getInt(10));
        post.setNumComments(rs.getInt(11));
        post.setNumFavorites(rs.getInt(12));
        post.setAuthor(rs.getString(13));
    }
}
