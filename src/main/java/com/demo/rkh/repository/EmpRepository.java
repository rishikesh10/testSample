package com.demo.rkh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.rkh.model.Emp;


@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {

	Optional<Emp> findById(Integer id);
	Optional<Emp> findByEmpName(String empName);
	List<Emp> findByDeptId(Integer id);
	List<Emp> findAll();
}
