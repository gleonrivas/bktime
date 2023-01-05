package com.gleon.bktime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "payroll")
@Getter // GETTERS.
@Setter // SETTERS.
@AllArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 10)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "from_when")
    private LocalDate fromWhen;

    @Column(name = "to_when")
    private LocalDate toWhen;

    @Column(name = "begining_payroll_date")
    private LocalDate beginingPayrollDate;

    @Column(name = "finish_payroll_date")
    private LocalDate finishPayrollDate;

    @Column(name = "hour_price", nullable = false)
    private Double hourPrice;

    @Column(name = "additional_hour_price", nullable = false)
    private Double additionalHourPrice;

    @Column(name = "summer_pay")
    private Double summerPay;

    @Column(name = "winter_pay")
    private Double winterPay;

    //RELACION CON EL MODELO USER UNIDA CON LA FK USERID.
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @OneToMany(
            mappedBy = "payroll_Id",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Schedule> schedules = new HashSet<>();

    public Payroll() {

    }

    @Override
    public String toString() {
        return "Payroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fromWhen=" + fromWhen +
                ", toWhen=" + toWhen +
                ", beginingPayrollDate=" + beginingPayrollDate +
                ", finishPayrollDate=" + finishPayrollDate +
                ", hourPrice=" + hourPrice +
                ", additionalHourPrice=" + additionalHourPrice +
                ", summerPay=" + summerPay +
                ", winterPay=" + winterPay +
                ", userId=" + userId +
                ", schedules=" + schedules +
                '}';
    }

    //EQUALS Y HASHCODE.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payroll payroll = (Payroll) o;
        return id != null && Objects.equals(id, payroll.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
