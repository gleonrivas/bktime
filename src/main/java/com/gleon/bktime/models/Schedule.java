package com.gleon.bktime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "schedule")
@Getter // GETTERS.
@Setter // SETTERS.
@AllArgsConstructor // CONSTRUCTORES COMPLETOS.
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 10)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "in_1")
    private Timestamp in1;

    @Column(name = "out_1")
    private Timestamp out1;

    @Column(name = "in_2")
    private Timestamp in2;

    @Column(name = "out_2")
    private Timestamp out2;

    @Column(name = "vacation")
    private Integer vacation;

    @Column(name = "rest")
    private Integer rest;

    @Column(name = "festive")
    private Integer festive;

    //RELACION CON EL MODELO USER UNIDA CON LA FK USERID.
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User userId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "payroll_id", nullable = false)
    private Payroll payrollId;

    public Schedule() {

    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", in1=" + in1 +
                ", out1=" + out1 +
                ", in2=" + in2 +
                ", out2=" + out2 +
                ", vacation=" + vacation +
                ", rest=" + rest +
                ", festive=" + festive +
                ", userId=" + userId +
                ", payrollId=" + payrollId +
                '}';
    }

    //EQUALS Y HASHCODE.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Schedule schedule = (Schedule) o;
        return id != null && Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
