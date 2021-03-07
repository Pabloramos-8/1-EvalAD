package org.jesuitasrioja.colegio.persistencia;

import java.util.List;
import java.util.Set;

import org.jesuitasrioja.colegio.modelo.Alumno;
import org.jesuitasrioja.colegio.modelo.Asignatura;
import org.jesuitasrioja.colegio.modelo.Libro;
import org.jesuitasrioja.colegio.modelo.Parte;

public interface IPersistencia {
	
	public boolean nuevoAlumno(Long id, String nombre, String email);
	public void crearParte(Long idAlumno, String descripcion);
	public void crearAsignatura(Long idAsignatura, String nombre, Set<Libro> libros);
	public void matricularAlumno(Long idAlumno, Long idAsignatura);
	public Set<Asignatura> asignaturasMatriculado(Long idAlumno);
	public Set<Parte> partesDeAlumno(Long idAlumno);
	public List<Alumno> alumnosConRangoPartes(Integer n);

}
