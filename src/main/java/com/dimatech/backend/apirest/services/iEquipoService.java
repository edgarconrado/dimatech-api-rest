package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Equipo;

public interface iEquipoService {

	public List<Equipo> findAll();
	
	public Equipo findById(Long id);
	
	public Equipo save(Equipo equipo);
	
}
