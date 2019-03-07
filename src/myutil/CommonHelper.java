package myutil;

import mybean.data.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonHelper {

    //get loginBean
    public static Login getLoginBean(HttpServletRequest req) {
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
        return loginBean;
    }

    //check email format
    public static boolean checkEmailFormat(String email) {
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return email.matches(pattern);
    }
}
