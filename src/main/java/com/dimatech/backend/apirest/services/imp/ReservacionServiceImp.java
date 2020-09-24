package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iReservacionDao;
import com.dimatech.backend.apirest.models.entity.Reservacion;
import com.dimatech.backend.apirest.services.iReservacionService;

/**
 * @author edgar_conrado on 09/03/2020
 *
 */
@Service
public class ReservacionServiceImp implements iReservacionService {

	@Autowired
	private iReservacionDao reservacionDao;
	
	
	/**
	 * Lista a todas las reservaciones
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Reservacion> findAll() {		
		return (List<Reservacion>) reservacionDao.findAll();
	}

	/**
	 * Lista una reservacion proporcionando el ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Reservacion findById(Long id) {	
		return reservacionDao.findById(id).orElse(null);
	}

	/**
	 * Guarda una reservación nueva o existente
	 */
	@Override
	@Transactional
	public Reservacion save(Reservacion reservacion) {		
		return reservacionDao.save(reservacion);
	}

	
}
