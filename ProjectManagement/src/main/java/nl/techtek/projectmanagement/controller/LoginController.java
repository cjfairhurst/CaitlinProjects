package nl.techtek.projectmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import nl.techtek.projectmanagement.util.TechtekUtils;
import nl.techtek.projectmanagement.dao.UserDAO;
import nl.techtek.projectmanagement.model.User;
import nl.techtek.projectmanagement.util.CONSTANTS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login - Register - Verify - Recover
 *
 * @author Caitlin
 */
@Controller
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/")
    public ModelAndView login() throws Exception {
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
            model.addObject("errorMessage", CONSTANTS.REQUIRED_FIELDS_MISSING);
            return model;
        }

        //query user based on username and password
        User user = userDAO.getUser(username, password);

        //check if user exists
        if (user == null) {
            //return to login page with error message
            ModelAndView model = new ModelAndView("login");
            model.addObject("errorMessage", "Username or password is incorrect.");
            return model;
        } else if (!user.getIsVerified().equalsIgnoreCase("y")) {
            user.getEmail();
            //TODO: Send email.
            //TODO: Generate verifyKey and save to database.
            ModelAndView model = new ModelAndView("verify");
            return model;
        } else {
            //proceed to index page
            ModelAndView model = new ModelAndView("index");
            model.addObject("firstName", user.getFirstName());
            return model;
        }
    }

    @RequestMapping("/verifyAccountRequest")
    public ModelAndView verifyAccountRequest(HttpServletRequest request) {
        String verifyKey = (String) request.getParameter("verifyKey");
        //TODO:check user verifyKey in database
        //TODO:compare keys
        //TODO:override password

        return new ModelAndView();
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        ModelAndView model = new ModelAndView("register");
        return model;
    }

    @RequestMapping("/createAccountRequest")
    public ModelAndView createAccountRequest(HttpServletRequest request) throws Exception {
        //checks if required fields are missing
        if (TechtekUtils.checkNullOrEmpty(request.getParameter("username"))
                || TechtekUtils.checkNullOrEmpty(request.getParameter("password"))
                || TechtekUtils.checkNullOrEmpty(request.getParameter("email"))
                || TechtekUtils.checkNullOrEmpty(request.getParameter("firstName"))
                || TechtekUtils.checkNullOrEmpty(request.getParameter("lastName"))) {
            ModelAndView model = new ModelAndView("register");
            model.addObject("errorMessage", CONSTANTS.REQUIRED_FIELDS_MISSING);
            model.addObject("username", request.getParameter("username"));
            model.addObject("email", request.getParameter("email"));
            model.addObject("firstName", request.getParameter("firstName"));
            model.addObject("middleName", request.getParameter("middleName"));
            model.addObject("lastName", request.getParameter("lastName"));
            model.addObject("company", request.getParameter("company"));
            model.addObject("street", request.getParameter("street"));
            model.addObject("city", request.getParameter("city"));
            model.addObject("postcode", request.getParameter("postcode"));
            model.addObject("region", request.getParameter("region"));
            model.addObject("country", request.getParameter("country"));
            model.addObject("telephone", request.getParameter("telephone"));
            model.addObject("mobile", request.getParameter("mobile"));
            return model;
        }

        //checks if username already exists
        if (userDAO.userExists((String) request.getParameter("username")) == null) {
            //creatures user based on input
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

            //saves user
            userDAO.saveOrUpdate(user);

            //returns to login
            ModelAndView model = new ModelAndView("login");
            model.addObject("confirmationMessage", "Account Created");
            model.addObject("username", request.getParameter("username"));
            return model;
        } else {
            //username already in use, returns to register
            ModelAndView model = new ModelAndView("register");
            model.addObject("errorMessage", "This username is already in use.");
            model.addObject("username", request.getParameter("username"));
            model.addObject("email", request.getParameter("email"));
            model.addObject("firstName", request.getParameter("firstName"));
            model.addObject("middleName", request.getParameter("middleName"));
            model.addObject("lastName", request.getParameter("lastName"));
            model.addObject("company", request.getParameter("company"));
            model.addObject("street", request.getParameter("street"));
            model.addObject("city", request.getParameter("city"));
            model.addObject("postcode", request.getParameter("postcode"));
            model.addObject("region", request.getParameter("region"));
            model.addObject("country", request.getParameter("country"));
            model.addObject("telephone", request.getParameter("telephone"));
            model.addObject("mobile", request.getParameter("mobile"));
            return model;
        }
    }

    @RequestMapping("/recover")
    public ModelAndView recover() {
        ModelAndView model = new ModelAndView("recover");
        return model;
    }

    @RequestMapping("/recoverAccountRequest")
    public ModelAndView recoverAccountRequest(HttpServletRequest request) {
        //check if username is empty
        if (TechtekUtils.checkNullOrEmpty((String) request.getParameter("username"))) {
            ModelAndView model = new ModelAndView("recover");
            model.addObject("errorMessage", CONSTANTS.REQUIRED_FIELDS_MISSING);
            return model;
        }

        //retrieve user from database
        User user = userDAO.userExists((String) request.getParameter("username"));
        if (user != null) {
            user.getEmail();
            //TODO: Send mail.
            //TODO: Generate recoveryKey and save to database.

            ModelAndView model = new ModelAndView("recoverKey");
            return model;
        } else {
            ModelAndView model = new ModelAndView("recover");
            model.addObject("errorMessage", "This username does not exist.");
            return model;
        }
    }

    @RequestMapping("/overridePasswordRequest")
    public ModelAndView overridePasswordRequest(HttpServletRequest request) {
        String recoveryKey = (String) request.getParameter("recoveryKey");
        //TODO:check user recoverKey in database
        //TODO:compare keys
        //TODO:override password

        return new ModelAndView();
    }

}
