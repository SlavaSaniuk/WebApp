package by.bsac.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 */
@Entity
@Proxy(lazy = false)
@Table(name = "user_detail")
public class UserDetail {

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

        //Split entered field on date fields(e.g. YEAR, DAY ..)
        String[] date_fields = birthDate.split("-");

        //Create new Local data object
        LocalDate bd = LocalDate.of(Integer.parseInt(date_fields[0]), Integer.parseInt(date_fields[1]), Integer.parseInt(date_fields[2]));

        //Mapping
        this.birthDate = bd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

}
