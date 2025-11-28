package org.example.sisead.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sisead.entity.*;
import org.example.sisead.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final ScoreRepository scoreRepo;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        var pageable = PageRequest.of(page, 10);
        var studentPage = studentRepo.findAll(pageable);
        studentPage.getContent().forEach(s -> s.getScores().size());
        model.addAttribute("students", studentPage);
        return "students";
    }

    // === CRUD SINH VIÊN ===
    @GetMapping("/add-student")
    public String addStudentForm(Model model) {
        if (!model.containsAttribute("student")) model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/add-student")
    public String addStudent(@Valid @ModelAttribute Student student, BindingResult result,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.student", result);
            ra.addFlashAttribute("student", student);
            return "redirect:/add-student";
        }
        try {
            studentRepo.save(student);
            ra.addFlashAttribute("success", "Thêm sinh viên thành công!");
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("studentCode", "", "Mã sinh viên đã tồn tại!");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.student", result);
            ra.addFlashAttribute("student", student);
            return "redirect:/add-student";
        }
        return "redirect:/";
    }

    @GetMapping("/edit-student/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student s = studentRepo.findById(id).orElseThrow();
        model.addAttribute("student", s);
        return "edit-student";
    }

    @PostMapping("/edit-student/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Student student,
                         BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.student", result);
            ra.addFlashAttribute("student", student);
            return "redirect:/edit-student/" + id;
        }
        try {
            Student existing = studentRepo.findById(id).orElseThrow();
            existing.setStudentCode(student.getStudentCode());
            existing.setFullName(student.getFullName());
            existing.setAddress(student.getAddress());
            studentRepo.save(existing);
            ra.addFlashAttribute("success", "Cập nhật thành công!");
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("studentCode", "", "Mã sinh viên đã tồn tại!");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.student", result);
            ra.addFlashAttribute("student", student);
            return "redirect:/edit-student/" + id;
        }
        return "redirect:/";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes ra) {
        studentRepo.deleteById(id);
        ra.addFlashAttribute("success", "Xóa sinh viên thành công!");
        return "redirect:/";
    }

    // === CRUD ĐIỂM ===
    @GetMapping("/add-score")
    public String addScoreForm(Model model) {
        if (!model.containsAttribute("score")) model.addAttribute("score", new StudentScore());
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("subjects", subjectRepo.findAll());
        return "add-score";
    }

    @PostMapping("/add-score")
    public String addScore(@Valid @ModelAttribute("score") StudentScore score,
                           BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.score", result);
            ra.addFlashAttribute("score", score);
            ra.addFlashAttribute("students", studentRepo.findAll());
            ra.addFlashAttribute("subjects", subjectRepo.findAll());
            return "redirect:/add-score";
        }
        scoreRepo.save(score);
        ra.addFlashAttribute("success", "Nhập điểm thành công!");
        return "redirect:/";
    }

    @GetMapping("/edit-score/{id}")
    public String editScoreForm(@PathVariable Long id, Model model) {
        StudentScore s = scoreRepo.findById(id).orElseThrow();
        model.addAttribute("score", s);
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("subjects", subjectRepo.findAll());
        return "edit-score";
    }

    @PostMapping("/edit-score/{id}")
    public String updateScore(@PathVariable Long id, @Valid @ModelAttribute("score") StudentScore score,
                              BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.score", result);
            ra.addFlashAttribute("score", score);
            ra.addFlashAttribute("students", studentRepo.findAll());
            ra.addFlashAttribute("subjects", subjectRepo.findAll());
            return "redirect:/edit-score/" + id;
        }
        score.setStudentScoreId(id);
        scoreRepo.save(score);
        ra.addFlashAttribute("success", "Cập nhật điểm thành công!");
        return "redirect:/";
    }

    @GetMapping("/delete-score/{id}")
    public String deleteScore(@PathVariable Long id, RedirectAttributes ra) {
        scoreRepo.deleteById(id);
        ra.addFlashAttribute("success", "Xóa điểm thành công!");
        return "redirect:/";
    }
}