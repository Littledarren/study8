package myservlet;

import myutil.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String nickname = req.getParameter("nickname");
        String sex = req.getParameter("sex");
        String university = req.getParameter("university");
        String major = req.getParameter("major");


        //1. check email format
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        boolean emailWellFormatted = email.matches(pattern);
        if (!emailWellFormatted) {
            req.setAttribute("backnews", "email is not in right format");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
        //2. check email not exist
        DatabaseHelper dh = DatabaseHelper.getInstance();
        boolean emailNotExists = (boolean) dh.execSql(con -> {
            try {
                Statement sql = con.createStatement();
                ResultSet rs = sql.executeQuery("SELECT mail from user where mail=" + email);

                return rs.getRow() == 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
        if (!emailNotExists) {
            req.setAttribute("backnews", "email already has been registered");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
        //3. check passwords equal
        boolean passwordEquals = password.equals(confirmPassword);
        if (!passwordEquals) {
            req.setAttribute("backnews", "passwords are not equal");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

        boolean insertOK = (boolean) dh.execSql(con -> {
            try {
                Statement sql = con.createStatement();
                sql.executeUpdate("insert into user " +
                        "(uname, mail, password, college, profession, profile_photo, points, sex) " +
                        "VALUES (" + nickname + "," + email + "," + password + "," + university + "," + major + "," +
                        "images/default.jpg" + "," + "0" + sex + ")");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return true;
            }
        });
        if (insertOK) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        } else {
            req.setAttribute("backnews", "inserting into database failed");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }
}
