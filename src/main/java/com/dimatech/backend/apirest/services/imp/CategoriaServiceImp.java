package com.dimatech.backend.apirest.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimatech.backend.apirest.models.dao.iCategoriaDao;
import com.dimatech.backend.apirest.models.entity.Categoria;
import com.dimatech.backend.apirest.services.iCategoriaService;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@Service
public class CategoriaServiceImp implements iCategoriaService {

	@Autowired
	private iCategoriaDao categoriaDao;
	
	/**
	 * Lista todas las categorias
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {		
		return (List<Categoria>) categoriaDao.findAll();
	}

	/**
	 * Lista una categoria encontrando por el ID
	 */
	@Override
	@Transactional(readOnly = true)
	public Categoria findById(Long id) {
		return categoriaDao.findById(id).orElse(null);
	}

	/**
	 * Guadar una categoria nueva o existente
	 */
	@Override
	@Transactional
	public Categoria save(Categoria categoria) {		
		return categoriaDao.save(categoria);
	}

}
