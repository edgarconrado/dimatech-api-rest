package com.dimatech.backend.apirest.services;

import java.util.List;

import com.dimatech.backend.apirest.models.entity.Role;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
public interface iRoleService {

	public List<Role> findAll();
	
	public Role findById(Long id);
	
	public Role save(Role role);
}
