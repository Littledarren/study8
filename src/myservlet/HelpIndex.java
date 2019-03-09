package myservlet;


import mybean.data.Index;
import mybean.data.Login;
import mybean.data.Post;
import mybean.data.dbModel.Message;
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

        //2. get predefined_classification and num *****this "type" is predefined_classification
        String typeString = req.getParameter("type");
        short type = predefined.getOrDefault(typeString, (short) 8);

        short tempType = 8;
        int tempNum = 5;
        try {

            tempType = Short.valueOf(req.getParameter("postType"));
            tempNum = Integer.valueOf(req.getParameter("postNum"));
        } catch (NumberFormatException e) {
            tempType = type;
            //            System.out.println("tempType:" + tempType);
        }
        final int postNum = tempNum;
        final short postType = tempType;

        //3.set bean
        Index index = new Index();
        req.setAttribute("index", index);

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
                    CommonHelper.getPostsFromSQLstatement(arrayList, ps);
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
                    ps.setInt(2, postNum - 5);
                    ps.setInt(3, 5);
                    CommonHelper.getPostsFromSQLstatement(arrayList, ps);
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
                            "where share_type=0 and predefined_classification=? " +
                            "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id " +
                            "limit ?, ?");
                    ps.setShort(1, postType);
                    ps.setInt(2, postNum - 5);
                    ps.setInt(3, 5);
                    CommonHelper.getPostsFromSQLstatement(arrayList, ps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return arrayList.toArray(new Post[0]);
            });
        }

        index.setPosts(posts);
        index.setPostType(postType);
        getMessagesFromDatabase(databaseHelper, index, login);


        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("title") == null) {
            this.doGet(req, resp);
            return;
        }
        final String title = req.getParameter("title");
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getServletContext());
        Post[] posts = (Post[]) databaseHelper.execSql(con -> {
            ArrayList<Post> arrayList = new ArrayList<>();
            try {
                PreparedStatement ps = con.prepareStatement("select * " +
                        "from post " +
                        "where title like ? and share_type=0 " +
                        "order by 0.1 * numReads + 5 * numLikes + numComments + 10 * numFavourites DESC, post_id "
                );
                ps.setString(1, "%" + title + "%");
                CommonHelper.getPostsFromSQLstatement(arrayList, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            return arrayList.toArray(new Post[0]);
        });

        Index index = new Index();
        req.setAttribute("index", index);
        index.setPosts(posts);
        Login login = CommonHelper.getLoginBean(req);
        getMessagesFromDatabase(databaseHelper, index, login);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

    private void getMessagesFromDatabase(DatabaseHelper databaseHelper, Index index, Login login) {
        if (login.isLogined()) {
            String mail = login.getAccount();
            Message[] messages = (Message[]) databaseHelper.execSql(con -> {
                PreparedStatement preparedStatement = null;
                ArrayList<Message> arrayList = new ArrayList<>();
                try {
                    preparedStatement = con.prepareStatement("select * from message where mail=? order by recvtime");
                    preparedStatement.setString(1, mail);
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        Message message = new Message();
                        message.setMid(rs.getLong(1));
                        message.setMail(rs.getString(2));
                        message.setMsg_content(rs.getString(3));
                        message.setRecvtime(rs.getTimestamp(4));
                        arrayList.add(message);
                    }
                    return arrayList.toArray(new Message[0]);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Message[0];
                }

            });
            index.setMessages(messages);
        }
    }
}
