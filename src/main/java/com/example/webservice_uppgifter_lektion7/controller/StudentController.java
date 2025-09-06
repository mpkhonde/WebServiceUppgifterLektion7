package com.example.webservice_uppgifter_lektion7.controller;

import com.example.webservice_uppgifter_lektion7.model.Student;
import com.example.webservice_uppgifter_lektion7.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // GET /students -> 200 med lista, 204 om tom
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Student> students = repository.findAll();
        if (students.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(students);
    }

    // GET /students/{id} -> 200 om hittad, 204 om inte
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return repository.findStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // POST /students -> 201 Created + Location
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student s) {
        Student saved = repository.save(s);
        return ResponseEntity.created(URI.create("/students/" + saved.getId())).body(saved);
    }

    // PUT /students/{id} -> 200 om uppdaterad, 404 om saknas
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Student incoming) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFirstName(incoming.getFirstName());
                    existing.setLastName(incoming.getLastName());
                    existing.setEmail(incoming.getEmail());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /students/{id} -> 204 om raderad, 404 om saknas
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
