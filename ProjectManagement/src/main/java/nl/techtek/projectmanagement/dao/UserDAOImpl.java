package nl.techtek.projectmanagement.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import nl.techtek.projectmanagement.model.User;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Caitlin
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<User> list() {
        return getEntityManager().createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        getEntityManager().persist(user);
    }

    @Override
    @Transactional
    public User get(int id) {
        return getEntityManager().find(User.class, id);
    }
    
    @Override
    public User getUser(String username, String password) {
        return getEntityManager().createQuery("FROM User u WHERE u.username = :username AND u.password = :password", User.class).getSingleResult();
    }

    @Override
    public void delete(int id) {
        getEntityManager().remove(id);
    }

}
