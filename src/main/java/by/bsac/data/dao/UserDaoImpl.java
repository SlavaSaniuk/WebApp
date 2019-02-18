package by.bsac.data.dao;

import by.bsac.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //Persistence bean
    @PersistenceContext
    private EntityManager em;

    @Override
    public long create(User a_user) {

        //Create transaction object:
        EntityTransaction tr = em.getTransaction();

        //Begin transaction:
        tr.begin();

        //Persist given object to database
        em.persist(a_user);

        //Create query
        Query get_generated_id = em.createQuery("FROM User user WHERE user.userEmail = :email");

        //Set parameters to query:
        get_generated_id.setParameter("email", a_user.getUserEmail());

        //Execute query:
        long generated_id = (long) get_generated_id.getSingleResult();

        //Commit transaction
        tr.commit();

        //Return statement
        return generated_id;

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
