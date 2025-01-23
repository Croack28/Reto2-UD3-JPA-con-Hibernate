package org.apache.maven.hibernate202301;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.*;

public class App {
    private static Session sesion;
    private static Transaction tx;

    public static void main(String[] args) {
        // La siguiente línea elimina los mensajes informativos en consola
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        SessionFactory singleton = new Configuration().configure().buildSessionFactory();

        sesion = singleton.openSession();
        tx = sesion.getTransaction();
        tx.begin();

        try {
            // Ejemplo de operaciones CRUD

            // Crear un nuevo alumno
        	crearAlumno("A051", "11122233X", "Carlos", "Pérez", "López", "carlos.perez@example.com", "Calle Ejemplo 123", 28001, "Madrid", "Madrid", "Beca Deportiva", "P001");

            // Leer (Buscar) alumno por NIF
            buscarAlumno("11122233X");

            // Actualizar un alumno
            actualizarAlumno("A051", "Carlos", "Pérez", "López", "carlos.updated@example.com");

            // Eliminar un alumno
            eliminarAlumno("A051");

            // Commit si todo va bien
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback(); // Hacer rollback en caso de error
            }
            e.printStackTrace();
        } finally {
            sesion.close();
            singleton.close();
        }
    }

    public static void crearAlumno(String idAlumno, String nif, String nombre, String apellido1, String apellido2,
    	String email, String direccion, Integer codigoPostal, String municipio, String provincia,
        String beca, String profesorId) {

		Profesor profesor = sesion.get(Profesor.class, profesorId);  // Obtener el profesor por su ID

		if (profesor != null) {
			Alumno alumno = new Alumno(idAlumno, profesor, nif, nombre, apellido1, apellido2, email, direccion, codigoPostal,
			municipio, provincia, beca);
			sesion.persist(alumno); 
			System.out.println("Alumno creado: " + alumno.getIdAlumno());
		} else {
			System.err.println("No se encontró el profesor con ID: " + profesorId);
		}
	}


    // Buscar alumnos (SELECT)
    public static void buscarAlumno(String nif) {
        String query = "from Alumno where nif = :nif";
        Query q = sesion.createQuery(query, Alumno.class);
        q.setParameter("nif", nif);
        List<Alumno> listaAlumnos = q.list();

        if (listaAlumnos.isEmpty()) {
            System.err.println("No existe ningún alumno con ese NIF");
        } else {
            for (Alumno alumno : listaAlumnos) {
                System.out.println("Alumno encontrado:");
                System.out.println(" - NIF: " + alumno.getNif());
                System.out.println(" - Nombre: " + alumno.getNombre() + " " + alumno.getApellido1() + " " + alumno.getApellido2());
                if (alumno.getProfesor() != null) {
                    System.out.println(" - Profesor: " + alumno.getProfesor().getNombre());
                } else {
                    System.out.println(" - Profesor: No asignado");
                }
            }
        }
    }

    // Actualizar alumno (UPDATE)
    public static void actualizarAlumno(String idAlumno, String nuevoNombre, String nuevoApellido1, String nuevoApellido2, String nuevoEmail) {
        Alumno alumno = sesion.get(Alumno.class, idAlumno);  // Obtener el alumno por ID
        if (alumno != null) {
            alumno.setNombre(nuevoNombre);
            alumno.setApellido1(nuevoApellido1);
            alumno.setApellido2(nuevoApellido2);
            alumno.setEmail(nuevoEmail);
            sesion.merge(alumno);  // Actualizar el alumno
            System.out.println("Alumno actualizado: " + alumno.getIdAlumno());
        } else {
            System.err.println("No se encontró el alumno con ID: " + idAlumno);
        }
    }

    // Eliminar alumno (DELETE)
    public static void eliminarAlumno(String idAlumno) {
        Alumno alumno = sesion.get(Alumno.class, idAlumno);  // Obtener el alumno por ID
        if (alumno != null) {
            sesion.remove(alumno);  // Eliminar el alumno
            System.out.println("Alumno eliminado: " + alumno.getIdAlumno());
        } else {
            System.err.println("No se encontró el alumno con ID: " + idAlumno);
        }
    }
}
