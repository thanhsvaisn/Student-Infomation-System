package org.example.sisead.repository;

import org.example.sisead.entity.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<StudentScore, Long> {}