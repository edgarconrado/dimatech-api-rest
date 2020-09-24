package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Paciente;

public interface iPacienteService {
	
	public List<Paciente> findAll();
	
	public Paciente findById(Long id);
	
	public Paciente save(Paciente paciente);

}
