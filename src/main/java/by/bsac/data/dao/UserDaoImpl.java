package by.bsac.data.dao;

import by.bsac.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //Persistence bean


    @PersistenceContext
    private EntityManager em;


    @Override
    public long create(User a_user) {
        return 0;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long a_id) {
        return null;
    }

    @Override
    public User findByEmail(String a_email) {
        return null;
    }

    @Override
    public void update(User a_user) {

    }

    @Override
    public void delete(User a_user) {

    }

}
