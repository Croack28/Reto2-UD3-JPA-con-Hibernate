package apartado4;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import apartado1.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

public class CursoClaseCRUD {


	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Facultad");
	private static EntityManager em = emf.createEntityManager();
	private static CriteriaBuilder cb = em.getCriteriaBuilder();


	public static void main(String[] args) throws ParseException {

		Repository repository = new Repository("Facultad");

		//repository.poblarBaseDatos();

		repository.query1AlgunosCampos();



		repository.query2Where();
		repository.query3Ordenada();
		repository.query4CriteriosAproximados();
		repository.query5Between();
		repository.query6MeduasSumas();
		repository.query7Join();
		repository.query8();
		repository.query9();

	}










}

