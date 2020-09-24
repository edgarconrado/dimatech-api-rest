package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Paciente;

public interface iPacienteDao extends CrudRepository<Paciente, Long>{

}
