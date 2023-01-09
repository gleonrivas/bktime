package com.gleon.bktime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter // GETTERS.
@Setter // SETTERS.
@AllArgsConstructor // CONSTRUCTORES COMPLETOS.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 10)
    private Integer id;

    @Column(name = "user_img", length = 150)
    private String userImg;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_type", nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "account_state", nullable = false, length = 1)
    private Integer accountState;

    //RELACION CON EL CAMPO USERID DEL MODELO PAYROLL.
    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Set<Payroll> payrolls = new HashSet<>();

    //RELACION CON EL CAMPO USERID DEL MODELO SCHEDULE.
    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Set<Schedule> schedules = new HashSet<>();

    public User() {

    }

    public User(Integer id, String description, String firstName, UserType user, String firstName1, String lastName, String phoneNumber, String emailAddress, String description1) {
    }

    public User(User userById) {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userImg='" + userImg + '\'' +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountState=" + accountState +
                ", payrolls=" + payrolls +
                ", schedules=" + schedules +
                '}';
    }

    //EQUALS Y HASHCODE.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
