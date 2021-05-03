package com.balakin.qa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDate employementDate;

    private boolean fired;

    private LocalDate retirementDate;

    @Enumerated(value = EnumType.STRING)
    private Project project;

    @Enumerated(value = EnumType.STRING)
    private FireCause fireCause;

    @Override
    public String toString() {
        return "Operator: " +
                fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(id, operator.id) &&
                Objects.equals(fullName, operator.fullName) &&
                Objects.equals(employementDate, operator.employementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, employementDate);
    }

}