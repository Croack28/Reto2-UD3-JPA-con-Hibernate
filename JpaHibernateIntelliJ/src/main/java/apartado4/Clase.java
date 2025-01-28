package apartado4;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clase")
public class Clase {


	@Id
	@Column(name="id_clase")
	private int id;
	
	@Column(name="planta")
	private int planta;
	
	@Column(name="materia")
	private String materia;

	@Column(name="nombre")
	private String nombre;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="fechaInicio")
	private Date fechaInicio;

	@Column(name="fechaFin")
	private Date fechaFin;

	@Column(name="duracionHoras")
	private int duracionHoras;

	@Column(name="profesor")
	private String profesor;

	@Column(name="activa")
	private boolean activa;

	@Column(name="estudiantes")
	private List<String> estudiantes;


	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE	
	})
	@JoinTable(name = "Clases_Cursos",
			joinColumns = @JoinColumn(name = "id_clase"),
			inverseJoinColumns = @JoinColumn(name = "id_curso"))
	private Set<Curso> cursos = new HashSet<Curso>();
	
	public Clase() {
		super();
	}

	// Constructor
	public Clase(int id, String nombre, String descripcion, Date fechaInicio, Date fechaFin,
				 int duracionHoras, String profesor, int cupoMaximo, boolean activa) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.duracionHoras = duracionHoras;
		this.profesor = profesor;
		this.activa = activa;

	}

	// Getters y Setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public Date getFechaInicio() { return fechaInicio; }
	public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

	public Date getFechaFin() { return fechaFin; }
	public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

	public int getDuracionHoras() { return duracionHoras; }
	public void setDuracionHoras(int duracionHoras) { this.duracionHoras = duracionHoras; }

	public String getProfesor() { return profesor; }
	public void setProfesor(String profesor) { this.profesor = profesor; }


	public boolean isActiva() { return activa; }
	public void setActiva(boolean activa) { this.activa = activa; }


	public Set<Curso> getCursos() {
		return cursos;
		}
		public void setModulos(Set<Curso> cursos) {
		this.cursos = cursos;
		}

	@Override
	public String toString() {
		return "Clase{" +
				"id=" + id +
				", planta=" + planta +
				", materia='" + materia + '\'' +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", fechaInicio=" + fechaInicio +
				", fechaFin=" + fechaFin +
				", duracionHoras=" + duracionHoras +
				", profesor='" + profesor + '\'' +
				", activa=" + activa +
				", estudiantes=" + estudiantes +
				", cursos=" + cursos +
				'}';
	}
}
