package myservlet;

import mybean.data.Login;
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

public class HelpLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if success ->index.jsp
        String email = request.getParameter("account");
        String password = request.getParameter("password");
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        String uname = (String) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select uname from user where mail=? and password=?");
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getString(1);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });
        Login login = CommonHelper.getLoginBean(request);
        if (uname == null) {
            login.setSuccess(false);
            login.setBackNews("账户或密码输入错误");
            response.sendRedirect("login.jsp");
        } else {
            login.setNickname(uname);
            login.setLogined(true);
            login.setAccount(email);
            login.setPassword(password);
            login.setSuccess(true);
            response.sendRedirect("handleIndex");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //logout
        Login login = CommonHelper.getLoginBean(request);
        login.setLogined(false);
        response.sendRedirect("handleIndex");
    }
}
