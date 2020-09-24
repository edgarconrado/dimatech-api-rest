package com.dimatech.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dimatech.backend.apirest.models.entity.User;

public interface iUserDao extends CrudRepository<User, Long>{
	
	public User findByUserName(String userName);

}
