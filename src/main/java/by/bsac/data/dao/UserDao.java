package by.bsac.data.dao;

import by.bsac.models.User;

import java.util.List;

/**
 *
 */
public interface UserDao {

    //Create
    void create(User a_user);

    //Read
    List<User> findAll();
    List<User> findById(long a_id);
    List<User> findByEmail(String a_email);

    //Update
    void update(User a_user);

    //Delete
    void delete(User a_user);

}
