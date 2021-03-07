package org.jesuitasrioja.colegio.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jesuitasrioja.colegio.modelo.Alumno;
import org.jesuitasrioja.colegio.modelo.Asignatura;
import org.jesuitasrioja.colegio.modelo.Libro;
import org.jesuitasrioja.colegio.modelo.Parte;
import org.jesuitasrioja.colegio.modelo.ParteId;

public class PersistenciaHB implements IPersistencia {

	@Override
	public boolean nuevoAlumno(Long id, String nombre, String email) {
		boolean nuevo = false;
		Session s = HibernateUtil.getSessionFactory().openSession();

		Transaction t = s.beginTransaction();
		Alumno nuevoAlumno = new Alumno(id, nombre, email, null, null);

		s.save(nuevoAlumno);
		t.commit();
		s.close();

		return nuevo;

	}

	@Override
	public void crearParte(Long idAlumno, String descripcion) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();

		Date date = new Date();
		Alumno alumno = new Alumno();
		alumno = s.get(Alumno.class, idAlumno);
		ParteId parteId = new ParteId(date, alumno);
		Parte parte = new Parte(parteId, descripcion, null);

		s.save(parte);
		t.commit();

	}

	@Override
	public void crearAsignatura(Long idAsignatura, String nombre, Set<Libro> libros) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();
		
		Asignatura asignatura=new Asignatura(idAsignatura, nombre, libros);
		s.save(asignatura);
		t.commit();
	}

	@Override
	public void matricularAlumno(Long idAlumno, Long idAsignatura) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();
		
		Alumno alumnoMatriculado=s.get(Alumno.class, idAlumno);
		Asignatura asignatura= s.get(Asignatura.class, idAsignatura);
		
		alumnoMatriculado.setAsignatura(asignatura);
		t.commit();
		s.close();
	}

	@Override
	public Set<Asignatura> asignaturasMatriculado(Long idAlumno) {
		Set<Asignatura> setRetorno=null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();
		
		Query q =s.createQuery("from Asignatura asig where asig.Alumno.idemtificador =:idAlumno");
		q.setParameter("identificador", idAlumno);
		
		setRetorno=new HashSet<>(q.getResultList());
		s.close();
		return setRetorno;
	}

	@Override
	public Set<Parte> partesDeAlumno(Long idAlumno) {
		Set<Parte> setRetorno=null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();
		
		Query q =s.createQuery("from Parte p where p.Alumno.identificador =:idAlumno");
		q.setParameter("identificador", idAlumno);
		
		setRetorno=new HashSet<>(q.getResultList());
		s.close();
		return setRetorno;
	}

	@Override
	public List<Alumno> alumnosConRangoPartes(Integer n) {
		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t =s.beginTransaction();
		
		Query q = s.createQuery("from Alumno where count(parte) > :parte");
		q.setParameter("parte", n);
		
		listaAlumnos.addAll(q.getResultList());
		
		t.commit();
		return listaAlumnos;
	}

}
