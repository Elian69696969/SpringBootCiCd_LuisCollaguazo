package edu.espe.springlab.web.controller;

import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateData;
import edu.espe.springlab.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) { 
        this.studentService = studentService; 
    }

    //Crear un estudiante
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequestData request){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(request));
    }

    //Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getById(id));
    }

    //Obtener todos los estudiantes (sin paginación)
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAll(){
        return ResponseEntity.ok(studentService.list());
    }

    //Obtener estudiantes con paginación
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok(studentService.listPaginated(pageable));
    }

    //Buscar estudiantes por nombre con paginación
    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponse>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok(studentService.searchByName(name, pageable));
    }

    //Búsqueda avanzada por nombre y email
    @GetMapping("/advanced-search")
    public ResponseEntity<Page<StudentResponse>> advancedSearch(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok(studentService.searchStudents(fullName, email, pageable));
    }

    //Actualizar un estudiante (actualización parcial)
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, 
                                                  @Valid @RequestBody StudentUpdateData updateData){
        return ResponseEntity.ok(studentService.update(id, updateData));
    }

    //Desactivar un estudiante
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivate(@PathVariable Long id){
        return ResponseEntity.ok(studentService.deactivate(id));
    }
}
