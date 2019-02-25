package by.bsac.services.hashing;

import by.bsac.exceptions.NotSameTypesException;
import by.bsac.util.CommonUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionServiceImpl implements EncryptionService {

    private String hash_function;
    private int hash_salt_length;

    EncryptionServiceImpl(String hash_function, int hash_salt_length) {
        this.hash_function = hash_function;
        this.hash_salt_length = hash_salt_length;
    }

    @Override
    public byte[] encrypt(String msg, byte[] pass_salt) {

        //Concatenate two byte arrays
        //salt + password
        byte[] password_with_salt;

        try {

            password_with_salt = CommonUtils.concatenateTwoArrays(msg.getBytes(), pass_salt);

        } catch (NotSameTypesException exc) {
            exc.printStackTrace();
            return null;
        }

        byte[] resulting_hash;

        //Encrypt password
        try {

            //Create message digest object
            MessageDigest md = MessageDigest.getInstance(this.hash_function);

            //Hashing bytes
            resulting_hash  = md.digest(password_with_salt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            resulting_hash = new byte[password_with_salt.length];
        }

        return resulting_hash;

    }

    public byte[] getSalt() {
        return getSalt(hash_salt_length);
    }
}
