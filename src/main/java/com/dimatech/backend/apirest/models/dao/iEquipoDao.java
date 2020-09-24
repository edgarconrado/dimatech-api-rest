package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Equipo;

public interface iEquipoDao extends CrudRepository<Equipo, Long> {

}
