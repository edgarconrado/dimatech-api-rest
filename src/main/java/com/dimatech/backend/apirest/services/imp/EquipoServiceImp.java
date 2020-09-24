package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iEquipoDao;
import com.dimatech.backend.apirest.models.entity.Equipo;
import com.dimatech.backend.apirest.services.iEquipoService;

@Service
public class EquipoServiceImp implements iEquipoService {

	@Autowired
	private iEquipoDao equipoDao;
	
	/**
	 * Lista todos los equipos
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Equipo> findAll() {
		return (List<Equipo>) equipoDao.findAll();
	}

	/**
	 * Lista un equipo encontrado por ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Equipo findById(Long id) {
		return equipoDao.findById(id).orElse(null);
	}

	/**
	 * Guarda un equipo nuevo o ya existente
	 */
	@Override
	@Transactional
	public Equipo save(Equipo equipo) {
		return equipoDao.save(equipo);
	}

}
