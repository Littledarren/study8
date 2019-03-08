package myservlet;

import mybean.data.Login;
import mybean.data.PersonalInfo;
import myutil.CommonHelper;
import myutil.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class HelpWrite extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //post or resource?
        //where?

        //1. make sure logged in
        Login login = CommonHelper.getLoginBean(request);
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }

        //2. file or posts?


        //3. if post

        //save post in posts as post_id.txt
        String realPath = getServletContext().getRealPath("/");
        String account = login.getAccount();
        //get parameters
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        short blogType = Short.valueOf(request.getParameter("blogType"));
        short predifined = Short.valueOf(request.getParameter("predefined"));
        short share_type = Short.valueOf(request.getParameter("private_switch"));
        String[] gids = request.getParameterValues("share_group");
        if ((gids == null || gids.length != 0) && share_type == 0) share_type = 3;
        String[] self_classifications = request.getParameterValues("self_classification");
        String submit = request.getParameter("submit");
        if (submit.equals("保存草稿")) {
            share_type = 1;
        }
        final short st = share_type;

        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        long post_id = (long) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("insert into post (mail, title, post_url, post_timestamp, predefined_classification, type, share_type)values (?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, account);
                ps.setString(2, title);
                ps.setString(3, "");
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setInt(5, predifined);
                ps.setInt(6, blogType);
                ps.setInt(7, st);
                ps.executeUpdate();
                //no interrupt
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select max(post_id) from post");
                if (rs.next()) return Long.valueOf(rs.getString(1));
                return -1L;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1L;
            }
        });
        if (post_id == -1) {
            //failed to insert post
            response.sendRedirect("handleWrite");
            return;
        } else {
            Charset charset = Charset.forName("UTF-8");
            String post_url = realPath + "posts/" + post_id + ".txt";
            Path file = Paths.get(post_url);
            Files.createFile(file);
            try (BufferedWriter bw = Files.newBufferedWriter(file, charset)) {
                bw.write(content, 0, content.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean updateOK = (boolean) dh.execSql(con -> {
                try {
                    PreparedStatement ps = con.prepareStatement("update post set post_url=? where post_id=?");
                    ps.setString(1, post_url);
                    ps.setLong(2, post_id);
                    ps.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            });
            response.sendRedirect("handleWrite");
        }


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
