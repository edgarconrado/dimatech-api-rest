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

import com.dimatech.backend.apirest.models.entity.Equipo;
import com.dimatech.backend.apirest.services.iEquipoService;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class EquipoRestController {
	
	@Autowired
	private iEquipoService equipoService;
	

	/**
	 * Lista todos los equipos de la Base de datos
	 * 
	 * @return
	 */
	@GetMapping("/equipos")
	public List<Equipo> index() {
		return equipoService.findAll();
	}
	
	/**
	 * Procedimiento para listar una categoria
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/equipos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Equipo equipo = null;  //Iniciamos el objecto categoria
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el categoria en la base de datos
			equipo = equipoService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar el equipo");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el equipo, muestra un mensaje que no fué encontrado
		if (equipo == null) {
			response.put("message", "El Equipo con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el equipo encontrado junsto conu un status 200
		return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar un nuevo equipo en la Base de Datos
	 * 
	 * @param equipo
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/equipos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Equipo equipo, BindingResult result) {
		
		
		Equipo equipoNuevo = null; //Iniciamos el objecto categoria
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
			//Guardamos el equipo en la base de datos
			equipoNuevo = equipoService.save(equipo);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar el Equipo, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al categoria guardado junto con el status CREATED
		response.put("message", "equipo guardado con éxito" );
		response.put("equipo", equipoNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un equipo 
	 * 
	 * @param equipo
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/equipos/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Equipo equipo, BindingResult result,  @PathVariable Long id) {
		
		Equipo equipoActualizado = null; //Iniciamos el objecto equipo
		Equipo equipoActual = null; //Iniciamos el objecto equipo
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
			
			//Buscamos el equipo  que se va a actulizar
			equipoActual = equipoService.findById(id);
			
			if (equipoActual == null) {
				response.put("message", "El equipo con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			equipoActual.setLastModified(new Date());
			equipoActual.setNombre(equipo.getNombre());
			equipoActual.setDescripcion(equipo.getDescripcion());
			equipoActual.setStatus(equipo.getStatus());
			equipoActual.setTag(equipo.getTag());
			
			//Actulizaoms la categoria en la base de datos
			equipoActualizado = equipoService.save(equipoActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar el equipo, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El equipo ha sido actualizado " );
		response.put("equipo", equipoActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

}
