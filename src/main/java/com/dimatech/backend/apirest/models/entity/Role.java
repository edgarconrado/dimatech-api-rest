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

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {


	/**
	 * Identificador del Role
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del Rol
	 */
	@Column(name = "nombre", unique = true, length = 20)
	private String nombre;

	/**
	 * Descripcion del Role
	 */
	@Column(name = "descripcion", length = 300)
	private String descripcion;

	/**
	 * Status del Paciente (Activo / Inactivo)
	 */
	@Column(name = "status")
	private Boolean status;

	/**
	 * Día cuando se creo el registro de la Categoria
	 */
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	/**
	 * Día cuando se hizo la última modificación de la Categoria
	 */
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
