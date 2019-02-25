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

    //Password encryption object:
    //'transient' - don't serializable
    private transient EncryptionServiceImpl password_encryptor = EncryptionServiceFactory.getHashService(HashFunctions.SHA512);

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

    public String getPassSalt() {
        return passSalt;
    }

    /**
     * Method generating random 128 byte string for column password salt 'pass_salt';
     * Uses for register user in database.
     * By this column definition (On class FIELD) JPA provider direct get/set values to object fields.
     */
    private void setPassSalt() {

        //Generate password salt
        byte[] generated_salt = this.password_encryptor.getSalt();

        //Convert to HEX string
        this.passSalt = Convertor.byteToHexForm(generated_salt);
    }

    /**
     * Hashing user password with selected hash function.
     * Method should be used after the user entered his password in clear form.
     */
    public void hashPassword() {

        //Get generated pass salt:
        String pass_salt = this.getPassSalt();

        //If pass salt is not generated, then generate pass salt
        if (pass_salt == null) {
            setPassSalt();
            pass_salt = getPassSalt();
        }

        //Encrypt user password
        byte[] hash_bytes = this.password_encryptor.encrypt(this.getUserPass(), pass_salt.getBytes());

        //Set user password to field
        this.userPass = Convertor.byteToHexForm(hash_bytes);

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
