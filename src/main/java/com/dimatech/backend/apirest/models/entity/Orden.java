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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author edgar_conrado on 09/04/2020
 *
 */
@Entity
@Table(name = "ordenes")
public class Orden implements Serializable {

	/**
	 * Identificador de la Orden
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "entrega_estudio")
	@Size(max = 50)
	private String entregaEstudio;
	
	@Column(name = "formato_estudio")
	@Size(max = 50)
	private String formatoEstudio;
	
	@Column(name = "estudio_completo_para")
	private String estudioCompletoPara;
	
	@Column(name = "atm_tomografia")
	private String atmTomografia;
	
	@Column(name = "area_interes")
	private String areaInteres;
	
	@Column(name = "enfoque_tomografia")
	private String enfoqueTomografia;
	
	@Column(name = "modelo_estudio")
	private String modeloEstudio;
	
	@Column(name = "fotografia_tipo_fondo")
	private String fotografiaTipoFondo;
	
	@Column(name = "fotografia_para")
	private String fotografiaPara;
	
	@Column(name = "fotografia_tipo")
	private String fotografiaTipo;
	
	/**
	 * observacion dela orden
	 */
	@Column(name = "observaciones")
	@Size(max = 250)
	private String observaciones;

	/**
	 * Fecha de la orden
	 */
	@NotEmpty
	@Column(name = "fecha", nullable = false)
	private Date fecha;

	/**
	 * Subtotal a pagar de la orden
	 */
	@NotEmpty
	@Column(name = "subtotal", nullable = false)
	@NumberFormat(style = Style.CURRENCY)
	private Double subtotal;

	/**
	 * Ivan a pagar de la Orden
	 */
	@NotEmpty
	@Column(name = "iva", nullable = false)
	@NumberFormat(style = Style.CURRENCY)
	private Double iva;

	/**
	 * Total a pagar de la Orden
	 */
	@NotEmpty
	@Column(name = "total", nullable = false)
	@NumberFormat(style = Style.CURRENCY)
	private Double total;

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
	@JsonIgnoreProperties({"ordenes", "hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Paciente paciente;

	/**
	 * Clave foranea de la entidad Doctores
	 */
	@JsonIgnoreProperties({"ordenes", "hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Doctor doctor;

	/**
	 * Clave foranea de la entidad Usuarios
	 */
	@JsonIgnoreProperties({"ordenes", "hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "orden_id")
	private List<OrdenEstudio> detallesOrden;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		lastModified = new Date();
	}

	public Orden() {
		detallesOrden = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrdenEstudio> getDetallesOrden() {
		return detallesOrden;
	}

	public void setDetallesOrden(List<OrdenEstudio> detallesOrden) {
		this.detallesOrden = detallesOrden;
	}

	
	
	public String getEntregaEstudio() {
		return entregaEstudio;
	}

	public void setEntregaEstudio(String entregaEstudio) {
		this.entregaEstudio = entregaEstudio;
	}

	public String getFormatoEstudio() {
		return formatoEstudio;
	}

	public void setFormatoEstudio(String formatoEstudio) {
		this.formatoEstudio = formatoEstudio;
	}

	public String getEstudioCompletoPara() {
		return estudioCompletoPara;
	}

	public void setEstudioCompletoPara(String estudioCompletoPara) {
		this.estudioCompletoPara = estudioCompletoPara;
	}

	public String getAtmTomografia() {
		return atmTomografia;
	}

	public void setAtmTomografia(String atmTomografia) {
		this.atmTomografia = atmTomografia;
	}

	public String getAreaInteres() {
		return areaInteres;
	}

	public void setAreaInteres(String areaInteres) {
		this.areaInteres = areaInteres;
	}

	public String getEnfoqueTomografia() {
		return enfoqueTomografia;
	}

	public void setEnfoqueTomografia(String enfoqueTomografia) {
		this.enfoqueTomografia = enfoqueTomografia;
	}

	public String getModeloEstudio() {
		return modeloEstudio;
	}

	public void setModeloEstudio(String modeloEstudio) {
		this.modeloEstudio = modeloEstudio;
	}

	public String getFotografiaTipoFondo() {
		return fotografiaTipoFondo;
	}

	public void setFotografiaTipoFondo(String fotografiaTipoFondo) {
		this.fotografiaTipoFondo = fotografiaTipoFondo;
	}

	public String getFotografiaPara() {
		return fotografiaPara;
	}

	public void setFotografiaPara(String fotografiaPara) {
		this.fotografiaPara = fotografiaPara;
	}

	public String getFotografiaTipo() {
		return fotografiaTipo;
	}

	public void setFotografiaTipo(String fotografiaTipo) {
		this.fotografiaTipo = fotografiaTipo;
	}

	public Double calcularTotal() {
		Double total = 0.00;
		for(OrdenEstudio item: detallesOrden) {
			total += item.calcularImporte();
		}
		
		return total;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
