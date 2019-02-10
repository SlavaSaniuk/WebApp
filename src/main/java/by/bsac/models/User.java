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
}
