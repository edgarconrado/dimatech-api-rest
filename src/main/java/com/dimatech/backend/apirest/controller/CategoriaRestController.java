package com.dimatech.backend.apirest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dimatech.backend.apirest.models.entity.Categoria;
import com.dimatech.backend.apirest.services.iCategoriaService;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class CategoriaRestController {
	
	@Autowired
	private iCategoriaService categoriaService;
	
	/**
	 * Lista todos los categorias de la Base de datos
	 * 
	 * @return
	 */
	@GetMapping("/categorias")
	public List<Categoria> index() {
		return categoriaService.findAll();
	}
	
	/**
	 * Procedimiento para listar una categoria
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/categorias/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Categoria categoria = null;  //Iniciamos el objecto categoria
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el categoria en la base de datos
			categoria = categoriaService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar la categoría");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra la categoria, muestra un mensaje que no fué encontrado
		if (categoria == null) {
			response.put("message", "La Categoría con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos la categoria encontrado junsto conu un status 200
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar una nueva categoria en la Base de Datos
	 * 
	 * @param categoria
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/categorias")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult result) {
		
		
		Categoria categoriaNuevo = null; //Iniciamos el objecto categoria
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos que los paraemtros esten bien 
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError err: result.getFieldErrors()) {
				errors.add("The field " + err.getField() + " " + err.getDefaultMessage());
			}
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		
		try {
			//Guardamos la categoria en la base de datos
			categoriaNuevo = categoriaService.save(categoria);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar la categoria, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al categoria guardado junto con el status CREATED
		response.put("message", "Paciente guardado con éxito" );
		response.put("categoria", categoriaNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar una categoria 
	 * 
	 * @param categoria
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/categorias/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult result,  @PathVariable Long id) {
		
		Categoria categoriaActualizado = null; //Iniciamos el objecto categoria
		Categoria categoriaActual = null; //Iniciamos el objecto categoria
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos que los paraemtros esten bien 
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError err: result.getFieldErrors()) {
				errors.add("The field " + err.getField() + " " + err.getDefaultMessage());
			}
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		
		try {
			
			//Buscamos la categoria que se va a actulizar
			categoriaActual = categoriaService.findById(id);
			
			if (categoriaActual == null) {
				response.put("message", "La categoria con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			categoriaActual.setLastModified(new Date());
			categoriaActual.setNombre(categoria.getNombre());
			categoriaActual.setDescripcion(categoria.getDescripcion());
			categoriaActual.setStatus(categoria.getStatus());
			
			//Actulizaoms la categoria en la base de datos
			categoriaActualizado = categoriaService.save(categoriaActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar la categoria, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "La categoria ha sido actualizado " );
		response.put("categoria", categoriaActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

}
