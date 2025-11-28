-- src/main/resources/data.sql
-- DỮ LIỆU MẪU ĐẸP NHƯ ĐỀ THI T2404E – 27/12/2025

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM student_score_t;
DELETE FROM student_t;
DELETE FROM subject_t;

-- Reset auto increment
ALTER TABLE student_t AUTO_INCREMENT = 1;
ALTER TABLE subject_t AUTO_INCREMENT = 1;
ALTER TABLE student_score_t AUTO_INCREMENT = 1;

-- 1. Thêm môn học (có tín chỉ đúng như đề)
INSERT INTO subject_t (subject_code, subject_name, credit) VALUES
                                                               ('JAVA', 'Java Programming', 3),
                                                               ('WEB', 'Web Development', 4),
                                                               ('DBMS', 'Database Management', 3),
                                                               ('AI', 'Artificial Intelligence', 4),
                                                               ('MOB', 'Mobile Development', 3);

-- 2. Thêm sinh viên (mã đẹp, tên giống đề)
INSERT INTO student_t (student_code, full_name, address) VALUES
                                                             ('2007A1', 'Nguyễn Văn A', 'Hà Nội'),
                                                             ('2007A2', 'Trần Thị B', 'TP.HCM'),
                                                             ('2007A3', 'Lê Văn C', 'Đà Nẵng'),
                                                             ('2007A4', 'Phạm Thị D', 'Cần Thơ'),
                                                             ('2007A5', 'Hoàng Văn E', 'Hải Phòng');

-- 3. Nhập điểm – ĐÚNG Y CHANG BẢNG MẪU TRONG ĐỀ (Score1 = 8.0, Score2 = 8.0 → Grade A)
INSERT INTO student_score_t (student_id, subject_id, score1, score2) VALUES
                                                                         (1, 1, 8.0, 8.0),  -- Nguyễn Văn A - JAVA
                                                                         (1, 2, 8.0, 8.0),  -- Nguyễn Văn A - WEB
                                                                         (1, 3, 8.0, 8.0),  -- Nguyễn Văn A - DBMS

                                                                         (2, 1, 8.0, 8.0),  -- Trần Thị B - JAVA
                                                                         (2, 2, 8.0, 8.0),  -- Trần Thị B - WEB

                                                                         (3, 1, 8.0, 8.0),  -- Lê Văn C - JAVA
                                                                         (3, 4, 8.0, 8.0),  -- Lê Văn C - AI

                                                                         (4, 1, 8.0, 8.0),  -- Phạm Thị D - JAVA
                                                                         (4, 5, 8.0, 8.0),  -- Phạm Thị D - MOB

                                                                         (5, 1, 8.0, 8.0),  -- Hoàng Văn E - JAVA
                                                                         (5, 3, 8.0, 8.0);  -- Hoàng Văn E - DBMS

-- Bonus: Thêm vài điểm khác để test Grade đa dạng
INSERT INTO student_score_t (student_id, subject_id, score1, score2) VALUES
                                                                         (1, 4, 9.5, 9.0),  -- Nguyễn Văn A - AI → A (9.65)
                                                                         (2, 3, 7.0, 7.5),  -- Trần Thị B - DBMS → B (7.35)
                                                                         (3, 5, 5.5, 6.0),  -- Lê Văn C - MOB → C (5.85)
                                                                         (4, 2, 3.0, 4.0);  -- Phạm Thị D - WEB → F (3.7)