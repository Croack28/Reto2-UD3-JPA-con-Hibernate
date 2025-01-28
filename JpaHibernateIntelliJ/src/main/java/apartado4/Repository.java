package apartado4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static EntityManagerFactory emf ;
    private static EntityManager em ;
    private static CriteriaBuilder cb ;

    public Repository(String baseDatos){
        emf = Persistence.createEntityManagerFactory(baseDatos);
        em = emf.createEntityManager();
        cb = em.getCriteriaBuilder();
    }
    public  void query9(){
        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");

        TypedQuery<Clase> queryClasesCurso = em.createQuery(
                "SELECT c FROM Clase c JOIN c.cursos cu " +
                        "WHERE cu.titulo = :cursoTitulo AND c.duracionHoras > :duracionMinima", Clase.class
        );
        queryClasesCurso.setParameter("cursoTitulo", "Spring Boot");
        queryClasesCurso.setParameter("duracionMinima", 40);

        List<Clase> clasesDeCurso = queryClasesCurso.getResultList();

        clasesDeCurso.forEach(clase ->
                System.out.println("Clase: " + clase.getNombre() + " - Duración: " + clase.getDuracionHoras() + " horas"));

        //_____________________________ API CRITERIA _____________________________//
        System.out.println("\nQUERY CON API CRITERIA\n");

    }public  void query8(){

        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");
        TypedQuery<Curso> queryCursos = em.createQuery(
                "SELECT cu FROM Curso cu " +
                        "WHERE SIZE(cu.clases) > :cantidad", Curso.class
        );
        queryCursos.setParameter("cantidad", 3);

        List<Curso> cursosConMasClases = queryCursos.getResultList();
        cursosConMasClases.forEach(curso ->
                System.out.println("Curso: " + curso.getTitulo() + " - Clases asociadas: " + curso.getClases().size()));


        //_____________________________ API CRITERIA _____________________________//
        System.out.println("\nQUERY CON API CRITERIA\n");

    }public  void query7Join(){
        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");

        List<Object[]> cursosYClases = em.createQuery(
                        "SELECT cu.titulo, c.nombre FROM Curso cu " +
                                "JOIN cu.clases c WHERE c.duracionHoras > :minDuracion", Object[].class
                )
                .setParameter("minDuracion", 30)
                .getResultList();

        for (Object[] row : cursosYClases) {
            System.out.println("Curso: " + row[0] + ", Clase: " + row[1]);
        }


        //_____________________________ API CRITERIA _____________________________//
        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Curso> cursoRoot = query.from(Curso.class);
        Join<Curso,Clase> claseJoin = cursoRoot.join("clases");


        query.multiselect(
                cursoRoot.get("titulo"),
                claseJoin.get("nombre")
        );

        query.where(cb.greaterThan(claseJoin.get("duracionHoras"), 30));

        List<Object[]> resultado2 = em.createQuery(query).getResultList();

        for (Object[] fila : resultado2) {
            System.out.println("Curso: " + fila[0] + ", Clase: " + fila[1]);
        }

    }public  void query6MeduasSumas(){

        //_____________________________ JPQL _____________________________//
        System.out.println("\nQUERY CON JPQL\n");

        Long count = em.createQuery(
                "SELECT COUNT(c) FROM Clase c WHERE c.activa = true", Long.class
        ).getSingleResult();
        System.out.println("Número de clases activas: " + count);

        Double avgDuration = em.createQuery(
                "SELECT AVG(c.duracionHoras) FROM Clase c WHERE c.activa = true", Double.class
        ).getSingleResult();
        System.out.println("Duración media de clases activas: " + avgDuration+" horas");


        //_____________________________ API CRITERIA _____________________________//

        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Clase> claseRoot1 = countQuery.from(Clase.class);
        countQuery.select(cb.count(claseRoot1))
                .where(cb.isTrue(claseRoot1.get("activa")));

        Long cuenta2 = em.createQuery(countQuery).getSingleResult();
        System.out.println("Número de clases activas: " + cuenta2);

// Consulta para calcular la duración promedio de clases activas
        CriteriaQuery<Double> avgQuery = cb.createQuery(Double.class);
        Root<Clase> claseRoot2 = avgQuery.from(Clase.class);
        avgQuery.select(cb.avg(claseRoot2.get("duracionHoras")))
                .where(cb.isTrue(claseRoot2.get("activa")));

        Double duracion2 = em.createQuery(avgQuery).getSingleResult();
        System.out.println("Duración media de clases activas: " + duracion2 + " horas");


    }public  void query5Between(){

        //_____________________________ JPQL _____________________________//
        System.out.println("\nQUERY CON JPQL\n");
        //Si buscamos solo clases, se puede poner sin el select y poniendo "From Curso " y te seleeciona todos los cursos que cumplan con la query
        List<Curso> cursosRango = em.createQuery(
                        "FROM Curso c WHERE c.duracionSemanas BETWEEN :minDuracion AND :maxDuracion AND c.precio < :maxPrecio", Curso.class
                )
                .setParameter("minDuracion", 6)
                .setParameter("maxDuracion", 10)
                .setParameter("maxPrecio", 200)
                .getResultList();

        cursosRango.forEach(curso -> System.out.println(curso.getTitulo() + " - " + curso.getDuracionSemanas() + " semanas - $" + curso.getPrecio()));

        //_____________________________ API CRITERIA _____________________________//
        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Curso> query = cb.createQuery(Curso.class);
        Root<Curso> root = query.from(Curso.class);

        // Definir condiciones
        Predicate duracionEnRango = cb.between(root.get("duracionSemanas"),
                cb.parameter(Integer.class, "minDuracion"),
                cb.parameter(Integer.class, "maxDuracion"));
        Predicate precioMenor = cb.lt(root.get("precio"), cb.parameter(Double.class, "maxPrecio"));

        // Construir la consulta con las condiciones
        query.select(root)
                .where(cb.and(duracionEnRango, precioMenor));

        // Ejecutar la consulta con parámetros
        TypedQuery<Curso> typedQuery = em.createQuery(query);
        typedQuery.setParameter("minDuracion", 6);
        typedQuery.setParameter("maxDuracion", 10);
        typedQuery.setParameter("maxPrecio", 200.0);

        List<Curso> resultado2 = typedQuery.getResultList();

        for (Curso curso : resultado2) {
            System.out.println("ID: " + curso.getIdCurso() +
                    ", Título: " + curso.getTitulo() +
                    ", Duración: " + curso.getDuracionSemanas() +
                    ", Precio: " + curso.getPrecio());
        }

    }public  void query4CriteriosAproximados(){

        //_____________________________ JPQL _____________________________//
        System.out.println("\nQUERY CON JPQL\n");

        //Se puede buscar asi tambien, sin poner el "Select"

        List<Clase> clasesJavaActivas = em.createQuery(
                        "FROM Clase c WHERE c.nombre LIKE :nombre AND c.activa = true", Clase.class
                )
                .setParameter("nombre", "%Java%")
                .getResultList();

        clasesJavaActivas.forEach(clase -> System.out.println(clase.getNombre()));


        //_____________________________ API CRITERIA _____________________________//

        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Clase> query = cb.createQuery(Clase.class);
        Root<Clase> root = query.from(Clase.class);

        Predicate nombreLike = cb.like(root.get("nombre"), cb.parameter(String.class, "nombre"));
        Predicate esActiva = cb.isTrue(root.get("activa"));

        query.select(root)
                .where(cb.and(nombreLike, esActiva));

        TypedQuery<Clase> typedQuery = em.createQuery(query);
        typedQuery.setParameter("nombre", "%Java%");
        List<Clase> resultado2 = typedQuery.getResultList();

        for (Clase clase : resultado2) {
            System.out.println("ID:" + clase.getId() + ", Nombre:" + clase.getNombre() + ", Activa:" + clase.isActiva());
        }

    }public  void query3Ordenada(){

        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");

        List<Clase> clasesOrdenadas = em.createQuery(
                "FROM Clase c ORDER BY c.fechaInicio ASC, c.duracionHoras DESC", Clase.class
        ).getResultList();

        clasesOrdenadas.forEach(clase -> System.out.println(clase.getNombre() + " - " + clase.getFechaInicio() + " - " + clase.getDuracionHoras()));

        //_____________________________ API CRITERIA _____________________________//

        System.out.println("\nQUERY CON API CRITERIA\n");
        CriteriaQuery<Clase> query = cb.createQuery(Clase.class);
        Root<Clase> root = query.from(Clase.class);

        query.select(root).orderBy(cb.asc(root.get("fechaInicio")),cb.desc(root.get("duracionHoras")));


        List<Clase> resultado2 = em.createQuery(query).getResultList();

        for (Clase clase : resultado2) {
            System.out.println("ID:" + clase.getId() + ", Nombre:" + clase.getNombre() +
                    ", Fecha Inicio:" + clase.getFechaInicio() +
                    ", Duración:" + clase.getDuracionHoras());
        }

    }
    public  void query2Where(){

        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");

        List<Clase> resultado = em.createQuery(
                        "FROM Clase c WHERE c.activa = true AND c.duracionHoras > :minDuracion", Clase.class
                )
                .setParameter("minDuracion", 40)
                .getResultList();

        for(Clase clase : resultado){
            System.out.println("ID:"+ clase.getId() +", Nombre:"+clase.getNombre());
        }

        //_____________________________ API CRITERIA _____________________________//

        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Clase> query = cb.createQuery(Clase.class);
        Root<Clase> root = query.from(Clase.class);

        //Condiciones
        Predicate esActiba = cb.isTrue(root.get("activa"));
        //gt = greater than
        Predicate duracionMayor = cb.gt(root.get("duracionHoras"),cb.parameter(Integer.class,"minDuracion"));

        //Construir consulta
        query.select(root).where(cb.and(esActiba,duracionMayor));

        //ejecutar consulta

        TypedQuery<Clase> typedQuery = em.createQuery(query);
        typedQuery.setParameter("minDuracion",40);
        List<Clase> resultado2 = typedQuery.getResultList();


        for (Clase clase : resultado2) {
            System.out.println("ID:" + clase.getId() + ", Nombre:" + clase.getNombre());
        }
    }

    public  void query1AlgunosCampos(){

        //_____________________________ JPQL _____________________________//

        System.out.println("\nQUERY CON JPQL\n");

        String query2 = "SELECT planta FROM Clase";

        List<Object[]> resultado = em.createQuery(
                "SELECT c.nombre, c.duracionHoras FROM Clase c " +
                        "WHERE c.activa = true AND c.duracionHoras > " +
                        "(SELECT AVG(c2.duracionHoras) FROM Clase c2)", Object[].class
        ).getResultList();


        for (Object[] fila : resultado) {
            System.out.println("Nombre: " + fila[0] + ", Duración: " + fila[1]);
        }


        //_____________________________ API CRITERIA _____________________________//

        System.out.println("\nQUERY CON API CRITERIA\n");

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Clase> root_campos = query.from(Clase.class);

        //Para la subquery
        Subquery<Double> subquery = query.subquery(Double.class);
        Root<Clase> rootSubquery = subquery.from(Clase.class);
        subquery.select(cb.avg(rootSubquery.get("duracionHoras")));


        query.multiselect(root_campos.get("nombre"),root_campos.get("duracionHoras"))
                .where(cb.and(cb.isTrue(root_campos.get("activa")),
                        cb.gt(root_campos.get("duracionHoras"),
                                subquery)));


        List<Object[]> resultado2 = em.createQuery(query).getResultList();

        for (Object[] fila : resultado2) {
            System.out.println("Nombre: " + fila[0] + ", Duración: " + fila[1]);
        }
    }





    public void poblarBaseDatos() throws ParseException {

        //AQUI CREAMOS LOS CURSOS Y CLASES PARA POSTERIORMENTE HACER CONSULTAS

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Curso curso1 = new Curso(1, "Java Básico", "Programación", "Principiante", 150.0, 8, true);
        Curso curso2 = new Curso(2, "Spring Boot", "Desarrollo Web", "Intermedio", 200.0, 10, true);
        Curso curso3 = new Curso(3, "Bases de Datos", "Data Science", "Avanzado", 180.0, 6, false);
        Curso curso4 = new Curso(4, "Python para IA", "Inteligencia Artificial", "Intermedio", 220.0, 12, true);
        Curso curso5 = new Curso(5, "Desarrollo Frontend", "Diseño Web", "Principiante", 160.0, 9, false);

        List<Curso> cursos = new ArrayList<>();
        cursos.add(curso1);
        cursos.add(curso2);
        cursos.add(curso3);
        cursos.add(curso4);
        cursos.add(curso5);

        Clase clase1 = new Clase(101, "Introducción a Java", "Conceptos básicos de Java.",
                sdf.parse("2024-01-10"), sdf.parse("2024-03-10"), 40, "Juan Pérez", 30, true);
        Clase clase2 = new Clase(102, "Spring Boot desde cero", "Desarrollo de API REST con Spring Boot.",
                sdf.parse("2024-02-15"), sdf.parse("2024-04-15"), 50, "María López", 25, true);
        Clase clase3 = new Clase(103, "SQL Avanzado", "Consultas complejas en bases de datos.",
                sdf.parse("2024-03-01"), sdf.parse("2024-05-01"), 35, "Carlos Gómez", 20, false);
        Clase clase4 = new Clase(104, "Machine Learning ", "Modelos de ML básicos.",
                sdf.parse("2024-04-05"), sdf.parse("2024-06-05"), 60, "Ana Torres", 30, true);
        Clase clase5 = new Clase(105, "HTML y CSS", "Diseño web desde cero.",
                sdf.parse("2024-05-20"), sdf.parse("2024-07-20"), 20, "David Romero", 40, false);
        Clase clase6 = new Clase(106, "Programación Funcional en Java", "Lambda y Streams.",
                sdf.parse("2024-06-10"), sdf.parse("2024-08-10"), 45, "Fernando Ruiz", 35, true);
        Clase clase7 = new Clase(107, "Spring Security", "Seguridad en aplicaciones web.",
                sdf.parse("2024-07-01"), sdf.parse("2024-09-01"), 55, "Laura Martín", 25, true);
        Clase clase8 = new Clase(108, "NoSQL con MongoDB", "Gestión de bases de datos NoSQL.",
                sdf.parse("2024-08-15"), sdf.parse("2024-10-15"), 50, "Pedro Sánchez", 20, false);
        Clase clase9 = new Clase(109, "Deep Learning con Python", "Redes neuronales profundas.",
                sdf.parse("2024-09-05"), sdf.parse("2024-11-05"), 70, "Clara Vargas", 30, true);
        Clase clase10 = new Clase(110, "JavaScript Avanzado", "Programación asincrónica y ES6+.",
                sdf.parse("2024-10-20"), sdf.parse("2024-12-20"), 40, "Ricardo Gómez", 35, false);

        List<Clase> clases = new ArrayList<>();
        clases.add(clase1);
        clases.add(clase2);
        clases.add(clase3);
        clases.add(clase4);
        clases.add(clase5);
        clases.add(clase6);
        clases.add(clase7);
        clases.add(clase8);
        clases.add(clase9);
        clases.add(clase10);


        //Las guardamos en sus respectivas tablas mysql


        em.getTransaction().begin();

        for(Clase clase : clases){
            em.persist(clase);
        }

        for(Curso curso: cursos){
            em.persist(curso);

        }

        em.getTransaction().commit();


        //Ahora añadiremos las depencencias manyToMany

        em.getTransaction().begin();


        clase1.getCursos().add(curso1);
        clase1.getCursos().add(curso2);
        em.merge(clase1);

        clase2.getCursos().add(curso2);
        em.merge(clase2);


        clase3.getCursos().add(curso3);
        em.merge(clase3);

        clase4.getCursos().add(curso2);
        clase4.getCursos().add(curso3);
        clase4.getCursos().add(curso4);
        em.merge(clase4);

        clase5.getCursos().add(curso2);
        clase5.getCursos().add(curso5);
        em.merge(clase5);

        clase6.getCursos().add(curso1);
        clase6.getCursos().add(curso2);
        clase6.getCursos().add(curso3);
        em.merge(clase6);

        clase7.getCursos().add(curso2);
        clase7.getCursos().add(curso5);
        em.merge(clase7);

        clase8.getCursos().add(curso3);
        em.merge(clase8);

        clase9.getCursos().add(curso3);
        clase9.getCursos().add(curso4);
        em.merge(clase9);

        clase10.getCursos().add(curso2);
        clase10.getCursos().add(curso5);
        em.merge(clase10);


        em.getTransaction().commit();
    }


}
