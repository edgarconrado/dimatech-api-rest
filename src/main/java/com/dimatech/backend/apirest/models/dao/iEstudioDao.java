package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Estudio;

public interface iEstudioDao extends CrudRepository<Estudio, Long> {

}
