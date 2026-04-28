package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Employee;
import com.gympro.gymprobackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee>getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id,Employee employee){

        Employee existing = getEmployeeById(id);

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmployeeType(employee.getEmployeeType());
        existing.setSpecialty(employee.getSpecialty());
        existing.setSalary(employee.getSalary());
        existing.setHireDate(employee.getHireDate());
        existing.setActive(employee.isActive());
        return employeeRepository.save(existing);
    }
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
