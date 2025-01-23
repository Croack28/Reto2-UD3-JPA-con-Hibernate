package ejercicios1y3y4;

import jakarta.persistence.*;
import java.util.List;

public class Ejercicio1 {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AlumnoPU");

    public static void crearAlumno(String idAlumno,String NIF, String nombre, String apellido1, String apellido2, String email, String direccion, int codigoPostal, String municipio, String provincia, String beca) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            //Alumno alumno = new Alumno(idAlumno, NIF, nombre, apellido1, apellido2, email, direccion, codigoPostal, municipio, provincia, beca);
            Alumno alumno = new Alumno();
            em.persist(alumno);
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

    public static void actualizarAlumno(String id, String email, String direccion) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Alumno alumno = em.find(Alumno.class, id);
            if (alumno != null) {
                alumno.setEmail(email);
                alumno.setDireccion(direccion);
                em.merge(alumno);
            }

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

    public static void borrarAlumno(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Alumno alumno = em.find(Alumno.class, id);
            if (alumno != null) {
                em.remove(alumno);
            }

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

    public static List<Alumno> getAlumnos() {
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


    public static void main(String[] args) {
    	crearAlumno("AL001",  "12345678A", "Juan", "Pérez", "Gómez", "juan.perez@example.com", "Calle Falsa 123", 28080, "Madrid", "Madrid", "SI");
    	crearAlumno("AL002",  "33565678A", "Paco", "Sanz", "Gordo", "paco.sanz@example.com", "Calle Falsa 333", 28030, "Madrid", "Madrid", "NO");
        Alumno alumno = buscarAlumno("AL001");
        System.out.println("Found Alumno: " + alumno.getNombre());
        actualizarAlumno("AL001", "nuevo.email@example.com", "Nueva Dirección 456");
        borrarAlumno("AL002");

        List<Alumno> alumnos = getAlumnos();
        for (Alumno a : alumnos) {
            System.out.println(a.getNombre());
        }
    }
}
