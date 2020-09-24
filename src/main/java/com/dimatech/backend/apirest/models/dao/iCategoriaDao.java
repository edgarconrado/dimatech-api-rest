package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Categoria;

public interface iCategoriaDao extends CrudRepository<Categoria, Long> {

}
