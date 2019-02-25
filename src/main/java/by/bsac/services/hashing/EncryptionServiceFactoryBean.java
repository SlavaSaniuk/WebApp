package by.bsac.services.hashing;

import by.bsac.exceptions.WebAppException;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 *
 */
public class EncryptionServiceFactoryBean extends AbstractFactoryBean<EncryptionService> {


    private HashFunctions hash_function;

    public void setHashFunction(HashFunctions function) {
        this.hash_function = function;
    }

    @Override
    public Class<?> getObjectType() {
        return EncryptionService.class;
    }

    @Override
    protected EncryptionService createInstance() throws Exception {

        //Create encryption service bean:
        EncryptionService enc = null;

        switch (this.hash_function) {
            case SHA512:
                enc = new Sha512Encryptor();
                break;
            default:
                throw new WebAppException("Not supposed hash functions found.");
        }

        return enc;
    }


}
