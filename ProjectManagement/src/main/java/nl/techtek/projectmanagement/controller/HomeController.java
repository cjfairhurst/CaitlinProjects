
package nl.techtek.projectmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import nl.techtek.projectmanagement.dao.UserDAO;
import nl.techtek.projectmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Caitlin
 */
@Controller
public class HomeController {
    
    @Autowired
    private UserDAO userDAO;
    
    @RequestMapping("/")
    public ModelAndView handleRequest() throws Exception {
        ModelAndView model = new ModelAndView("index");
        return model;
    }
    
    @RequestMapping("/login")
    public ModelAndView loginRequest(HttpServletRequest request) throws Exception {
        request.getSession().getAttribute("username");
        request.getSession().getAttribute("password");
        User user = userDAO.getUser((String) request.getSession().getAttribute("username"), (String) request.getSession().getAttribute("password"));
        ModelAndView model = new ModelAndView("");
        return model;
    }
    
}
