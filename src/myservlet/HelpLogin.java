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
import java.util.ArrayList;

public class HelpLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if success ->index.jsp
        String email = request.getParameter("account");
        String password = request.getParameter("password");
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        ArrayList<Object> arrayList = (ArrayList<Object>) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select uid, uname from user where mail=? and password=?");
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                ArrayList<Object> result = new ArrayList<>();

                if (rs.next()) {
                    result.add(rs.getLong(1));
                    result.add(rs.getString(2));
                }
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });
        long uid = (long) arrayList.get(0);
        String uname = (String) arrayList.get(1);
        Login login = CommonHelper.getLoginBean(request);
        if (uname == null) {
            //error
            System.out.println("lll");
            login.setBackNews("error");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            login.setUid(uid);
            login.setNickname(uname);
            login.setLogined(true);
            login.setAccount(email);
            login.setPassword(password);
            login.setSuccess(true);
            request.getRequestDispatcher("/handleIndex").forward(request, response);

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //logout
        Login login = CommonHelper.getLoginBean(request);
        login.setLogined(false);
        request.getRequestDispatcher("/handleIndex").forward(request, response);
    }
}
