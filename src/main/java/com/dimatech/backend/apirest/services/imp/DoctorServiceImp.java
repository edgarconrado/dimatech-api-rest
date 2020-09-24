package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iDoctorDao;
import com.dimatech.backend.apirest.models.entity.Doctor;
import com.dimatech.backend.apirest.services.iDoctorService;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@Service
public class DoctorServiceImp implements iDoctorService{

	@Autowired
	private iDoctorDao doctorDao;
	
	/**
	 * Lista a todos los doctores
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Doctor> findAll() {		
		return (List<Doctor>) doctorDao.findAll();
	}

	/**
	 * Lista un doctor por el ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Doctor findById(Long id) {
		return doctorDao.findById(id).orElse(null);
	}

	
	/**
	 * Guarda un doctor nuevo o uno existente
	 */
	@Override
	@Transactional
	public Doctor save(Doctor doctor) {		
		return doctorDao.save(doctor);
	}

}
