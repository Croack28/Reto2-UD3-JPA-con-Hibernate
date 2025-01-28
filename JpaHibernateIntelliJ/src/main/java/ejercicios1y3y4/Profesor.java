package ejercicios1y3y4;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profesor {
    
    @Id
    private String idProfesor;
    private String nombre;

    @OneToMany(mappedBy = "profesor",cascade = CascadeType.ALL)  // Relación OneToMany con Alumno
    private List<Alumno> alumnos;

    // Constructor vacío
    public Profesor() {
    }

    // Constructor completo
    public Profesor(String idProfesor, String nombre) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.alumnos = new ArrayList<Alumno>();
    }

    // Getters y Setters

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    // Método para añadir un alumno a la lista
    public void addAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
        alumno.setProfesor(this);  // Establece el profesor del alumno
    }
}
