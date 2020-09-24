package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Estudio;

public interface iEstudioService {

	public List<Estudio> findAll();
	
	public Estudio findById(Long id);
	
	public Estudio save(Estudio estudio);
}
