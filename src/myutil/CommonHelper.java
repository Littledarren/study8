package myutil;

import mybean.data.Login;
import mybean.data.PersonalInfo;
import mybean.data.Post;
import mybean.data.dbModel.User;

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

    public static PersonalInfo getUserInfo(DatabaseHelper dh, String account) {
        PersonalInfo pi = new PersonalInfo();
        pi.setUser((User) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select * from user where mail=?");
                ps.setString(1, account);
                ResultSet rs = ps.executeQuery();
                rs.next();
                User temp = new User();
                temp.setMail(rs.getString(1));
                temp.setPassword(rs.getString(2));
                temp.setUname(rs.getString(3));
                temp.setSex(rs.getString(4));
                temp.setCollege(rs.getString(5));
                temp.setProfession(rs.getString(6));
                temp.setProfile_photo(rs.getString(7));
                temp.setPoints(rs.getLong(8));
                return temp;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }));
        return pi;
    }

    public static void getNums(HttpServletRequest request, DatabaseHelper dh, String account, PersonalInfo pi) {
        pi.setNumArticles((Integer) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select count(post_id) from post where mail=? and share_type=0");
                return CommonHelper.getNum(account, ps);

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        }));

        pi.setNumComments((Integer) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select count(cid) from comment where mail=? ");
                return CommonHelper.getNum(account, ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        }));

        pi.setNumFans((Integer) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select count(use_mail) from user_watch_user where mail=?");
                return CommonHelper.getNum(account, ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        }));

        pi.setNumLikes((Integer) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select sum(numLikes) from post where mail=?");
                return CommonHelper.getNum(account, ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        }));

        pi.setNumReads((Integer) dh.execSql(con -> {
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement("select sum(numReads) from post where mail=?");
                return CommonHelper.getNum(account, ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }));

        request.setAttribute("personalInfo", pi);
    }

    public static Object getNum(String mail, PreparedStatement ps) throws SQLException {
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
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
