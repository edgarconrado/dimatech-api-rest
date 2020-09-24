package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iOrdenDao;
import com.dimatech.backend.apirest.models.entity.Orden;
import com.dimatech.backend.apirest.services.iOrdenService;

/**
 * @author edgar_conrado on 09/04/2020
 *
 */
@Service
public class OrdenServiceImp implements iOrdenService {

	@Autowired
	private iOrdenDao ordenDao;
	
	/**
	 * Lista todas las ordenes
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Orden> findAll() {
		return (List<Orden>) ordenDao.findAll();
	}

	/**
	 * Lista una orden proporcionando el id
	 */
	@Override
	@Transactional(readOnly = true)
	public Orden findById(Long id) {
		return ordenDao.findById(id).orElse(null);
	}

	/**
	 * Guadar una orden nueva o existente
	 */
	@Override
	@Transactional(readOnly = true)
	public Orden save(Orden orden) {
		return ordenDao.save(orden);
	}

}
