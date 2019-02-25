package by.bsac.services.hashing;


/**
 *
 */
public class EncryptionServiceFactory{

    public static EncryptionServiceImpl getHashService(HashFunctions hash_function){

        //Create encryption service bean:
        EncryptionServiceImpl enc;

        //Hash function and salt length constants
        int MD2_SALT_LENGTH = 16;
        String MD2 = "MD2";

        int MD5_SALT_LENGTH = 16;
        String MD5 = "MD5";

        int SHA512_SALT_LENGTH = 64;
        String SHA512 = "SHA-512";

        //Switch statement
        switch (hash_function) {
            case SHA512:
                enc = new EncryptionServiceImpl(SHA512, SHA512_SALT_LENGTH);
                break;
            case MD5:
                enc = new EncryptionServiceImpl(MD5, MD5_SALT_LENGTH);
                break;
            case MD2:
                enc = new EncryptionServiceImpl(MD2, MD2_SALT_LENGTH);
                break;
            default:
                enc = new EncryptionServiceImpl(MD5, MD5_SALT_LENGTH);
                break;
        }

        return enc;
    }


}
