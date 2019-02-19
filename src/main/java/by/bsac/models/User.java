package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull(message = "Email address must not be empty")
    @Min(4)
    private String userEmail;

    @Column(name = "user_pass")
    @NotNull(message = "Password must not be empty")
    @Size(min = 8, max = 30)
    private String userPass;

    //Constructors

    /**
     * Default constructor. Used by hibernate to create new Object from relation form.
     */
    public User() {}

    //Getters and setters

    public long getUserId() {
        return user_id;
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
        result = (int) (prime * result + (this.getUserId()));

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
        return this.user_id == compared_user.getUserId();

    }

    @Override
    public String toString() {

        return this.getUserId() +": " +this.getUserEmail();

    }

}
