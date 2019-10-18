package com.crudspringboot.employeeApp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crudspringboot.employeeApp.exception.ResourceNotFoundException;
import com.crudspringboot.employeeApp.model.Employee;
import com.crudspringboot.employeeApp.repository.EmployeeRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController { 
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	
	
	
	@GetMapping("/employees") 
	public Page<Employee> getAllEmployees(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
		
		
		return  employeeRepository.findAll(paging);
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable("id") Long empId) throws ResourceNotFoundException
	{
		  Employee employee=employeeRepository.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId));
		  return ResponseEntity.ok().body(employee);
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/employees/{id}")
	
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long empId,@Valid @RequestBody Employee empDetails) throws ResourceNotFoundException
	{
		Employee employee=employeeRepository.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::"+empId));
		employee.setFirstName(empDetails.getFirstName());
		employee.setLastName(empDetails.getLastName());
		employee.setEmailId(empDetails.getEmailId());
		final Employee updatedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
		
	}
	
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	

}
