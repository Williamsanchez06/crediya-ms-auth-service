package co.com.crediya.authservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.UUID;

@Getter
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

    @JsonProperty("phone")
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "El número de teléfono debe tener entre 7 y 15 dígitos y puede empezar con +")
    private String phone;

    @JsonProperty("role_id")
    @NotNull
    private UUID roleId;

}
