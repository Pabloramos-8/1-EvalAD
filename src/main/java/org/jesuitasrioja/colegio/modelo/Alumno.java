package org.jesuitasrioja.colegio.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name="Alumno")

public class Alumno implements Serializable{
	@Id 
    @NonNull private Long identificador;
    private String nombre;
    private String email;
    
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "parte")
    @PrimaryKeyJoinColumn
    private Parte parte;
    
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "asignatura")
    @PrimaryKeyJoinColumn
    private Asignatura asignatura;

}
