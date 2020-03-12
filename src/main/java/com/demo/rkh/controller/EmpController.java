package com.demo.rkh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rkh.domain.Department;
import com.demo.rkh.domain.EmployeeDetails;
import com.demo.rkh.exception.ResourceNotFoundException;
import com.demo.rkh.model.Dept;
import com.demo.rkh.model.Emp;
import com.demo.rkh.repository.DeptRepository;
import com.demo.rkh.repository.EmpRepository;
import com.demo.rkh.service.EmpService;


@RestController
@RequestMapping("/api/v1")
public class EmpController {

	@Autowired
	private EmpRepository empRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@GetMapping("/emp/all")
	public ResponseEntity<?> getEmployeeAll() {
		List<Emp> emps =  empRepository.findAll();
		if (emps != null) {
			
			return new ResponseEntity<List<Emp>>(emps, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Data doesn't exist");
		}
	}
	
	@GetMapping("/emp/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable int id){
		Optional <Emp> empOptional  = empRepository.findById(id);
		Dept dept;
		
		if(empOptional.isPresent()) {
			Emp emp = empOptional.get();
						
			Optional <Dept> deptOptional  = deptRepository.findById(id);
			if(deptOptional.isPresent()) {
				dept = deptOptional.get();
			} else {
				throw new ResourceNotFoundException("Employe Data doesn't exist for :" + id);
			}
			 
			EmployeeDetails employeeDetails = new EmployeeDetails(emp.getId(), emp.getEmpName(), emp.getDeptId(), dept.getDeptName());
			return new ResponseEntity<EmployeeDetails>(employeeDetails, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Employe Data doesn't exist for :" + id);
		}
	}
	
	@GetMapping("/emp/{name}")
	public ResponseEntity<?> getEmployeeByName(@PathVariable String name){
		Optional <Emp> empOptional  = empRepository.findByEmpName(name);
		Dept dept;
		
		if(empOptional.isPresent()) {
			Emp emp = empOptional.get();
						
			Optional <Dept> deptOptional  = deptRepository.findById(emp.getDeptId());
			if(deptOptional.isPresent()) {
				dept = deptOptional.get();
			} else {
				throw new ResourceNotFoundException("Employe Data doesn't exist for :" + name);
			}
			 
			EmployeeDetails employeeDetails = new EmployeeDetails(emp.getId(), emp.getEmpName(), emp.getDeptId(), dept.getDeptName());
			return new ResponseEntity<EmployeeDetails>(employeeDetails, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Employe Data doesn't exist for :" + name);
		}
	}
	
	@GetMapping("/dept/all")
	public ResponseEntity<?> getDepartmentAll() {
		List<Dept> depts =  deptRepository.findAll();
		if (depts != null) {
			
			return new ResponseEntity<List<Dept>>(depts, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Data doesn't exist");
		}
	}
	
	
	@GetMapping("/dept/{id}")
	public ResponseEntity<?> getDepartment(@PathVariable int id){
		Optional <Dept> deptOptional  = deptRepository.findById(id);
		if(deptOptional.isPresent()) {
			Dept dept = deptOptional.get();
			return new ResponseEntity<Dept>(dept, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Employe Data doesn't exist for :" + id);
		}
	}
	
	
	@PostMapping("/dept/save")
	public ResponseEntity<?> saveDepartment(@RequestBody Dept dept){
		try {
			Dept newDept= deptRepository.save(dept);
			return new ResponseEntity<Dept>(newDept, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error while Save operations", HttpStatus.CONFLICT);
		}
	}
	
	
	@PostMapping("/emp/save")
	public ResponseEntity<?> saveEmployee(@RequestBody Emp emp){
		try {
			Optional <Dept> deptOptional= deptRepository.findById(emp.getDeptId());
			if(deptOptional.isPresent()) {
				Emp newEmp = empRepository.save(emp);
			} else {
				throw new ResourceNotFoundException("InCorrect Department Id:" + emp.getDeptId());
			}
			return new ResponseEntity<Emp>(emp, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error while Save operations", HttpStatus.CONFLICT);
		}
	}
}
