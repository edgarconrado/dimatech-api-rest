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

import com.dimatech.backend.apirest.models.entity.Reservacion;
import com.dimatech.backend.apirest.services.iReservacionService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class ReservaciónRestController {
	
	@Autowired
	private iReservacionService reservacionService;
	
	/**
	 * Procedieminto para listar todas las reservaciones
	 * 
	 * @return
	 */
	@GetMapping("/reservaciones")
	public List<Reservacion> index() {
		return reservacionService.findAll();
	}
	
	
	/**
	 * Procedimiento para listar una Reservación
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/reservaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Reservacion reservacion = null;  //Iniciamos el objecto user
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos la Reservación en la base de datos
			reservacion = reservacionService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar La Reservación");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el Reservación, muestra un mensaje que no fué encontrado
		if (reservacion == null) {
			response.put("message", "La reservación con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el Reservación encontrado junto con un status 200
		return new ResponseEntity<Reservacion>(reservacion, HttpStatus.OK);
	}

	/**
	 * Procedimiento para guardar una nueva Reservación en la Base de Datos
	 * 
	 * @param reservacion
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/reservaciones")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Reservacion reservacion, BindingResult result) {
		
		
		Reservacion reservacionNuevo = null; //Iniciamos el objecto reservacion
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
			reservacionNuevo = reservacionService.save(reservacion);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar la Reservación, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos la Reservación guardado junto con el status CREATED
		response.put("message", "Reservación guardado con éxito" );
		response.put("reservacion", reservacionNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar una Reservación
	 * 
	 * @param reservacion
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/reservaciones/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Reservacion reservacion, BindingResult result,  @PathVariable Long id) {
		
		Reservacion reservacionActualizado = null; //Iniciamos el objecto reservacion
		Reservacion reservacionActual = null; //Iniciamos el objecto reservacion
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
			reservacionActual = reservacionService.findById(id);
			
			if (reservacionActual == null) {
				response.put("message", "La Reservación con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			reservacionActual.setLastModified(new Date());
			reservacionActual.setDia(reservacion.getDia());
			reservacionActual.setHora(reservacion.getHora());
			reservacionActual.setStatus(reservacion.getStatus());
			reservacionActual.setPaciente(reservacion.getPaciente());
			
			//Actulizamos el Usuario en la base de datos
			reservacionActualizado = reservacionService.save(reservacionActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar la Reservación, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "La reservación ha sido actualizado " );
		response.put("reservacion", reservacionActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}
}
