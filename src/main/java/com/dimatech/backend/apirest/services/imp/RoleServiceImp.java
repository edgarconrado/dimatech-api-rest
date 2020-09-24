package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iRoleDao;
import com.dimatech.backend.apirest.models.entity.Role;
import com.dimatech.backend.apirest.services.iRoleService;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
@Service
public class RoleServiceImp implements iRoleService{

	@Autowired
	private iRoleDao roleDao;
	
	/**
	 * Lista todas los roles
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return (List<Role>) roleDao.findAll();
	}

	/**
	 * Lista un Role encontrado por ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		return roleDao.findById(id).orElse(null);
	}

	/**
	 * Guarda un role nuevo o existente
	 */
	@Override
	@Transactional
	public Role save(Role role) { 
		return roleDao.save(role);
	}

}
