package myservlet;

import mybean.data.Login;
import mybean.data.PersonalInfo;
import mybean.data.dbModel.User;
import myutil.CommonHelper;
import myutil.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelpMine extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
    }

    static void getNums(DatabaseHelper dh, String account) {
        PersonalInfo pi = new PersonalInfo();
        User user = (User) dh.execSql(con -> {
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

        });
        pi.setUser(user);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //pernal info
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        Login login = CommonHelper.getLoginBean(request);
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }
        final String account = login.getAccount();

        getNums(dh, account);
        PersonalInfo pi;

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
                return getNum(post_mail, ps);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }));

        request.setAttribute("personalInfo", pi);
        request.setAttribute("post", post);
        request.getRequestDispatcher("mine.jsp").forward(request, response);
    }
}
