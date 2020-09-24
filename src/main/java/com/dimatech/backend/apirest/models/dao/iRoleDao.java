package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.Role;

public interface iRoleDao extends CrudRepository<Role, Long> {

}
