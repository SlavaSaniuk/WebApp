package by.bsac.models;

public class UserWrapper {

    private User common_user;

    //Constructor
    public UserWrapper(User a_user) {

        //Mapping
        this.common_user = a_user;

    }

    //Methods
    public String getFullName() {
        return this.common_user.getUserDetail().getfName() + this.common_user.getUserDetail().getlName();
    }

}
