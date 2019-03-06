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
            TODO
            get parameters
            check validation:
                1. check passwords
                2. check email not in database
            if ok:
                store in db
                -> registerSuccess.jsp
            else:
                request.setAttribute("success", "false");
                -> register.jsp
         */
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String nickname = req.getParameter("nickname");
        String sex = req.getParameter("sex");
        String university = req.getParameter("university");
        String major = req.getParameter("major");

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

        boolean passwordEquals = password.equals(confirmPassword);

        if (emailNotExists && passwordEquals) {
            boolean insertOK = (boolean) dh.execSql(con -> {
                try {
                    Statement sql = con.createStatement();
                    sql.executeUpdate("insert into user " +
                            "(uname, mail, password, college, profession, profile_photo, points) " +
                            "VALUES (" + nickname + "," + email + "," + password + "," + university + "," + major + "," +
                            "images/default.jpg" + "," + "0)");
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return true;
                }
            });
            if (insertOK) {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
        }

        //error occurred
        req.setAttribute("success", false);
        req.getRequestDispatcher("register.jsp").forward(req, resp);


    }
}
