package org.example.sisead.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subject_t", uniqueConstraints = @UniqueConstraint(name = "UK_subject_code" , columnNames = "subject_code"))
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(name = "subject_code", nullable = false)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    private int credit;
}