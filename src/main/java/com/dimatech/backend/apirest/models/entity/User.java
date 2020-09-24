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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
@Entity
@Table(name = "usuarios")
public class User implements Serializable {

	/**
	 * Identificador del Role
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del usuario
	 */
	@Column(name = "nombre", length = 60)
	private String nombre;

	/**
	 * Apellidos del usuario
	 */
	@Column(name = "apellidos", length = 60)
	private String apellidos;

	/**
	 * Ubicación de la foto de perfil del suaurio
	 */
	@Column(name = "foto_perfil", length = 500)
	private String fotoPerfil;

	/**
	 * Nombre de usuario
	 */
	@Column(name = "user_name", unique = true, length = 50)
	private String userName;

	/**
	 * Password del usuario
	 */
	@Column(name = "password", length = 60)
	private String password;

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

	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id")
	, inverseJoinColumns = @JoinColumn(name = "role_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
	private List<Role> roles;
	
	@JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Orden> ordenes;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
		lastModified = new Date();
	}

	public User() {
		this.ordenes = new ArrayList<>();
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
