package nl.techtek.projectmanagement.dao;

import java.util.List;
import nl.techtek.projectmanagement.model.User;

/**
 *
 * @author Caitlin
 */
public interface UserDAO {

    public List<User> list();

    public User get(int id);

    public void saveOrUpdate(User user);

    public void delete(int id);
    
    public User getUser(String username, String password);
    
    public User userExists(String username);

}
