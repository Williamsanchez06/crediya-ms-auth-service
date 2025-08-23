package co.com.crediya.authservice.model.user;
import co.com.crediya.authservice.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class User {

    private static final BigDecimal DEFAULT_BASE_SALARY = new BigDecimal("1500000.00");

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String documentNumber;
    private String phone;
    private Role role;
    private BigDecimal baseSalary;

    private User(UUID id, String name, String email, String password, String documentNumber, String phone, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.role = role;
        this.baseSalary = DEFAULT_BASE_SALARY;
    }

    public static User create(UUID id, String name, String email, String password, String documentNumber, String phone, Role role) {

        if (id == null) throw new IllegalArgumentException("User ID is required");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name is required");
        if (name.length() > 100)
            throw new IllegalArgumentException("Name exceeds maximum length (100 characters)");

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is required");
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            throw new IllegalArgumentException("Invalid email format");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password is required");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters");

        if (documentNumber == null || documentNumber.isBlank())
            throw new IllegalArgumentException("Document number is required");
        if (documentNumber.length() > 20)
            throw new IllegalArgumentException("Document number too long");

        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException("Phone is required");
        if (!phone.matches("^\\+?\\d{7,15}$"))
            throw new IllegalArgumentException("Invalid phone number");

        if (role == null)
            throw new IllegalArgumentException("Role is required");

        return new User(id, name, email, password, documentNumber, phone, role);
    }

}