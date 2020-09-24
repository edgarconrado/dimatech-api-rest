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

import com.dimatech.backend.apirest.models.entity.Role;
import com.dimatech.backend.apirest.services.iRoleService;

/**
 * @author edgar_conrado on 09/02/2020
 *
 */
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class RoleRestController {
	
	@Autowired
	private iRoleService roleService;
	
	/**
	 * Procedimiento para listar a todos los roles
	 * 
	 * @return
	 */
	@GetMapping("/roles")
	public List<Role> index() {
		return roleService.findAll();
	}
	
	/**
	 * Procedimiento para listar un Role
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/roles/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Role role = null;  //Iniciamos el objecto role
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el role en la base de datos
			role = roleService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar el role");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el role, muestra un mensaje que no fué encontrado
		if (role == null) {
			response.put("message", "El Role con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el role encontrado junsto conu un status 200
		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar un nuevo role en la Base de Datos
	 * 
	 * @param role
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/roles")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Role role, BindingResult result) {
		
		
		Role roleNuevo = null; //Iniciamos el objecto role
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
			//Guardamos el role en la base de datos
			roleNuevo = roleService.save(role);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar el role, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al role guardado junto con el status CREATED
		response.put("message", "Paciente guardado con éxito" );
		response.put("role", roleNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un role
	 * 
	 * @param role
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/roles/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Role role, BindingResult result,  @PathVariable Long id) {
		
		Role roleActualizado = null; //Iniciamos el objecto role
		Role roleActual = null; //Iniciamos el objecto role
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
			
			//Buscamos el Role que se va a actulizar
			roleActual = roleService.findById(id);
			
			if (roleActual == null) {
				response.put("message", "El role con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			roleActual.setLastModified(new Date());
			roleActual.setNombre(role.getNombre());
			roleActual.setDescripcion(role.getDescripcion());
			roleActual.setStatus(role.getStatus());
			
			//Actulizaoms el Role en la base de datos
			roleActualizado = roleService.save(roleActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar el role, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El role ha sido actualizado " );
		response.put("role", roleActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

}
