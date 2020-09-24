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

import com.dimatech.backend.apirest.models.entity.Doctor;
import com.dimatech.backend.apirest.services.iDoctorService;

/**
 * @author edgar_conrado on 09/01/2020
 *
 */
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class DoctorRestController {

	@Autowired
	private iDoctorService doctorService;
	
	/**
	 * Llama al servicio para listar a todos los doctores almacenados en la base de datos
	 * @return
	 */
	@GetMapping("/doctors")
	public List<Doctor> index() {
		return doctorService.findAll();
	}
	
	/**
	 * Llama sl servicio para listar a un doctor proporcionando el ID del mismo 
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/doctores/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Doctor doctor = null;  //Iniciamos el objecto Doctor
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el doctor en la base de datos
			doctor = doctorService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar al Doctor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el doctor, muestra un mensaje que no fué encontrado
		if (doctor == null) {
			response.put("message", "El Doctor con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el doctor encontrado junsto conu un status 200
		return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar un nuevo doctor en la Base de Datos
	 * 
	 * @param doctor
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/doctores")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Doctor doctor, BindingResult result) {
		
		
		Doctor doctoreNuevo = null; //Iniciamos el objecto doctor
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
			//Guardamos al doctor en la base de datos
			doctoreNuevo = doctorService.save(doctor);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar al Doctor, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al doctor guardado junto con el status CREATED
		response.put("message", "Doctor guardado con éxito" );
		response.put("doctor", doctoreNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un doctor 
	 * 
	 * @param doctor
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/doctores/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Doctor doctor, BindingResult result,  @PathVariable Long id) {
		
		Doctor doctorActualizado = null; //Iniciamos el objecto Doctor
		Doctor doctorActual = null; //Iniciamos el objecto Doctor
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
			
			//Buscamos al doctor que se va a actulizar
			doctorActual = doctorService.findById(id);
			
			if (doctorActual == null) {
				response.put("message", "El Doctor con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			doctorActual.setaMaterno(doctor.getaMaterno());
			doctorActual.setaPaterno(doctor.getaPaterno());
			doctorActual.setEmail(doctor.getEmail());
			doctorActual.setLastModified(new Date());
			doctorActual.setNombre(doctor.getNombre());
			doctorActual.setStatus(doctor.getStatus());
			doctorActual.setTelefono(doctor.getTelefono());
			
			//Actulizaoms el doctor en la base de datos
			doctorActualizado = doctorService.save(doctorActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar al Doctor, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El Doctor ha sido actualizado " );
		response.put("doctor", doctorActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}
}
