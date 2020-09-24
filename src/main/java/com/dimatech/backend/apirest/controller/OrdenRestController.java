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

import com.dimatech.backend.apirest.models.entity.Orden;
import com.dimatech.backend.apirest.services.iOrdenService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class OrdenRestController {
	
	@Autowired
	private iOrdenService orderService;
	
	/**
	 * Procedimiento para listar todas las ordenes guardadas en la base de datos
	 * 
	 * @return
	 */
	@GetMapping("/ordenes")
	public List<Orden> index() {
		return orderService.findAll();
	}
	
	/**
	 * Procedimiento para listar una Reservación
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/ordenes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Orden orden = null;  //Iniciamos el objecto user
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos la Reservación en la base de datos
			orden = orderService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar La Orden de Trabajo");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el Reservación, muestra un mensaje que no fué encontrado
		if (orden == null) {
			response.put("message", "La Orde de Trabajo con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el Reservación encontrado junto con un status 200
		return new ResponseEntity<Orden>(orden, HttpStatus.OK);
	}

	/**
	 * Procedimiento para guardar una nueva Orde de trabajo en la Base de Datos
	 * 
	 * @param orden
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/ordenes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Orden orden, BindingResult result) {
		
		
		Orden ordenNuevo = null; //Iniciamos el objecto reservacion
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
			//Guardamos la reservacion en la base de datos
			ordenNuevo = orderService.save(orden);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar la Orden de Trabajo, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos la Orden de trabajo guardado junto con el status CREATED
		response.put("message", "Orden de trabajo guardado con éxito" );
		response.put("orden", ordenNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar una Orden de Trabajo
	 * 
	 * @param orden
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/ordenes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Orden orden, BindingResult result,  @PathVariable Long id) {
		
		Orden ordenActualizado = null; //Iniciamos el objecto orden
		Orden ordenActual = null; //Iniciamos el objecto orden
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
			ordenActual = orderService.findById(id);
			
			if (ordenActual == null) {
				response.put("message", "La Orden de Trabajo con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			ordenActual.setLastModified(new Date());
			ordenActual.setFecha(orden.getFecha());
			ordenActual.setSubtotal(orden.getSubtotal());
			ordenActual.setIva(orden.getIva());
			ordenActual.setTotal(orden.getTotal());
			ordenActual.setStatus(orden.getStatus());
			ordenActual.setObservaciones(orden.getObservaciones());
			
			ordenActual.setPaciente(orden.getPaciente());
			ordenActual.setDoctor(orden.getDoctor());
			ordenActual.setUser(orden.getUser());
			
			//Actulizamos el Usuario en la base de datos
			ordenActualizado = orderService.save(ordenActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar la Orden de Trabajo, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "La Orden de Trabajo ha sido actualizado " );
		response.put("orden", ordenActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}

}
