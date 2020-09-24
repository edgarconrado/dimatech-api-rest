package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Orden;

public interface iOrdenService {
	
	public List<Orden> findAll();
	
	public Orden findById(Long id);
	
	public Orden save(Orden orden);

}
