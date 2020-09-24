package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Reservacion;

public interface iReservacionDao extends CrudRepository<Reservacion, Long> {

}
