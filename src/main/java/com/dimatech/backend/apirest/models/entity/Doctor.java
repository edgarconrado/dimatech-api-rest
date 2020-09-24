package com.dimatech.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author edgar_conrado
 * 
 *         Lista de Doctores
 *
 */
@Entity
@Table(name = "doctores")
public class Doctor implements Serializable {

	/**
	 * identificador del Doctor
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del doctor
	 */
	@NotEmpty
	@Column(name = "nombre", nullable = false, length = 50)
	@Size(min = 1, max = 50, message = "El nombre debe contener un máximo de 50 caracteres")
	private String nombre;

	/**
	 * Apellido paterno del Doctor
	 */
	@NotEmpty
	@Column(name = "a_paterno", nullable = false, length = 50)
	@Size(min = 1, max = 50, message = "El Apellido Paterno debe contener un máximo de 50 caracteres")
	private String aPaterno;

	/**
	 * Apellido materno del Doctor
	 */
	@NotEmpty
	@Column(name = "a_materno", nullable = false, length = 50)
	@Size(min = 1, max = 50, message = "El Apellido Materno debe contener un máximo de 50 caracteres")
	private String aMaterno;

	/**
	 * Correo Electrónico del Doctor
	 */
	@Column(name = "email", length = 150)
	@Email(message = "Debe introducir un correro electronico válido")
	private String email;

	/**
	 * Telefono del Docotr
	 */
	@Column(name = "telefono", length = 20)
	private String telefono;

	/**
	 * Status del Doctor (Activo / Inactivo)
	 */
	@NotEmpty(message = "Debe seleccionar un status")
	@Column(name = "status")
	private Boolean status;

	/**
	 * Día cuando se creo el registro del Doctor
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	/**
	 * Día cuando se hizo la última modificación del Doctor
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "last_modified")
	@Temporal(TemporalType.DATE)
	private Date lastModified;

	@JsonIgnoreProperties({"doctor", "hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Orden> ordenes;

	/**
	 * 
	 */
	@PrePersist
	public void prePersist() {
		createAt = new Date();
		lastModified = new Date();
	}

	public Doctor() {
		this.ordenes = new ArrayList<>();
	}

	/**
	 * Obtenemos el id del Doctor
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seteamos el id del Docor
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtemos el Apellido Paterno del Doctor
	 * 
	 * @return
	 */
	public String getaPaterno() {
		return aPaterno;
	}

	/**
	 * Seteamos el Apellido Paterno del Doctor
	 * 
	 * @param aPaterno
	 */
	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	/**
	 * Obtemos el Apellido Materno del Doctor
	 * 
	 * @return
	 */
	public String getaMaterno() {
		return aMaterno;
	}

	/**
	 * Seteamos el Apellido Materno del Doctor
	 * 
	 * @param aMaterno
	 */
	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	/**
	 * Obtenemos el nombre del Doctor
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Seteamos el nombre del Doctor
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtenemos el Correo Elctrónico del Doctor
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Seteamos el Correo Elctrónico del Doctor
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtenemos el Telefono del Doctor
	 * 
	 * @return
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Seteamos el Telefono del Doctor
	 * 
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtenemos el status del Doctor
	 * 
	 * @return
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Seteamos el status del Doctor
	 * 
	 * @param status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Obtenemos la fecha de cuando se creó el Doctor
	 * 
	 * @return
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * Seteamos la fecha de cuando se creó el Doctor
	 * 
	 * @param createAt
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * Obtenemos el día que se modificó el Doctor
	 * 
	 * @return
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Seteamos la fecha de cuando se modificó el Paciente
	 * 
	 * @param lastModified
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public List<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
