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

import com.dimatech.backend.apirest.models.entity.Estudio;
import com.dimatech.backend.apirest.services.iEstudioService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class EstudioRestController {

	@Autowired
	private iEstudioService estudioService;
	
	@GetMapping("/estudios")
	public List<Estudio> index() {
		return estudioService.findAll();
	}
	
	/**
	 * Procedimiento para listar un Estudio
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/estudios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Estudio estudio = null;  //Iniciamos el objecto estudio
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Buscamos el estudio en la base de datos
			estudio = estudioService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar buscar el estudio");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Si no encuentra el estudio, muestra un mensaje que no fué encontrado
		if (estudio == null) {
			response.put("message", "El Estudio con el ID: " .concat(id.toString().concat(" no existe en la base de Datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Regresamos el estudio encontrado junsto conu un status 200
		return new ResponseEntity<Estudio>(estudio, HttpStatus.OK);
	}
	
	/**
	 * Procedieminto para guardar un nuevo estudio en la Base de Datos
	 * 
	 * @param estudio
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping("/estudios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Estudio estudio, BindingResult result) {
		
		
		Estudio estudioNuevo = null; //Iniciamos el objecto categoria
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
			//Guardamos el estudio en la base de datos
			estudioNuevo = estudioService.save(estudio);
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar guardar el estudio, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Regremos al categoria guardado junto con el status CREATED
		response.put("message", "Estudio guardado con éxito" );
		response.put("estudio", estudioNuevo);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);				
		
	}
	
	/**
	 * Procedimiento para Actualizar un estudio 
	 * 
	 * @param estudio
	 * @param result
	 * @param id
	 * @return ResponseEntity
	 */
	@PutMapping("/estudios/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Estudio estudio, BindingResult result,  @PathVariable Long id) {
		
		Estudio estudioActualizado = null; //Iniciamos el objecto estudio
		Estudio estudioActual = null; //Iniciamos el objecto estudio
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
			estudioActual = estudioService.findById(id);
			
			if (estudioActual == null) {
				response.put("message", "El estudio con el ID: " .concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			// Seteamos el objecto que vamos a actualizar
			estudioActual.setLastModified(new Date());
			estudioActual.setNombre(estudio.getNombre());
			estudioActual.setDescripcion(estudio.getDescripcion());
			estudioActual.setStatus(estudio.getStatus());
			estudioActual.setPrecio(estudio.getPrecio());
			estudioActual.setCategoria(estudio.getCategoria());
			estudioActual.setEquipo(estudio.getEquipo());
			
			//Actulizaoms la categoria en la base de datos
			estudioActualizado = estudioService.save(estudioActual);
			
			
		} catch (DataAccessException e) {
			response.put("message", "Se encontró un error al intentar actualizar el estudio, contacte con el administrador");
			
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("message", "El estudio ha sido actualizado " );
		response.put("estudio", estudioActualizado);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

	}
}
