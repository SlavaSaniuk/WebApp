package by.bsac.models;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "user_pass")
    private String user_pass;

    //Constructors

    public User() {

    }

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
        return user_email;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public String getUserPass() {
        return user_pass;
    }

    public void setUserPass(String user_pass) {
        this.user_pass = user_pass;
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

        return this.user_id + ": " +this.user_email;

    }
}
