package apartado1;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AlumnoCRUD {
	
	private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("Facultad");

	public static void main(String[] args) {
		
		Alumno alumno1 = new Alumno("pepe","gutierrez", "wddw", "Dwda", "WDadWD", "wadwada", "dwdad", 12, "daDAWd", "d", "d");	
		//create(alumno1);
		//remove(alumno1.getIdAlumno());
		//update(alumno1.getIdAlumno(),"DWADAWDWAD",3);
		showAll();
		
	
	}
	

	public static void showAll() {
		
		
        EntityManager em = emf.createEntityManager();
        List<Alumno> lista = null;
        try {
        	//Cuando hacemos el selet , tenemos que poner el nombre de la tabla que hemos creado desde java, no la de mysql
        	lista=em.createQuery("SELECT a FROM Alumno a",Alumno.class).getResultList();
        	
            for(Alumno alumno: lista) {
            	System.out.println(alumno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
	}
	
	public static void create(Alumno alumno1) {
		
		
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(alumno1);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
	}
	
	public static void remove(String id) {
		
		
        EntityManager em = emf.createEntityManager();
        
        Alumno alumno1 = em.find(Alumno.class,id);
        try {
        	if(alumno1!=null) {
        		em.getTransaction().begin();
                em.remove(alumno1);
                em.getTransaction().commit();
        	}
        	else {
        		System.err.println("No existe alumno con ese dni");
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
	}
	
public static void update(String id,String gmail, int codigoPostal) {
		
		
        EntityManager em = emf.createEntityManager();
        
        Alumno alumno1 = em.find(Alumno.class,id);
        try {
        	if(alumno1!=null) {
        		em.getTransaction().begin();
        		alumno1.setEmail(gmail);
        		alumno1.setCodigoPostal(codigoPostal);
                em.merge(alumno1);
                em.getTransaction().commit();
        	}
        	else {
        		System.err.println("No existe alumno con ese dni");
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
	}
}
