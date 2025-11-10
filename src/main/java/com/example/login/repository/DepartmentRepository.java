package com.example.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.login.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
