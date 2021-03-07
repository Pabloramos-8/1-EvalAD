package org.jesuitasrioja.colegio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;



@Embeddable
public class ParteId implements Serializable{
	private Date fecha;
	private Alumno alumno;

}
