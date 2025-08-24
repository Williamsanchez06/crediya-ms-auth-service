package co.com.crediya.authservice.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "users", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("user_id")
    private UUID id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("document_number")
    private String documentNumber;

    @Column("phone")
    private String phone;

    @Column("base_salary")
    private BigDecimal baseSalary;

    @Column("role_id")
    private UUID roleId;

}