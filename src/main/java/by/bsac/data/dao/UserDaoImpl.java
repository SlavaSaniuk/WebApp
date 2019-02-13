package by.bsac.data.dao;

import by.bsac.models.User;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Repository
public class UserDaoImpl implements UserDao, Serializable {


    private SessionFactory session_factory; //Session factory object

    public void setSessionFactory(SessionFactory a_session_factory) {

        //Mapping session factories:\
        this.session_factory = a_session_factory;

    }


    @Override
    public long create(User a_user) {

        //Create session and transaction objects
        Session session = null;
        Transaction tr = null;

        long generated_id = 0;

        try {

            session = this.session_factory.openSession();
            tr = session.beginTransaction();

            //Save user object in database
            //And get generated user ID
            generated_id = (long) session.save(a_user);

            //Applying transaction
            tr.commit();

        }catch (HibernateException exc) {

            //Cancel transaction
            tr.rollback();

        }finally {
            session.close();
        }


        //Return statement
        return generated_id;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long a_id) {

        //Create session and transaction objects
        Session session;
        Transaction tr;

        //Create empty user object
        User founded_user;

        try {

            session = this.session_factory.openSession();
            tr = session.beginTransaction();

            //Get user object from database
            founded_user = session.get(User.class, a_id);

        }catch (HibernateException exc) {

        }

        return null;
    }

    @Override
    public User findByEmail(String a_email) {

        //Create session and transaction objects
        Session session;
        Transaction tr;

        // Create empty user object:
        User founded_user;

        //Open session
        session = this.session_factory.openSession();

        //Begin transaction
        tr = session.beginTransaction();

        //Create query object
        Query hql_query = session.createQuery("FROM User user WHERE user.userEmail = :specified_email");

        //Set parameter to query
        hql_query.setParameter("specified_email", a_email);

        //Execute query
        founded_user = (User) hql_query.uniqueResult();

        //Commit transaction, close session
        tr.commit();
        session.close();

        //Return statement
        return founded_user;
    }

    @Override
    public void update(User a_user) {

    }

    @Override
    public void delete(User a_user) {

    }


}
