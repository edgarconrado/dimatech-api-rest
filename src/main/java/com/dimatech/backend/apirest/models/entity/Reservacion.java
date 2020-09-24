package com.dimatech.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author edgar_conrado on 09/03/2020
 *
 */
@Entity
@Table(name = "reservaciones")
public class Reservacion implements Serializable {


	/**
	 * Identificador de la Reservación
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Día de la reservación
	 */
	@NotEmpty(message = "Debe de indicar una fecha")
	@Column(name = "dia")
	private Date dia;

	/**
	 * Hora en que se planea hacer la reservaciçon
	 */
	@NotEmpty(message = "Debe de indicar una hora")
	@Column(name = "hora")
	private Date hora;

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

	/**
	 * Clave foranea de la entidad Pacientes
	 */
	@NotNull(message = "Debe de Seleccionar un Paciente")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id")
	@JsonIgnoreProperties({"reservacion", "hibernateLazyInitializer", "handler"})
	Paciente paciente;

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

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
