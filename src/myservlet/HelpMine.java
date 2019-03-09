package myservlet;

import mybean.data.Login;
import mybean.data.PersonalInfo;
import myutil.CommonHelper;
import myutil.DatabaseHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelpMine extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
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
        CommonHelper.getNums(request, dh, account, pi);

        request.setAttribute("personalInfo", pi);

        request.getRequestDispatcher("mine.jsp").forward(request, response);
    }


}
