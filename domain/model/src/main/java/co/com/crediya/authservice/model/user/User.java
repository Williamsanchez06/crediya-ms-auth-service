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

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String documentNumber;
    private String phone;
    private Role role;
    private BigDecimal baseSalary;

}