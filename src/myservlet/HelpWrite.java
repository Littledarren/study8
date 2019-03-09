package myservlet;

import mybean.data.Group;
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
        /*

         */
        //1. make sure logged in
        Login login = CommonHelper.getLoginBean(request);
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }

        //2. file or posts?


        //3. if post

        /*
            write a post
            1. insert into post
            2. insert into post_in_sc (on condition that self_classifications not null)
            3. insert into sgroup_share_post () (on condition that st == 3)

            4. inform who watch on you(insert into message)
            5. store post content as txt
            6. update post
         */
        String realPath = getServletContext().getRealPath("/");
        String account = login.getAccount();
        final String uname = login.getNickname();
        //get parameters
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        short blogType = Short.valueOf(request.getParameter("blogType"));
        short predefined = Short.valueOf(request.getParameter("predefined"));
        short share_type = Short.valueOf(request.getParameter("private_switch"));
        String[] gids = request.getParameterValues("share_group");
        if (gids != null && gids.length != 0) share_type = 3;
        String[] self_classifications = request.getParameterValues("self_classification");
        String submit = request.getParameter("submit");
        if (submit.equals("保存草稿")) {
            share_type = 1;
        }
        final short st = share_type;

        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        long post_id = (long) dh.execSql(con -> {
            try {
                con.setAutoCommit(false);
                //1. insert into post
                PreparedStatement ps = con.prepareStatement("insert into post " +
                        "(mail, title, post_url, post_timestamp, predefined_classification, type, share_type, author)" +
                        "values (?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, account);
                ps.setString(2, title);
                ps.setString(3, ""); //will set later
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setInt(5, predefined);
                ps.setInt(6, blogType);
                ps.setInt(7, st);
                ps.setString(8, uname);
                ps.executeUpdate();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select max(post_id) from post");

                long pt = -1L;
                if (rs.next()) pt = Long.valueOf(rs.getString(1));

                //2. insert into post_in_sc (on condition that self_classifications not null)
                if (self_classifications != null) {
                    for (String sc : self_classifications) {
                        ps = con.prepareStatement("insert into post_in_sc values (?, ?, ?)");
                        ps.setLong(1, pt);
                        ps.setString(2, account);
                        ps.setString(3, sc);
                        ps.executeUpdate();
                    }
                }

                //3. insert into sgroup_share_post () (on condition that st == 3)
                if (st == 3) {
                    for (String gid : gids) {
                        ps = con.prepareStatement("insert into sgroup_share_post values (?, ?)");
                        ps.setLong(1, pt);
                        ps.setLong(2, Long.valueOf(gid));
                    }
                }
                //            4. inform who watch on you(insert into message)
                ps = con.prepareStatement("insert into message (mail, msgcontent, recvtime) " +
                        "select use_mail, ?, ? from user_watch_user where mail=?");
                ps.setString(1, Long.toString(pt));
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, account);
                ps.executeUpdate();
                con.commit();

                return pt;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                return -1L;
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        if (post_id == -1) {
            //failed to insert post
            response.sendRedirect("handleWrite");
            return;
        } else {
            //5. store post content as txt
            Charset charset = Charset.forName("UTF-8");
            String post_url = realPath + "posts/" + post_id + ".txt";
            Path file = Paths.get(post_url);
            Files.createFile(file);
            try (BufferedWriter bw = Files.newBufferedWriter(file, charset)) {
                bw.write(content, 0, content.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //6. update post
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
                ArrayList<Group> groups = new ArrayList<>();
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setGid(resultSet.getLong(1));
                    group.setGname(resultSet.getString(2));
                }
                pi.setGroups(groups.toArray(new Group[0]));

                ps = con.prepareStatement("select scname from self_classification natural join user where mail=?");
                ps.setString(1, account);
                resultSet = ps.executeQuery();

                ArrayList<String> classes = new ArrayList<>();
                while (resultSet.next()) {
                    classes.add(resultSet.getString(1));
                }
                pi.setClasses(classes.toArray(new String[0]));
                return pi;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });

        request.setAttribute("personalInfo", personalInfo);

        String action = request.getParameter("action");
        if (action != null && action.equals("upload")) {
            request.getRequestDispatcher("upload.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("write.jsp").forward(request, response);
        }

    }
}
