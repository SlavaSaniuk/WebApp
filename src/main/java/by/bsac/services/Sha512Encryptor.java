package by.bsac.services;


import by.bsac.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
@Component("sha-512encryptor")
public class Sha512Encryptor extends AbstractEncryptionService {

    public static final String HASH_FUNCTION = "SHA-512";

    @Override
    public byte[] encrypt(String msg, byte[] a_salt) {

        //Convert password string to byte array
        byte[] password_bytes = msg.getBytes();

        //Clear variable in operating memory
        msg = null;

        //Concatenate two byte arrays
        //salt + password
        byte[] password_with_salt = new byte[password_bytes.length + a_salt.length];

        System.arraycopy(a_salt, 0, password_with_salt, 0, a_salt.length);
        System.arraycopy(password_bytes, 0, password_with_salt, a_salt.length, password_bytes.length);

        //Encrypt password
        try {

            //Create message digest object
            MessageDigest md = MessageDigest.getInstance(HASH_FUNCTION);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return new byte[];

    }

}
