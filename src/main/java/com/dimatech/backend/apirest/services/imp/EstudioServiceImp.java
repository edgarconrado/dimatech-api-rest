package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iEstudioDao;
import com.dimatech.backend.apirest.models.entity.Estudio;
import com.dimatech.backend.apirest.services.iEstudioService;

/**
 * @author edgar_conrado on 09/09/2020
 *
 */
@Service
public class EstudioServiceImp implements iEstudioService {

	@Autowired
	private iEstudioDao estudioDao;
	
	/**
	 * Lista a todos los Estudios
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Estudio> findAll() {
		return (List<Estudio>) estudioDao.findAll();
	}

	/**
	 * Lista un estudio encontrado por ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Estudio findById(Long id) {		
		return estudioDao.findById(id).orElse(null);
	}

	/**
	 * Guarda un estudio nuevo o ya existente
	 */
	@Override
	@Transactional
	public Estudio save(Estudio estudio) {		
		return estudioDao.save(estudio);
	}

}
