package edu.espe.springlab.service;

import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    //Crear un estudiante a partir del DTO validado
    StudentResponse create(StudentRequestData request);

    //Busqueda por ID
    StudentResponse getById(Long id);

    //Listar todos los estudiantes
    List<StudentResponse> list();

    //Listar estudiantes con paginación
    Page<StudentResponse> listPaginated(Pageable pageable);

    //Buscar estudiantes por nombre con paginación
    Page<StudentResponse> searchByName(String name, Pageable pageable);

    //Búsqueda avanzada por nombre y email con paginación
    Page<StudentResponse> searchStudents(String fullName, String email, Pageable pageable);

    //Actualizar un estudiante
    StudentResponse update(Long id, StudentUpdateData updateData);

    //Cambiar estado del estudiante
    StudentResponse deactivate(Long id);
}
