package org.example.sisead.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_t", uniqueConstraints = @UniqueConstraint(name = "UK_student_code" , columnNames = "student_code"))
@Data
@ToString(exclude = "scores") // Tránh StackOverflow khi in log
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "student_code", nullable = false, length = 20)
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 6, max = 20, message = "Mã sinh viên từ 6-20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Mã sinh viên chỉ được chứa chữ cái và số, không dấu cách")
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 100)
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 5, max = 100, message = "Họ tên từ 5-100 ký tự")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Họ tên chỉ được chứa chữ cái và dấu cách")
    private String fullName;

    @Column(length = 255)
    @Size(max = 255, message = "Địa chỉ không được quá 255 ký tự")
    private String address;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<StudentScore> scores = new ArrayList<>();

    public void addScore(StudentScore score) {
        scores.add(score);
        score.setStudent(this);
    }

    public void removeScore(StudentScore score) {
        scores.remove(score);
        score.setStudent(null);
    }
}