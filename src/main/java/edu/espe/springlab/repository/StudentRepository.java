package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //Buscar un estudiante por email
    Optional<Student> findByEmail(String email);

    //Responder si existe el estudiante con ese email
    boolean existsByEmail(String email);

    //Buscar estudiantes por nombre (ignorando mayúsculas/minúsculas) con paginación
    Page<Student> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    //Búsqueda avanzada por nombre y email con paginación
    @Query("SELECT s FROM Student s WHERE " +
           "(:fullName IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
           "(:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    Page<Student> searchStudents(@Param("fullName") String fullName, 
                                @Param("email") String email, 
                                Pageable pageable);
}
