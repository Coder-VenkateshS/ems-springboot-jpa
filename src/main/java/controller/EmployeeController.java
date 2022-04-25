package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Employee;
import exception.ResourceNotFoundException;
import repository.EmployeeRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeeController {
		
		@Autowired
		EmployeeRepository employeeRepository;
		
//		Fetching data from database
		@GetMapping("/employees")
		public List<Employee> getAllEmployees(){
			return employeeRepository.findAll();
		}
		
//		fetch individual data
		@GetMapping("/employees/{id}")
		public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long employeeId)
		throws ResourceNotFoundException{
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee Id not found "+employeeId));
			return ResponseEntity.ok().body(employee);
		}
		
		
//		saving data to the database
		@PostMapping("/employees")
		public Employee createEmployee(@RequestBody Employee employee) {
			return employeeRepository.save(employee);
		}
		
//		Editing the data
		@PutMapping("/employees/{id}")
		public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long employeeId, @RequestBody Employee employeeDetails) 
				throws ResourceNotFoundException{
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee Id not found "+employeeId));
					
			employee.setEmailId(employeeDetails.getEmailId());
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			final Employee updatedEmployee = employeeRepository.save(employee);
			return ResponseEntity.ok(updatedEmployee);
		}
		
		@DeleteMapping("/employees/{id}")
		public Map<String, Boolean> deleteEmployee(@PathVariable(value="id") Long employeeId)
		throws ResourceNotFoundException{
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee Id not found "+employeeId));
			employeeRepository.delete(employee);
			Map<String,Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}
		
}
