package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.dimatech.backend.apirest.models.dao.iUserDao;
import com.dimatech.backend.apirest.models.entity.User;
import com.dimatech.backend.apirest.services.iUserService;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
@Service
public class UserServiceImp implements iUserService {

	@Autowired
	private iUserDao userDao;
	
	/**
	 * Lista todos los usuarios
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {		
		return (List<User>) userDao.findAll();
	}

	/**
	 * Lista un Usuario encontrado por userName
	 */
	@Override
	@Transactional(readOnly = true)
	public User findByUserName(String userName) {		
		return userDao.findByUserName(userName);
	}
	
	/**
	 * Lista un Usuario encontrado por ID
	 */
	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	/**
	 * Guarda un usaurio nuevo o existente
	 */
	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}


}
