
package nl.techtek.projectmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import nl.techtek.projectmanagement.config.TechtekUtils;
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
public class LoginController {
    
    @Autowired
    private UserDAO userDAO;
    
    @RequestMapping("/")
    public ModelAndView handleRequest() throws Exception {
        ModelAndView model = new ModelAndView("login");
        return model;
    }
    
    @RequestMapping("/loginRequest")
    public ModelAndView loginRequest(HttpServletRequest request) throws Exception {
        //retrieve username and password from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //check for empty values
        if (TechtekUtils.checkNullOrEmpty(username) || TechtekUtils.checkNullOrEmpty(password)) {
            //return to login page with error message
            ModelAndView model = new ModelAndView("login");
            model.addObject("errorMessage", "Required Fields Are Empty");
            return model;
        }
        
        //query user based on username and password
        User user = userDAO.getUser(username, password);

        //check if user exists
        if (user == null) {
            //return to login page with error message
            ModelAndView model = new ModelAndView("login");
            model.addObject("errorMessage", "Username or Password Is Incorrect");
            return model;
        } else {
            //proceed to index page
            ModelAndView model = new ModelAndView("index");  
            model.addObject("firstName", user.getFirstName());
            return model;
        } 
    }
    
    @RequestMapping("/createAccountRequest")
    public ModelAndView createAccountRequest(HttpServletRequest request) throws Exception {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setPermissions("basic");
        user.setFirstName(request.getParameter("firstName"));
        user.setMiddleName(request.getParameter("middleName"));
        user.setLastName(request.getParameter("lastName"));
        user.setCompany(request.getParameter("company"));
        user.setStreet(request.getParameter("street"));
        user.setCity(request.getParameter("city"));
        user.setPostcode(request.getParameter("postcode"));
        user.setRegion(request.getParameter("region"));
        user.setCountry(request.getParameter("country"));
        user.setTelephone(request.getParameter("telephone"));
        user.setMobile(request.getParameter("mobile"));
        user.setEmail(request.getParameter("email"));
        
        userDAO.saveOrUpdate(user);
        
        ModelAndView model = new ModelAndView("login");
        model.addObject("confirmationMessage", "Account Created");
        return model;
    }
    
}
