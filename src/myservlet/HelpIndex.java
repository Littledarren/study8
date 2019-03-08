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
        //1.get some data from Helper
        Login login = CommonHelper.getLoginBean(req);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getServletContext());

        //2. get predefined_classification and num *****this type is predefined_classification
        String typeString = req.getParameter("type");
        short type = predefined.getOrDefault(typeString, (short) 8);
        short tempType = 8;
        int tempNum = 5;
        try {

            tempType = Short.valueOf(req.getParameter("postType"));
            tempNum = Integer.valueOf(req.getParameter("postNum"));
        } catch (NumberFormatException e) {
            // this may mean
            tempType = type;
        }
        final int postNum = tempNum;
        final short postType = tempType;

        //3.set bean
        Index index = new Index();
        index.setMessages(new String[]{"aaa", "bbb", "ccc"});
        Post[] posts = null;
        if (postType == 8) {
            //recommended
            posts = (Post[]) databaseHelper.execSql(con -> {
                ArrayList<Post> arrayList = new ArrayList<>();
                try {
                    PreparedStatement ps = con.prepareStatement("select * " +
                            "from post " +
                            "where share_type=0 " +
                            "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                            "limit ?, ?");

                    ps.setInt(1, postNum - 5);
                    ps.setInt(2, 5);
                    getPostsFromSQLstatement(arrayList, ps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return arrayList.toArray(new Post[0]);
            });
        } else if (postType == 9) {
            //focused person
            if (!login.isLogined()) {
                resp.sendRedirect("login.jsp");
                return;
            }
            final String mail = login.getAccount();
            posts = (Post[]) databaseHelper.execSql(con -> {
                ArrayList<Post> arrayList = new ArrayList<>();
                try {
                    PreparedStatement ps = con.prepareStatement("select * " +
                            "from post natural join user " +
                            "where share_type=0 and post.mail in " +
                            "(select user_watch_user.mail from user_watch_user where use_mail = ?) " +
                            "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                            "limit ?, ?");
                    ps.setString(1, mail);
                    ps.setInt(1, postNum - 5);
                    ps.setInt(2, 5);
                    getPostsFromSQLstatement(arrayList, ps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return arrayList.toArray(new Post[0]);
            });
        } else {
            posts = (Post[]) databaseHelper.execSql(con -> {
                ArrayList<Post> arrayList = new ArrayList<>();
                try {
                    PreparedStatement ps = con.prepareStatement("select * " +
                            "from post " +
                            "where share_type=0 and type=? " +
                            "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                            "limit ?, ?");
                    ps.setShort(1, postType);
                    ps.setInt(1, postNum - 5);
                    ps.setInt(2, 5);
                    getPostsFromSQLstatement(arrayList, ps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return arrayList.toArray(new Post[0]);
            });
        }

        index.setPosts(posts);
        index.setPostType(postType);


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
                PreparedStatement ps = con.prepareStatement("select * " +
                        "from post " +
                        "where title like ? and share_type=0 " +
                        "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                        "limit ?, ?");
                ps.setString(1, "%" + keywords + "%");
                ps.setInt(2, start);
                ps.setInt(3, num);
                getPostsFromSQLstatement(arrayList, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            return arrayList.toArray(new Post[0]);
        });
        return posts;
    }

    private void getPostsFromSQLstatement(ArrayList<Post> arrayList, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setID(rs.getLong(1));
            post.setMail(rs.getString(2));
            post.setTitle(rs.getString(3));
            post.setContent(rs.getString(4)); // actual url
            post.setPost_timestamp(rs.getTimestamp(5));
            post.setPredefined_classification(rs.getShort(6));
            post.setType(rs.getShort(7));
            post.setShare_type(rs.getShort(8));
            post.setNumReads(rs.getInt(9));
            post.setNumLikes(rs.getInt(10));
            post.setNumComments(rs.getInt(11));
            post.setNumFavorites(rs.getInt(12));
            post.setAuthor(rs.getString(13));
            arrayList.add(post);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
