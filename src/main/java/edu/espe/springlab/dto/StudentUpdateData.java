package edu.espe.springlab.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class StudentUpdateData {
    
    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String fullName;

    @Email(message = "El formato del email no es válido")
    @Size(max = 120, message = "El email no puede exceder 120 caracteres")
    private String email;

    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate birthDate;

    private Boolean active;

    // Validación personalizada para edad mínima
    @AssertTrue(message = "El estudiante debe tener al menos 16 años")
    private boolean isAgeValid() {
        if (birthDate == null) {
            return true; // Si no se proporciona fecha, no se valida la edad
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= 16;
    }

    // Getters y Setters
    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Email(message = "El formato del email no es válido")
    @Size(max = 120, message = "El email no puede exceder 120 caracteres")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
