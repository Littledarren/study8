package myservlet;

import mybean.data.PersonalInfo;
import mybean.data.Post;
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

public class HelpRead extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //parameter ->postID

        //->read.jsp
        long postID = Long.valueOf(request.getParameter("postID"));

        Post post = null;
        DatabaseHelper dh = DatabaseHelper.getInstance(getServletContext());
        post = (Post) dh.execSql(con -> {
            try {
                Post temp = new Post();
                PreparedStatement ps = con.prepareStatement("select * from post where post_id=?");
                ps.setLong(1, postID);
                ResultSet rs = ps.executeQuery();
                rs.next();
                CommonHelper.getPostFromRS(temp, rs);
                return temp;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        });

        PersonalInfo pi = new PersonalInfo();
        request.setAttribute("personalInfo", pi);
        request.setAttribute("post", post);
        request.getRequestDispatcher("read.jsp").forward(request, response);
    }


}
