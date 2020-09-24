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

import com.dimatech.backend.apirest.models.entity.User;
import com.dimatech.backend.apirest.services.iUserService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private iUserService userService;
	
	/**
	 * Procedimiento para listar a todos los usuarios
	 * 
	 * @return
	 */
	@GetMapping("/users")
	public List<User> index() {
		return userService.findAll();
	}
	
	
	/**
	 * Procedimiento para listar un Usuario
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		User user = null;  //Iniciamos el objecto user
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el user en la base de datos
			user = userService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar al Usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el Usuario, muestra un mensaje que no fué encontrado
		if (user == null) {
			response.put("message", "El Usuario con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el usuario encontrado junsto conu un status 200
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/**
	 * Procedimiento para guardar un nuevo Usuario en la Base de Datos
	 * 
	 * @param user
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		
		
		User userNuevo = null; //Iniciamos el objecto user
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
			//Guardamos el usuario en la base de datos
			userNuevo = userService.save(user);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar el Usuario, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al usuario guardado junto con el status CREATED
		response.put("message", "Usuario guardado con éxito" );
		response.put("user", userNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un usuario
	 * 
	 * @param user
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/users/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result,  @PathVariable Long id) {
		
		User userActualizado = null; //Iniciamos el objecto user
		User userActual = null; //Iniciamos el objecto user
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
			
			//Buscamos el Usuario que se va a actulizar
			userActual = userService.findById(id);
			
			if (userActual == null) {
				response.put("message", "El Usuario con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			userActual.setLastModified(new Date());
			userActual.setNombre(user.getNombre());
			userActual.setApellidos(user.getApellidos());
			userActual.setStatus(user.getStatus());
			userActual.setFotoPerfil(user.getFotoPerfil());
			userActual.setPassword(user.getPassword());
			userActual.setUserName(user.getUserName());
			
			//Actulizaoms el Usuario en la base de datos
			userActualizado = userService.save(userActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar el Usaurio, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El Usuario ha sido actualizado " );
		response.put("user", userActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

}
