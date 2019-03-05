package myservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelpRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
            TODO
            get parameters
            check validation:
                1. check passwords
                2. check email not in database
            if ok:
                store in db
                -> registerSuccess.jsp
            else:
                request.setAttribute("success", "false");
                -> register.jsp
         */
    }
}
