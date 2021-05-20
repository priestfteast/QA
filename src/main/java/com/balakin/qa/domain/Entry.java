package com.balakin.qa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Project project;

    @Lob
    private String payload;

    @ManyToOne
    @JoinColumn(name="operator_id", nullable=false)
    private Operator operator;

    @ManyToOne
    @JoinColumn(name="auditor_id", nullable=false)
    private User auditor;

    private String fileName;

    private int totalPoints;

    private LocalDate recordDate;

    private LocalDate checkDate;

    private String logs;


}
