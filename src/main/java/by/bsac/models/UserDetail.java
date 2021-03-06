package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 */
@Entity
@Proxy(lazy = false)
@Table(name = "user_detail")
public class UserDetail implements Serializable {

    /*
        Global class variables
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    @Max(99999999)
    private long detail_id;

    @Column(name = "fname")
    @NotNull(message = "Name must not be empty")
    @Size(min = 2, max = 20,  message = "Name must be from 2 to 20 characters.")
    private String fName;

    @Column(name = "lname")
    @NotNull(message = "Last name must not be empty")
    @Size(min = 2, max = 20,  message = "Last name must be from 2 to 20 characters.")
    private String lName;

    @Column(name = "birth_date")
    @NotNull(message = "Birth date must not be empty")
    private LocalDate birthDate;

    @Column(name = "age")
    @NotNull
    private int age;

    @Column(name = "sex")
    private char sex;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @OneToOne( mappedBy = "user_detail" )
    private User user_owner;

    /*
        Constructors
     */

    //Default constructor
    public UserDetail() {

    }

    /*
        Class methods
     */

    //  Getters and setters
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {

        //Create new Local data object
        LocalDate bd = LocalDate.parse(birthDate);

        //Mapping
        this.birthDate = bd;

        //Calculate users age
        //Get period between given date and current date
        Period full_age = Period.between(bd, LocalDate.now());

        //Set user age
        this.setAge(full_age.getYears());

    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getDetailId() {
        return this.detail_id;
    }

    public User getUserOwner() {
        return this.user_owner;
    }



    //Override java.lang.Object methods
    @Override
    public String toString() {
        return this.detail_id +": " +fName + " " +lName + " : " +birthDate.toString() +" - " +age +
                ": " +sex +": " +country + ", " +city;
    }

}
