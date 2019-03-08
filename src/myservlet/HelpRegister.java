package myservlet;

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

public class HelpRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
            get parameters
            check validation:
                1. check format
                2. check email not in database
                2. check passwords
            if ok:
                store in db
                -> registerSuccess.jsp
            else:
                store backNews in request
                -> register.jsp
         */
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final String confirmPassword = req.getParameter("confirmPassword");
        final String nickname = req.getParameter("nickname");
        final String sex = req.getParameter("sex");
        final String university = req.getParameter("university");
        final String major = req.getParameter("major");


        //1. check email format
        boolean emailWellFormatted = CommonHelper.checkEmailFormat(email);
        if (!emailWellFormatted) {
            req.setAttribute("backnews", "邮箱格式错误");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        //2. check email not exist
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        boolean emailNotExists = (boolean) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select * from user where mail=?");
                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();
                return rs.getRow() == 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
        if (!emailNotExists) {
            req.setAttribute("backnews", "邮箱已被注册");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        //3. check passwords equal
        boolean passwordEquals = password.equals(confirmPassword);
        if (!passwordEquals) {
            req.setAttribute("backnews", "两次输入的密码不一致");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        boolean insertOK = (boolean) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("insert into user (uname, mail, password, college, profession,profile_photo, points, sex)values (?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, nickname);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, university);
                ps.setString(5, major);
                ps.setString(6, "images/default.jpg");
                ps.setLong(7, 0);
                ps.setString(8, sex);

                ps.executeUpdate();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
        if (insertOK) {
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("backnews", "数据库错误");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }
}
