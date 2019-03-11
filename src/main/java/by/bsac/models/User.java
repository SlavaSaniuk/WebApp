package by.bsac.models;

import by.bsac.data.validation.Email;
import by.bsac.services.hashing.EncryptionServiceFactory;
import by.bsac.services.hashing.EncryptionServiceImpl;
import by.bsac.services.hashing.HashFunctions;
import by.bsac.util.Convertor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *  Class represent a common user object.
 *  Annotated with hibernate, JPA annotations to specific table, fields in database table.
 *  Used for object - relation mapping by hibernate.
 */
@Entity()
@Proxy(lazy = false) //Disable lazy initialization
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "user_email")
    @NotNull(message = "Email address must not be empty")
    @Email(message = "Entered email is invalid")
    private String userEmail;

    @Column(name = "user_pass")
    @Size(min = 8, message = "Password length must contain a minimum 8 character.")
    @NotNull(message = "Password must not be empty")
    private String userPass;

    @Column(name = "pass_salt")
    private String passSalt;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_detail", referencedColumnName = "detail_id", unique = true)
    private UserDetail user_detail;

    //Password encryption object:
    //'transient' - don't serializable
    private static transient EncryptionServiceImpl password_encryptor = EncryptionServiceFactory.getHashService(HashFunctions.SHA512);

    //Constructors

    /**
     * Default constructor. Used by hibernate to create new Object from relation form.
     */
    public User() {
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

        //Set user password
        this.userPass = user_pass;

    }

    public String getPassSalt() {
        return passSalt;
    }

    private void setPassSalt() {

        //Generate password salt
        byte[] generated_salt = password_encryptor.getSalt();

        //Convert to HEX string
        this.passSalt = Convertor.byteToHexForm(generated_salt);
    }

    public UserDetail getUserDetail() {
        return user_detail;
    }

    public void setUserDetail(UserDetail user_detail) {
        this.user_detail = user_detail;
    }


    /**
     * Hashing user password with given passwords salt.
     * Hashing by SHA-512 algorithm.
     */
    public static String hashPassword(String user_pass, String pass_salt) {

        //Encrypt user password
        byte[] hash_bytes = password_encryptor.encrypt(user_pass, pass_salt.getBytes());

        //Return password hash as String
        return Convertor.byteToHexForm(hash_bytes);

    }

    public void prepareToPersist() {

        //Generate password salt
        this.setPassSalt();

        //Hashing user password
        this.userPass = hashPassword(getUserPass(), getPassSalt());

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
        result = prime * result + ((this.getUserPass() == null) ? 0 : this.getUserPass().hashCode());

        return result;

    }

    @Override
    public boolean equals(Object obj) {

        //Check for reference on same memory cell
        if (obj == this) return true;

        //Check for null and same types
        if (obj == null || obj.getClass() != this.getClass()) return false;

        //Cast type:
        User compared_user = (User) obj;

        //Compare ID value
        return this.user_id == compared_user.getUserId();

    }

    @Override
    public String toString() {

        return this.getUserId() + ": " + this.getUserEmail();

    }

}