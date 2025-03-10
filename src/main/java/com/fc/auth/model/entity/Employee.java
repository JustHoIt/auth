package com.fc.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private Long departmentId;

    private String email;

    private String nickname;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_role_mapping",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<EmployeeRole> employeeRoles;

    public static boolean isHR(Employee employee) {
        Set<EmployeeRole> employeeRoles = employee.getEmployeeRoles();
        return employeeRoles.stream().anyMatch(r -> r.getName().equals("인사팀"));
    }

}
