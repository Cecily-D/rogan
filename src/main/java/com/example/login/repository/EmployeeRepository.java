package com.example.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.login.domain.Employee;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByUsernameAndPassword(String username, String password);
    Employee findByEmail(String email);
    List<Employee> findByRole(String role);
}
