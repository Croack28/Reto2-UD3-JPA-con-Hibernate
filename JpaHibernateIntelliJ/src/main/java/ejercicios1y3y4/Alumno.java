package ejercicios1y3y4;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Alumnoej1")
public class Alumno {

    @Id
    private String idAlumno;
    private String NIF;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private String direccion;
    private int codigoPostal;
    private String municipio;
    private String provincia;
    private String beca;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Profesor profesor;  // Relación ManyToOne con Profesor

    // Constructor vacío
    public Alumno() {
    }

    // Constructor completo
    public Alumno(String idAlumno,String NIF, String nombre, String apellido1, String apellido2, String email, String direccion, int codigoPostal, String municipio, String provincia, String beca, Profesor profesor) {
        this.idAlumno = idAlumno;
        this.NIF = NIF;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.municipio = municipio;
        this.provincia = provincia;
        this.beca = beca;
        this.profesor = profesor;  // Inicializamos el profesor
    }

    // Getters y Setters

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
