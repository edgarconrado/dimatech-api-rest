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
 * @author edgar_conrado on 08/31/2020
 * 
 *         Lista de pacientes
 *
 */
@Entity
@Table(name = "pacientes")
public class Paciente implements Serializable {

	
	/**
	 * Identificador del paciente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del paciente
	 */
	@Column(name = "nombre", length = 60)
	private String nombre;

	/**
	 * Apellido paterno del Paciente
	 */
	@Column(name = "a_paterno", length = 50)
	private String aPaterno;

	/**
	 * Apellido materno del Paciente
	 */
	@Column(name = "a_materno", length = 50)
	private String aMaterno;

	/**
	 * Fecha de nacimiento del Paciente
	 */
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	/**
	 * Genero (Masculino / Femenino) del Paciente
	 */
	@Column(name = "genero", length = 20)
	private String genero;

	/**
	 * Dirección del Paciente
	 */
	@Column(name = "direccion", length = 500)
	private String direccion;

	/**
	 * Ciudad donde habita el Paciente
	 */
	@Column(name = "ciudad", length = 30)
	private String ciudad;

	/**
	 * Estado donde habita el Paciente
	 */
	@Column(name = "estado", length = 20)
	private String estado;

	/**
	 * País donde habita el Paciente
	 */
	@Column(name = "pais", length = 20)
	private String pais;

	/**
	 * País donde habita el paciente
	 */
	@Column(name = "cp", length = 10)
	private String cp;

	/**
	 * Correo Electrónico del Paciente
	 */
	
	@Column(name = "email", length = 150)
	@Email(message = "Debe introducir un correro electronico válido")
	@Size(min = 1, max = 150, message = "El correo electrónico debe tener un máximo de 150 caracteres")
	private String email;

	/**
	 * Telefono del Paciente
	 */
	@Column(name = "telefono")
	private String telefono;

	/**
	 * Celular del Paciente
	 */
	@Column(name = "celular")
	private String celular;

	/**
	 * Status del Paciente (Activo / Inactivo)
	 */
	@NotEmpty(message = "Debe seleccionar un status")
	@Column(name = "status")
	private Boolean status;

	/**
	 * Día cuando se creo el registro del Paciente
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	/**
	 * Día cuando se hizo la última modificación del Paciente
	 */
	@NotNull(message = "El campo no debe de ir vacio")
	@Column(name = "last_modified")
	@Temporal(TemporalType.DATE)
	private Date lastModified;

	@JsonIgnoreProperties({"paciente", "hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente", cascade = CascadeType.ALL)
	private List<Orden> ordenes;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		lastModified = new Date();
	}

	public Paciente() {
		this.ordenes = new ArrayList<>();
	}

	
	/**
	 * Obtenemos el id del Paciente
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seteamos el id del paciente,
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtemos el Nombre del Paciente
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Seteamos el nombre del paciente
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtenemos el Apellido Paterno del Paciente
	 * 
	 * @return aPaterno
	 */
	public String getaPaterno() {
		return aPaterno;
	}

	/**
	 * Seteamos el Apellido Paterno del paciente
	 * 
	 * @param aPaterno
	 */
	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	/**
	 * Obtenemos el Apellido Materno del Paciente
	 * 
	 * @return aMaterno
	 */
	public String getaMaterno() {
		return aMaterno;
	}

	/**
	 * Seteamo el Apellido Materno del Paciente.
	 * 
	 * @param aMaterno
	 */
	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	/**
	 * Obtenemos la Fecha de Nacimiento del Paciente
	 * 
	 * @return fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Setemoa la fecha de Nacimiento del Paciente
	 * 
	 * @param fechaNacimiento
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtenemos el Genero del Paciente
	 * 
	 * @return genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Seteamos el genero del Paciente
	 * 
	 * @param genero
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Obtenemos la dirección del paciente
	 * 
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Seteamos la dirección del Paciente.
	 * 
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtenemos la Ciudad del Paciente
	 * 
	 * @return ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * Setemos la ciudad del paciente
	 * 
	 * @param ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Obtenemos el estado del paciente
	 * 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Seteamos el estado del paciente
	 * 
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtenemos el país del paciente
	 * 
	 * @return pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Setemos el país del paciente
	 * 
	 * @param pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Obtenemos el Código postal del Paciente
	 * 
	 * @return cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * Setemos el Código Postal del Paciente
	 * 
	 * @param cp
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * Obtenemos el Correo Elctrónico del Paciente
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Seteamos el correo electrónico del Paciente.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtenemos el Telefono del Paciente
	 * 
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Seteamos el telefono del paciente
	 * 
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obteneoms el Celular del paciente
	 * 
	 * @return celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * Seteamos el celular del paciente.
	 * 
	 * @param celular
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * Obtenemos el status del paciente
	 * 
	 * @return status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Seteamos el status del paciente
	 * 
	 * @param status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Obtenemos la fecha de cuando se creó el paciente
	 * 
	 * @return
	 */
	public Date getDateAdded() {
		return createAt;
	}

	/**
	 * Seteamos la fecha de cuando se modificó el Paciente
	 * 
	 * @param lastModified
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Obtenemos el día que se modificó el Paciente
	 * 
	 * @return
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * Seteamos el día que se creó el Paciente
	 * 
	 * @param createAt
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
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
