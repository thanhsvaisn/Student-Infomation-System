package org.example.sisead.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "student_score_t")
@Data
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentScoreId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "Vui lòng chọn sinh viên")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @NotNull(message = "Vui lòng chọn môn học")
    private Subject subject;

    @NotNull(message = "Điểm thành phần không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm từ 0-10")
    @DecimalMax(value = "10.0", message = "Điểm không quá 10")
    private BigDecimal score1;

    @NotNull(message = "Điểm thi không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm từ 0-10")
    @DecimalMax(value = "10.0", message = "Điểm không quá 10")
    private BigDecimal score2;

    @Transient
    public double getTotalScore() {
        if (score1 == null || score2 == null) return 0.0;
        return 0.3 * score1.doubleValue() + 0.7 * score2.doubleValue();
    }

    @Transient
    public String getGrade() {
        double total = getTotalScore();

        if (total >= 8.0 && total <= 10.0) {
            return "A";
        } else if (total >= 6.0 && total <= 7.9) {
            return "B";
        } else if (total >= 4.0 && total <= 5.9) {
            return "D";
        } else {
            return "F"; // < 4.0
        }
    }
}