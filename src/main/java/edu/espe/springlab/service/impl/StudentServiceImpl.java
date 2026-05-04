package edu.espe.springlab.service.impl;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateData;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.ConflictException;
import edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {this.repo = repo;}

    @Override
    public StudentResponse create(StudentRequestData request) {
        if(repo.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email ya esta registrado");
        }
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());
        student.setActive(true);

        Student saved = repo.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        return toResponse(student);
    }

    @Override
    public List<StudentResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public Page<StudentResponse> listPaginated(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<StudentResponse> searchByName(String name, Pageable pageable) {
        return repo.findByFullNameContainingIgnoreCase(name, pageable).map(this::toResponse);
    }

    @Override
    public Page<StudentResponse> searchStudents(String fullName, String email, Pageable pageable) {
        return repo.searchStudents(
            StringUtils.hasText(fullName) ? fullName : null,
            StringUtils.hasText(email) ? email : null,
            pageable
        ).map(this::toResponse);
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateData updateData) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        
        // Verificar si el email ya existe y es diferente al actual
        if (updateData.getEmail() != null && !updateData.getEmail().equals(student.getEmail())) {
            if (repo.existsByEmail(updateData.getEmail())) {
                throw new ConflictException("El email ya esta registrado");
            }
            student.setEmail(updateData.getEmail());
        }
        
        // Actualizar otros campos si no son nulos
        if (updateData.getFullName() != null) {
            student.setFullName(updateData.getFullName());
        }
        
        if (updateData.getBirthDate() != null) {
            student.setBirthDate(updateData.getBirthDate());
        }
        
        if (updateData.getActive() != null) {
            student.setActive(updateData.getActive());
        }
        
        return toResponse(repo.save(student));
    }

    @Override
    public StudentResponse deactivate(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        student.setActive(false);
        return toResponse(repo.save(student));
    }

    //Mapeo interno Entidad DTO de salida
    private StudentResponse toResponse(Student student){
        StudentResponse r = new StudentResponse();
        r.setId(student.getId());
        r.setFullName(student.getFullName());
        r.setEmail(student.getEmail());
        r.setBirthDate(student.getBirthDate());
        r.setActive(student.getActive());
        return r;
    }
}
