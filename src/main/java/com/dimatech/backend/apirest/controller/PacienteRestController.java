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

import com.dimatech.backend.apirest.models.entity.Paciente;
import com.dimatech.backend.apirest.services.iPacienteService;


/**
 * @author edgar_conrado on 08/31/2020
 *
 */
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class PacienteRestController {
	
	@Autowired
	private iPacienteService pacienteService;

	/**
	 * Lista todos los pacientes de la Base de datos
	 * 
	 * @return
	 */
	@GetMapping("/pacientes")
	public List<Paciente> index() {
		return pacienteService.findAll();
	}
	
	/**
	 * Procedimiento para listar a un Paciente 
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/pacientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Paciente paciente = null;  //Iniciamos el objecto Paciente
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el paciente en la base de datos
			paciente = pacienteService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar el Paciente");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el paciente, muestra un mensaje que no fué encontrado
		if (paciente == null) {
			response.put("message", "El Paciente con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el paciente encontrado junsto conu un status 200
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar un nuevo paciente en la Base de Datos
	 * 
	 * @param paciente
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/pacientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Paciente paciente, BindingResult result) {
		
		
		Paciente pacienteNuevo = null; //Iniciamos el objecto Paciente
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
			//Guardamos al paciente en la base de datos
			pacienteNuevo = pacienteService.save(paciente);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar al Paciente, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al paciente guardado junto con el status CREATED
		response.put("message", "Paciente guardado con éxito" );
		response.put("paciente", pacienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un Paciente 
	 * 
	 * @param paciente
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/pacientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Paciente paciente, BindingResult result,  @PathVariable Long id) {
		
		Paciente pacienteActualizado = null; //Iniciamos el objecto Paciente
		Paciente pacienteActual = null; //Iniciamos el objecto Paciente
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
			
			//Buscamos al paciente que se va a actulizar
			pacienteActual = pacienteService.findById(id);
			
			if (pacienteActual == null) {
				response.put("message", "El paciente con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			pacienteActual.setaMaterno(paciente.getaMaterno());
			pacienteActual.setaPaterno(paciente.getaPaterno());
			pacienteActual.setCelular(paciente.getCelular());
			pacienteActual.setCiudad(paciente.getCiudad());
			pacienteActual.setCp(paciente.getCp());
			pacienteActual.setDireccion(paciente.getDireccion());
			pacienteActual.setEmail(paciente.getEmail());
			pacienteActual.setEstado(paciente.getEstado());
			pacienteActual.setFechaNacimiento(paciente.getFechaNacimiento());
			pacienteActual.setGenero(paciente.getGenero());
			pacienteActual.setLastModified(new Date());
			pacienteActual.setNombre(paciente.getNombre());
			pacienteActual.setPais(paciente.getPais());
			pacienteActual.setStatus(paciente.getStatus());
			pacienteActual.setTelefono(paciente.getTelefono());
			
			//Actulizaoms el paciente en la base de datos
			pacienteActualizado = pacienteService.save(pacienteActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar al Paciente, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El paciente ha sido actualizado " );
		response.put("paciente", pacienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}
	
}
