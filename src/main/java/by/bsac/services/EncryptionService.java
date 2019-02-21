package by.bsac.services;

/**
 *  This interface is contract between different encryption algorithm.
 */
public interface EncryptionService {

    /** Interface methods */
    byte[] encrypt(String msg, byte[] a_salt);

    byte[] getSalt(int number_of_bytes);

}
