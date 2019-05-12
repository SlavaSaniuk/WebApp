package by.bsac.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HibernateListCaster {

    private static Logger LOGGER = LoggerFactory.getLogger(HibernateListCaster.class);

    public static <T> List<T> castList(List a_list, Class a_class) {

        //Resulting array
        List<T> result = new ArrayList<>();

        //Check on existence
        //If null then throw new exception
        if (a_list == null) throw new NullPointerException();

        //Check size of list
        if (a_list.size() == 0) return result;

        //Check types of elements
        if (a_class.isAssignableFrom(a_list.get(0).getClass())) {
            for (Object obj : a_list) result.add((T) obj);
            return result;
        }else throw new ClassCastException("Given list cannot be casted to" +a_class.toString() +" class.");
    }
}
