package by.bsac.services.hashing;

import java.security.SecureRandom;

/**
 *
 */
public abstract class AbstractEncryptionService implements EncryptionService {

    @Override
    public byte[] getSalt(int number_of_bytes) {

        //Create secure random generator
        SecureRandom sr = new SecureRandom();

        //Create empty byte array with given array lenght
        byte[] generated_bytes = new byte[number_of_bytes];

        //Fill array with generated random bytes
        sr.nextBytes(generated_bytes);

        //Return statement
        return generated_bytes;
    }
}
