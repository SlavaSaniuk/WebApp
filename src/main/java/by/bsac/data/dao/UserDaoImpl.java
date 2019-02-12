package by.bsac.data.dao;

import by.bsac.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class UserDaoImpl implements UserDao {


    private SessionFactory session_factory; //Session factory object

    public void setSessionFactory(SessionFactory a_session_factory) {

        //Mapping session factories:\
        this.session_factory = a_session_factory;

    }

    /**
     *
     * @param a_user - user object with specified email and password fields.
     */
    @Override
    public void create(User a_user) {

        Session session = this.session_factory.openSession();
        Transaction tr = session.beginTransaction();

        session.save(a_user);

        tr.commit();
        session.close();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findById(long a_id) {
        return null;
    }

    @Override
    public List<User> findByEmail(String a_email) {
        return null;
    }

    @Override
    public void update(User a_user) {

    }

    @Override
    public void delete(User a_user) {

    }


}
