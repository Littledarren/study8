package myservlet;

import mybean.data.Group;
import mybean.data.Login;
import mybean.data.PersonalInfo;
import myutil.CommonHelper;
import myutil.DatabaseHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelpWrite extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESOURCES = "resources";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 500;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 550;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*

         */
        //1. make sure logged in
        Login login = CommonHelper.getLoginBean(request);
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }

        String realPath = getServletContext().getRealPath("/");
        String account = login.getAccount();


        //2. file or posts?
        String submit = request.getParameter("submit");
        if (submit.equals("上传")) {
            String url = null;
            //if file
            if (!ServletFileUpload.isMultipartContent(request)) {
                response.sendRedirect("handleWrite");
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);
            upload.setHeaderEncoding("UTF-8");
            String uploadPath = realPath + RESOURCES;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            List<FileItem> formItems = null;
            try {
                formItems = upload.parseRequest(request);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            String fileName = new File(item.getName()).getName();
                            url = uploadPath + File.separator + fileName;
                            File storeFile = new File(url);
                            item.write(storeFile);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String title = request.getParameter("title");
            String path = url;
            String[] gids = request.getParameterValues("share_group");
            short share_type = Short.parseShort(request.getParameter("private_switch"));
            if (gids != null && gids.length != 0) share_type = 2;
            final short st = share_type;
            long resource_id = (long) dh.execSql(con -> {
                try {
                    // 1.insert into resource
                    PreparedStatement ps = con.prepareStatement("insert into resource " +
                            "(rname, resource_url, cost_points, share_type) " +
                            "values (?, ?, ?, ?)");
                    ps.setString(1, title);
                    ps.setString(2, path);
                    ps.setLong(3, 0);
                    ps.setShort(4, st);
                    ps.executeUpdate();

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select max(rid) from resource");

                    long rid = -1L;
                    if (rs.next()) rid = Long.valueOf(rs.getString(1));


                    //2. insert into group_share_resource () (on condition that st == 2)
                    if (st == 2) {
                        for (String gid : gids) {
                            ps = con.prepareStatement("insert into group_share_resource values (?, ?)");
                            ps.setLong(1, rid);
                            ps.setLong(2, Long.valueOf(gid));
                            ps.executeUpdate();
                        }
                    }
                    //3. insert into user_with_resource
                    ps = con.prepareStatement("insert into user_with_resource values (?, ?, ?)");
                    ps.setString(1, account);
                    ps.setLong(2, rid);
                    ps.setShort(3, (short) 0);
                    ps.executeUpdate();

                    //4. inform who watch on you(insert into message)
                    storeMessage(account, con, rid);
                    return rid;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1L;
                }
            });
            response.sendRedirect("handleWrite");
            return;
        }

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

        final String uname = login.getNickname();
        //get parameters
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        short blogType = Short.valueOf(request.getParameter("blogType"));
        short predefined = Short.valueOf(request.getParameter("predefined"));
        short share_type = Short.valueOf(request.getParameter("private_switch"));
        String[] gids = request.getParameterValues("share_group");
        if (gids != null && gids.length != 0) share_type = 2;
        String[] self_classifications = request.getParameterValues("self_classification");
        if (submit.equals("保存草稿")) {
            share_type = 1;
        }
        final short st = share_type;


        long post_id = (long) dh.execSql(con -> {
            try {
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

                //3. insert into sgroup_share_post () (on condition that st == 2)
                if (st == 2) {
                    for (String gid : gids) {
                        ps = con.prepareStatement("insert into sgroup_share_post values (?, ?)");
                        ps.setLong(1, pt);
                        ps.setLong(2, Long.valueOf(gid));
                        ps.executeUpdate();
                    }
                }
                //            4. inform who watch on you(insert into message)
                storeMessage(account, con, pt);

                return pt;
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
            //5. store post content as txt
            Charset charset = Charset.forName("utf8");
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

    private void storeMessage(String account, Connection con, long rid) throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement("insert into message (mail, msgcontent, recvtime) " +
                "select use_mail, ?, ? from user_watch_user where mail=?");
        ps.setString(1, Long.toString(rid));
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        ps.setString(3, account);
        ps.executeUpdate();
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
