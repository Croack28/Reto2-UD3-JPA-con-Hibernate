package apartado4;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @Column(name = "idCurso", nullable = false)
    private int idCurso;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "nivel", nullable = false)
    private String nivel;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "duracionSemanas", nullable = false)
    private int duracionSemanas;

    @Column(name = "certificado", nullable = false)
    private boolean certificado;

    @ManyToMany(mappedBy ="cursos")
    private Set<Clase> clases = new HashSet<Clase>();


    // Constructor vacío
    public Curso() {
    }

    // Constructor con parámetros
    public Curso(int id, String titulo, String categoria, String nivel, double precio,
                 int duracionSemanas, boolean certificado) {
        this.idCurso = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.nivel = nivel;
        this.precio = precio;
        this.duracionSemanas = duracionSemanas;
        this.certificado = certificado;
    }

    // Getters y Setters
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int id) { this.idCurso = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getDuracionSemanas() { return duracionSemanas; }
    public void setDuracionSemanas(int duracionSemanas) { this.duracionSemanas = duracionSemanas; }

    public boolean isCertificado() { return certificado; }
    public void setCertificado(boolean certificado) { this.certificado = certificado; }
    
    

    public Set<Clase> getClases() {
		return clases;
	}

	public void setClases(Set<Clase> clases) {
		this.clases = clases;
	}

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", titulo='" + titulo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", nivel='" + nivel + '\'' +
                ", precio=" + precio +
                ", duracionSemanas=" + duracionSemanas +
                ", certificado=" + certificado +
                ", clases=" + clases +
                '}';
    }
}
