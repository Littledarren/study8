package myutil;

import mybean.data.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonHelper {

    //load loginBean
    public static void loginHelp(HttpServletRequest req) {
        Login loginBean = null;
        HttpSession session = req.getSession(true);
        try {
            loginBean = (Login) session.getAttribute("login");
            if (loginBean == null) {
                loginBean = new Login();
                session.setAttribute("login", loginBean);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            loginBean = new Login();
            session.setAttribute("login", loginBean);
        }
    }
}
