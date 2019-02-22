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
    @Size(max = 1)
    private char sex;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

}
