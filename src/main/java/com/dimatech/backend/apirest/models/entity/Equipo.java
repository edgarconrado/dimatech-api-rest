package com.dimatech.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@Entity
@Table(name = "equipos")
public class Equipo implements Serializable {


	/**
	 * Identificador del Equ
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Identificador unico del equipo
	 */
	@Column(name = "tag")
	@Size(max = 30)
	private String tag;
	
	
	/**
	 * Nombre del equipo 
	 */
	@NotEmpty
	@Column(name = "nombre", nullable = false)
	@Size(min = 1, max = 50, message = "El tamaño tiene qe estar entre 1 y 50 caracteres")
	private String nombre;
	
	/**
	 * Descripcion del equipo
	 */
	@Column(name = "descripcion")
	@Size(max = 500)
	private String descripcion;
	
	/**
	 * Status del Paciente (Activo / Inactivo)
	 */
	@NotEmpty(message = "Debe seleccionar un status")
	@Column(name = "status")
	private Boolean status;
	
	/**
	 * Día cuando se creo el registro de la Categoria 
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	
	/**
	 * Día cuando se hizo la última modificación de la Categoria 
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "last_modified")
	@Temporal(TemporalType.DATE)
	private Date lastModified;


	@PrePersist
	public void prePersist() {
		createAt = new Date();
		lastModified = new Date();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Date getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public Date getLastModified() {
		return lastModified;
	}


	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
