package ejercicios1y3y4;

import jakarta.persistence.*;
import java.util.List;

public class CrudOneToMany {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AlumnoPU");

    // Crear Profesor
    public static void crearProfesor(String idProfesor, String nombre) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Profesor profesor = new Profesor(idProfesor, nombre);

            em.persist(profesor);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Crear Alumno
    public static void crearAlumno(String idAlumno, String NIF, String nombre, String apellido1, String apellido2, String email, String direccion, int codigoPostal, String municipio, String provincia, String beca, Profesor profesor) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Alumno alumno = new Alumno(idAlumno, NIF, nombre, apellido1, apellido2, email, direccion, codigoPostal, municipio, provincia, beca, profesor);

            profesor.getAlumnos().add(alumno);
            
            em.persist(alumno);
            
            em.merge(profesor);
            
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Buscar Profesor
    public static Profesor buscarProfesor(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Profesor profesor = null;

        try {
            profesor = em.find(Profesor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return profesor;
    }

    // Buscar Alumno
    public static Alumno buscarAlumno(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Alumno alumno = null;

        try {
            alumno = em.find(Alumno.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return alumno;
    }

    // Obtener todos los Alumnos
    public static List<Alumno> obtenerAlumnos() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Alumno> alumnos = null;

        try {
            alumnos = em.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return alumnos;
    }

    // Main para pruebas
    public static void main(String[] args) {
        crearProfesor("P001", "Profesor 1");
        Profesor profesor = buscarProfesor("P001");

        crearAlumno("A001", "12345678A", "Juan", "Pérez", "Gómez", "juan.perez@example.com", "Calle Falsa 123", 28080, "Madrid", "Madrid", "SI", profesor);
        crearAlumno("A002", "33565678A", "Paco", "Sanz", "Gordo", "paco.sanz@example.com", "Calle Ejemplo 45", 29080, "Barcelona", "Barcelona", "NO", profesor);

        // Obtener alumnos de un profesor
        for (Alumno alumno : profesor.getAlumnos()) {
            System.out.println("Alumno: " + alumno.getNombre() + " " + alumno.getApellido1());
        }
    }
}

