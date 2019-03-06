package myservlet;


import mybean.data.Index;
import myutil.CommonHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HelpIndex extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* TODO

            judge parameter type
1
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

        CommonHelper.loginHelp(req);

        Index index = new Index();
        index.setMessageName(new String[]{"aaa", "bbb", "ccc"});
        req.setAttribute("index", index);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
