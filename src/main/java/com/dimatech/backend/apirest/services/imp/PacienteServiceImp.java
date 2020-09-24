package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iPacienteDao;
import com.dimatech.backend.apirest.models.entity.Paciente;
import com.dimatech.backend.apirest.services.iPacienteService;

/**
 * @author edgar_conrado on 08/31/2020
 *
 */
@Service
public class PacienteServiceImp implements iPacienteService {

	@Autowired
	private iPacienteDao pacienteDao;
	
	/**
	 * Listar a todos los pacientes 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Paciente> findAll() {
		return (List<Paciente>) pacienteDao.findAll();
	}

	/**
	 * Lista un paciente encontrado por el ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Paciente findById(Long id) {		
		return pacienteDao.findById(id).orElse(null);
	}

	/**
	 * Guarda un paciente nuevo o uno existente
	 */
	@Override
	@Transactional
	public Paciente save(Paciente paciente) {
		return pacienteDao.save(paciente);
	}

}
