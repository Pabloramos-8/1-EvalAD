package org.jesuitasrioja.colegio.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Asignatura")

public class Asignatura {
	@Id
	@NonNull private Long id;
	private String nombre;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy = "libro")
	@PrimaryKeyJoinColumn
	@ElementCollection
	private Set<Libro> libros= new HashSet<>();
	

}
