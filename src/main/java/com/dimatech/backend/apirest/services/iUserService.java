package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.User;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
public interface iUserService {

	public List<User> findAll();
	
	public User findById(Long id);
	
	public User findByUserName(String userName);
	
	public User save(User user);
}
