package com.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.example.login.domain.Employee;
import com.example.login.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String home() {
        return "home.html";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        Employee employee = employeeRepository.findByUsernameAndPassword(username, password);
        if (employee != null) {
            return "Login Successful! Welcome " + employee.getUsername();
        } else {
            return "Invalid username or password";
        }
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees.html";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register.html";
    }

    @PostMapping("/register")
    public String registerEmployee(@RequestParam String username, @RequestParam String password,
                                   @RequestParam String firstName, @RequestParam String lastName,
                                   @RequestParam String email, @RequestParam String hireDate,
                                   @RequestParam String role, @RequestParam Long departmentId) {
        Employee employee = new Employee(username, password, firstName, lastName, email,
                                         LocalDate.parse(hireDate), role, departmentId);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/{username}/edit")
    public String showEditForm(@PathVariable String username, Model model) {
        Employee employee = employeeRepository.findById(username).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "edit-employee.html";
        } else {
            return "redirect:/employees";
        }
    }

    @PostMapping("/employees/{username}/edit")
    public String updateEmployee(@PathVariable String username, @RequestParam String password,
                                 @RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String email, @RequestParam String hireDate,
                                 @RequestParam String role, @RequestParam Long departmentId) {
        Employee employee = employeeRepository.findById(username).orElse(null);
        if (employee != null) {
            employee.setPassword(password);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setHireDate(LocalDate.parse(hireDate));
            employee.setRole(role);
            employee.setDepartmentId(departmentId);
            employeeRepository.save(employee);
        }
        return "redirect:/employees";
    }

    @PostMapping("/employees/{username}/delete")
    public String deleteEmployee(@PathVariable String username) {
        employeeRepository.deleteById(username);
        return "redirect:/employees";
    }
}

