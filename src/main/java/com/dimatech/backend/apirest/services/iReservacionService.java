package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Reservacion;

/**
 * @author edgar_conrado on 09/03/2020
 *
 */
public interface iReservacionService {

	public List<Reservacion> findAll();
	
	public Reservacion findById(Long id);
	
	public Reservacion save(Reservacion reservacion);
	
}
