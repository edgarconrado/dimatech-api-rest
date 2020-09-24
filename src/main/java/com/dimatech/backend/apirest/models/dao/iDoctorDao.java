package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Doctor;

public interface iDoctorDao extends CrudRepository<Doctor, Long> {

}
