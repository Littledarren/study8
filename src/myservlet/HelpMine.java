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
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelpMine extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        Login login = CommonHelper.getLoginBean(request);
        String account = login.getAccount();
        if (!login.isLogined()) {
            response.sendRedirect("login.jsp");
            return;
        }
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getServletContext());
        if (submit.equals("create")) {
            //create group
            String create = request.getParameter("create");

            long groupId = (long) databaseHelper.execSql(con -> {
                try {
                    PreparedStatement ps = con.prepareStatement("insert into sgroup " +
                            "(gname, notice_board) values (?, ?)");
                    ps.setString(1, create);
                    ps.setString(2, "创群成功，赶紧邀请好友加入吧");
                    ps.executeUpdate();

                    ps = con.prepareStatement("select max(gid) from sgroup");
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    long gid = rs.getLong(1);

                    ps = con.prepareStatement("insert into user_in_group " +
                            "values (?, ?, ?)");
                    ps.setLong(1, gid);
                    ps.setString(2, account);
                    ps.setShort(3, (short) 0);
                    ps.executeUpdate();

                    return gid;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }

            });
            Group group = (Group) databaseHelper.execSql(con -> {
                try {
                    PreparedStatement ps = con.prepareStatement("select * from sgroup where gid=?");
                    Group temp = new Group();
                    ps.setLong(1, groupId);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    temp.setGid(rs.getLong(1));
                    temp.setGname(rs.getString(2));
                    temp.setNoticeContent(rs.getString(3));

                    ps = con.prepareStatement("select * from user_in_group where gid=?");
                    ps.setLong(1, groupId);
                    rs = ps.executeQuery();

                    ArrayList<String> mails = new ArrayList<>();
                    ArrayList<String> names = new ArrayList<>();
                    while (rs.next()) {

                    }
                    return temp;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            });
        } else if (submit.equals("join")) {
            String gidText = request.getParameter("join");
            long gid = Long.parseLong(gidText);

        }
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


        PersonalInfo pi = CommonHelper.getUserInfo(dh, account);
        request.setAttribute("personalInfo", pi);
        CommonHelper.getNums(request, dh, account, pi);

        //self_classification
        pi.setClasses((String[]) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select scname from self_classification where mail=?");
                ps.setString(1, account);
                ResultSet rs = ps.executeQuery();
                ArrayList<String> arrayList = new ArrayList<>();
                while (rs.next()) {
                    arrayList.add(rs.getString(1));
                }
                return arrayList.toArray(new String[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }));

        pi.setGroups((Group[]) dh.execSql(con -> {
            try {
                PreparedStatement ps = con.prepareStatement("select gid, gname from sgroup natural join user_in_group " +
                        "where mail=?");
                ps.setString(1, account);
                ResultSet rs = ps.executeQuery();
                ArrayList<Group> arrayList = new ArrayList<>();
                while (rs.next()) {
                    Group group = new Group();
                    group.setGid(rs.getLong(1));
                    group.setGname(rs.getString(2));
                    arrayList.add(group);
                }
                return arrayList.toArray(new Group[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }));



        request.getRequestDispatcher("mine.jsp").forward(request, response);
    }


}
