package by.bsac.data.dao;

import by.bsac.models.User;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 *  Implementation of data access to user object ({@link by.bsac.data.dao.UserDao} User DAO).
 */
@Repository
public class UserDaoImpl implements UserDao, Serializable {

    //Session factory bean.
    private SessionFactory session_factory;

    /**
     * Create new UserDao implementation object.
     * @param a_session_factory - {@link org.hibernate.SessionFactory>  uses to get sessions to implements DAO methods.
     */
    public UserDaoImpl(SessionFactory a_session_factory) {

        //Mapping session factories
        this.session_factory = a_session_factory;

    }

    /**
     * Save specifying user object in database. Return generated user ID.
     * @param a_user - {@link by.bsac.models.User} object with specified email and password fields.
     * @return - Generated user ID by hibernate generator strategy.
     * @throws org.hibernate.HibernateException - if session couldn't be opened.
     */
    @Override
    public long create(User a_user) throws HibernateException {

        //Create session and transaction objects
        Session session = session_factory.openSession();
        Transaction tr = null;

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        long generated_id = 0;

        try {

            tr = session.beginTransaction();

            //Persist user object in database.
            //Get generated user ID.
             generated_id = (long) session.save(a_user);

            //Applying transaction
            tr.commit();

        }catch (HibernateException exc) {
            if (tr!=null) tr.rollback();
        }finally {
            session.close();
        }

        if (generated_id == 0) throw new HibernateException("User cannot be saved.");

        //Return statement
        return generated_id;

    }

    /**
     * Find and return all user rows as User objects.
     * @return - {@link java.util.List<User>} - all users registered in database.
     * @throws org.hibernate.HibernateException - if session couldn't be opened.
     */
    @Override
    public List<User> findAll() throws HibernateException {

        //Create session and transaction objects
        Session session = session_factory.openSession();

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        Transaction tr = session.beginTransaction();

        //Create hibernate query
        Query<User> find_all_query = session.createQuery("FROM User user", User.class);

        //Execute query
        List<User> all_users = find_all_query.list();

        //Applying transaction
        tr.commit();

        //Close session
        session.close();

        //Return statement
        return all_users;
    }

    /**
     * Find user row in database by user ID. If user row founded - return appropriate user object,
     * otherwise return null.
     * @param a_id - {@link java.lang.Integer} user identifier (user_id).
     * @return - {@link by.bsac.models.User} object, if user created in database, else return {@code null}.
     * @throws org.hibernate.HibernateException - if session couldn't be opened.
     */
    @Override
    public User findById(long a_id) throws HibernateException {

        //Create session and transaction objects
        Session session = session_factory.openSession();

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        Transaction tr = session.beginTransaction();

        //Create empty user object
        User founded_user;

        //Get user object from database
        founded_user = session.get(User.class, a_id);

        //Commit transaction
        tr.commit();

        //Close session
        session.close();

        //Return statement
        return founded_user;
    }

    /**
     * Find user row in database by user ID. If user row founded - return appropriate user object,
     * otherwise return null.
     * @param a_email - user email address.
     * @return - {@link by.bsac.models.User} object, if user created in database, else return {@code null}.
     * @throws org.hibernate.HibernateException - if session couldn't be opened,
     * or  if there is more than one matching result.
     */
    @Override
    public User findByEmail(String a_email) throws HibernateException {

        //Create session and transaction objects
        Session session = session_factory.openSession();

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        Transaction tr = session.beginTransaction();

        //Create hibernate query
        Query<User> find_by_email = session.createQuery("FROM User user WHERE user.userEmail = :e_mail", User.class);

        User founded_user = find_by_email.uniqueResult();

        //Commit transaction
        tr.commit();

        //Close session
        session.close();

        //Return statement
        return founded_user;

    }

    @Override
    public void update(User a_user) {

        Session session = session_factory.openSession();
        Transaction tr = null;

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        try {

            tr = session.beginTransaction();

            session.update(a_user);

            tr.commit();
        }
        catch (Exception e) {
            if (tr!=null) tr.rollback();
        }
        finally {
            session.close();
        }

    }

    @Override
    public void delete(User a_user) {

        Session session = session_factory.openSession();
        Transaction tr = null;

        //Check whether the session is opened
        if (session == null) throw new HibernateException("Session couldn't be opened.");

        try {

            tr = session.beginTransaction();

            session.delete(a_user);

            tr.commit();
        }
        catch (Exception e) {
            if (tr!=null) tr.rollback();
        }
        finally {
            session.close();
        }

    }


}
