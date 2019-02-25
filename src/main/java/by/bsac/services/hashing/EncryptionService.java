package by.bsac.services.hashing;

import java.security.SecureRandom;

/**
 *  This interface is contract between different encryption algorithm.
 */
public interface EncryptionService {

    /** Interface methods */
    byte[] encrypt(String msg, byte[] pass_salt);

    default byte[] getSalt(int number_of_bytes) {

        //Create secure random generator
        SecureRandom sr = new SecureRandom();

        //Create empty byte array with given array length
        byte[] generated_bytes = new byte[number_of_bytes];

        //Fill array with generated random bytes
        sr.nextBytes(generated_bytes);

        //Return statement
        return generated_bytes;


    }



}
