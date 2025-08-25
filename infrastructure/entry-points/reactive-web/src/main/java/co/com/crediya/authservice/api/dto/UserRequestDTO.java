package co.com.crediya.authservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @JsonProperty("first_name")
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank
    @Size(max = 100)
    private String lastName;

    @JsonProperty("email")
    @NotBlank
    @Email
    private String email;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 8)
    private String password;

    @JsonProperty("document_number")
    @NotBlank
    @Size(max = 20)
    private String documentNumber;

    @JsonProperty("base_salary")
    @NotNull(message = "El salario base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El salario base no puede ser negativo")
    @DecimalMax(value = "15000000.0", inclusive = true, message = "El salario base no puede superar 15,000,000")
    private BigDecimal baseSalary;

    @JsonProperty("phone")
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "El número de teléfono debe tener entre 7 y 15 dígitos y puede empezar con +")
    private String phone;

    @JsonProperty("role_id")
    @NotNull
    private UUID roleId;

}
