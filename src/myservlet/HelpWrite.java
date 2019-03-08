package myservlet;

import mybean.data.Login;
import mybean.data.PersonalInfo;
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

public class HelpWrite extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Login login = CommonHelper.getLoginBean(request);
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }
        //store in database
        //->
        String realPath = getServletContext().getRealPath("/");
        String account = login.getAccount();

        String title = request.getParameter("title");
        String content = request.getParameter("content");

//        String post_url = realPath + "posts/" +

        short blogType = Short.valueOf(request.getParameter("blogType"));

        short predifined = Short.valueOf(request.getParameter("predefined"));

        String[] self_classifications = request.getParameterValues("self_classification");


        String submit = request.getParameter("submit");
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        if (submit.equals("保存草稿")) {

        } else {

        }

        boolean insertOK = (boolean) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("insert into post (mail, title, post_url, post_timestamp, predefined_classification, type, share_type)" +
                        "values (?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, account);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //find self_classification
        //generate personalInfo
        //->write.jsp

        Login login = CommonHelper.getLoginBean(request);
        final String account = login.getAccount();
        DatabaseHelper dh = DatabaseHelper.getInstance(request.getServletContext());

        PersonalInfo personalInfo = (PersonalInfo) dh.execSql(con -> {
            try {
                PersonalInfo pi = new PersonalInfo();

                PreparedStatement ps = con.prepareStatement("select gid, gname from sgroup natural join user_in_group where mail=?");
                ps.setString(1, account);
                ResultSet resultSet = ps.executeQuery();
                ArrayList<String> groupIDs = new ArrayList<>();
                ArrayList<String> groupNames = new ArrayList<>();
                while (resultSet.next()) {
                    groupIDs.add(resultSet.getString(1));
                    groupNames.add(resultSet.getString(2));
                }
                pi.setGroupIDs(groupIDs.toArray(new String[groupIDs.size()]));
                pi.setGroupNames(groupNames.toArray(new String[groupNames.size()]));

                ps = con.prepareStatement("select scname from self_classification natural join user where mail=?");
                ps.setString(1, account);
                resultSet = ps.executeQuery();

                ArrayList<String> classes = new ArrayList<>();
                while (resultSet.next()) {
                    classes.add(resultSet.getString(1));
                }
                pi.setClasses(classes.toArray(new String[classes.size()]));
                return pi;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });

        request.setAttribute("personalInfo", personalInfo);
        request.getRequestDispatcher("write.jsp").forward(request, response);

    }
}
