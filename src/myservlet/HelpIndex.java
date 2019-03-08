package myservlet;


import mybean.data.Index;
import mybean.data.Login;
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
import java.util.ArrayList;
import java.util.HashMap;


public class HelpIndex extends HttpServlet {
    final private HashMap<String, Short> predefined = new HashMap<>() {{
        put("forum", (short) 1);
        put("knowledge", (short) 2);
        put("experience", (short) 3);
        put("resource", (short) 4);
        put("postgraduate", (short) 5);
        put("work", (short) 6);
        put("others", (short) 7);
        //special tags
        put("recommend", (short) 8);
        put("focus", (short) 9);

    }};


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* TODO

            judge parameter type

            judge isLogined
            if notLogined
                query posts
            if Logined
                query msg
                query posts with certain type
                    title author read_count
                    order by read_count
             -> index.jsp

         */

        Login login = CommonHelper.getLoginBean(req);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getServletContext());

        String typeString = req.getParameter("type");
        short type = predefined.getOrDefault(typeString, (short) 8);

        short postType
        try {

        }
        short postType = Short.valueOf(req.getParameter("postType"));
        int postNum = Integer.valueOf(req.getParameter("postNum"));

        Index index = new Index();
        index.setMessages(new String[]{"aaa", "bbb", "ccc"});

        if (type == 8) {
            Post[] posts = (Post[]) databaseHelper.execSql(con -> {
                ArrayList<Post> arrayList = new ArrayList<>();
                try {
                    PreparedStatement ps = con.prepareStatement("select post_id, title,  " +
                            "post_url, post_timestamp, predefined_classification, type, mail, " +
                            "numReads, numLikes, numComments, numFavourites " +
                            "from post " +
                            "where title like ? and share_type=0 " +
                            "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                            "limit ?, ?");

                    ps.setInt(2, start);
                    ps.setInt(3, num);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Post post = new Post();
                        post.setID(rs.getLong(1));
                        post.setTitle(rs.getString(2));
                        post.setContent(rs.getString(3)); // actual url
                        post.setPost_timestamp(rs.getTimestamp(4));
                        post.setPredefined_classification(rs.getShort(5));
                        post.setType(rs.getShort(6));
                        post.setAuthor(rs.getString(7));  //actual mail
                        arrayList.add(post);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                return arrayList.toArray(new Post[0]);
            });
        }

        index.setPosts(posts);
        req.setAttribute("index", index);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    /**
     * query posts with keywords from start at the length of num
     *
     * @param databaseHelper
     * @param keywords
     * @param start
     * @param num
     * @return Post[]
     */
    private Post[] query(DatabaseHelper databaseHelper, String keywords, int start, int num) {


        Post[] posts = (Post[]) databaseHelper.execSql(con -> {
            ArrayList<Post> arrayList = new ArrayList<>();
            try {
                PreparedStatement ps = con.prepareStatement("select post_id, title,  " +
                        "post_url, post_timestamp, predefined_classification, type, mail, " +
                        "numReads, numLikes, numComments, numFavourites " +
                        "from post " +
                        "where title like ? and share_type=0 " +
                        "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                        "limit ?, ?");
                ps.setString(1, "%" + keywords + "%");
                ps.setInt(2, start);
                ps.setInt(3, num);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Post post = new Post();
                    post.setID(rs.getLong(1));
                    post.setTitle(rs.getString(2));
                    post.setContent(rs.getString(3)); // actual url
                    post.setPost_timestamp(rs.getTimestamp(4));
                    post.setPredefined_classification(rs.getShort(5));
                    post.setType(rs.getShort(6));
                    post.setAuthor(rs.getString(7));  //actual mail
                    arrayList.add(post);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            return arrayList.toArray(new Post[0]);
        });
        return posts;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
