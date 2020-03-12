package com.demo.rkh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.rkh.model.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
	
	Optional<Dept> findById(Integer id);
	
	//save(Dept dept);

}
