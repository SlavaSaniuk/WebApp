package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 *  Class represent a common user object.
 *  Annotated with hibernate, JPA annotations to specific table, fields in database table.
 *  Used for object - relation mapping by hibernate.
 */
@Entity
@Proxy(lazy = false) //Disable lazy initialization
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_pass")
    private String userPass;

    //Constructors

    /**
     * Default constructor. Used by hibernate to create new Object from relation form.
     */
    public User() {

    }

    /**
     * Constructor create new user object, with specified email and password.
     * Used to save information about current user to database.
     * @param a_email - user email address.
     * @param a_password - user password.
     */
    public User(String a_email, String a_password) {
        super();
        this.setUserEmail(a_email);
        this.setUserPass(a_password);
    }



    //Getters and setters

    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String user_email) {
        this.userEmail = user_email;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String user_pass) {
        this.userPass = user_pass;
    }

    //Override java.lang.Object methods

    @Override
    public int hashCode() {

        final int prime = 31;

        int result = 1;

        //ternary operator
        result = (int) (prime * result + ((Long.valueOf(this.getUserId()) == null) ? 0 : this.getUserId() ));

        //ternary operator
        result = prime * result + ((this.getUserEmail() == null) ? 0 : this.getUserEmail().hashCode());

        //ternary operator
        result = prime * result +((this.getUserPass() == null) ? 0 : this.getUserPass().hashCode());

        return result;

    }

    @Override
    public boolean equals(Object obj) {

        //Check for reference on same memory cell
        if(obj == this) return true;

        //Check for null and same types
        if (obj == null || obj.getClass() != this.getClass()) return false;

        //Cast type:
        User compared_user = (User) obj;

        //Compare ID value
        if (this.user_id == compared_user.getUserId()) return true;
        else return false;

    }

    @Override
    public String toString() {

        return this.getUserId() +": " +this.getUserEmail();

    }

    /**
     * Check on emptiness user object.
     * @return - true if user object is not empty, in otherwise return false.
     */
    public boolean isEmpty() {

        return this.getUserEmail() != null;

    }
}
