package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Doctor;

public interface iDoctorService {

	public List<Doctor> findAll();
	
	public Doctor findById(Long id);
	
	public Doctor save(Doctor doctor);
}
