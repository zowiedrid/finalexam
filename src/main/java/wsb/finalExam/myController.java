/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package wsb.finalExam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Person;
import java.util.ArrayList;
import java.util.List;
import model.PersonJpaController;
import model.exceptions.NonexistentEntityException;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wtwda
 */
@CrossOrigin
@RestController
@ResponseBody
public class myController {
	
	Person data = new Person();
	PersonJpaController control = new PersonJpaController();
	
	@GetMapping(value="/GET", produces = APPLICATION_JSON_VALUE)
	public List<Person> getData(){
	
		List<Person> buffer = new ArrayList<>();
		buffer = control.findPersonEntities();
		return buffer;
	}
	
	@PostMapping(value ="/POST", consumes = APPLICATION_JSON_VALUE)
	public String sendData (HttpEntity<String> datasend) throws JsonProcessingException{
		
		String feedback = "Do Nothing";
		
		ObjectMapper mapper = new ObjectMapper();
		
		data = mapper.readValue(datasend.getBody(), Person.class);
		
		try {
			control.create(data);
			feedback = data.getNama() + " save";
		}
		catch(Exception ex){
			feedback = ex.getMessage();
		}
		
		return feedback;
	}
	
	@PutMapping(value ="/PUT", consumes = APPLICATION_JSON_VALUE)
	public String editData (HttpEntity<String> datasend) throws JsonProcessingException{
		
		String feedback = "Do Nothing";
		
		ObjectMapper mapper = new ObjectMapper();
		
		data = mapper.readValue(datasend.getBody(), Person.class);
		
		try {
			control.edit(data);
			feedback = data.getNama() + " edited";
		}
		catch(Exception ex){
			feedback = ex.getMessage();
		}
		
		return feedback;
	}
	
		@DeleteMapping(value ="/DELETE", consumes = APPLICATION_JSON_VALUE)
	public String deleteData (HttpEntity<String> datasend) throws JsonProcessingException{
		
		String feedback = "Do Nothing";
		
		ObjectMapper mapper = new ObjectMapper();
		
		data = mapper.readValue(datasend.getBody(), Person.class);
		
		try {
			control.destroy(data.getId());
			feedback = data.getNama() + " deleted";
		}
		catch(NonexistentEntityException ex){
			feedback = ex.getMessage();
		}
		
		return feedback;
	}
}
