package co.com.crediya.authservice.model.user;
import co.com.crediya.authservice.model.role.Role;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    private static final BigDecimal DEFAULT_BASE_SALARY = new BigDecimal("1500000.00");

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String documentNumber;
    private String phone;
    private Role role;
    private BigDecimal baseSalary = DEFAULT_BASE_SALARY;

}