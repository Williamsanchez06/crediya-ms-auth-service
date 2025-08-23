package co.com.crediya.authservice.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@Table(name = "roles", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleEntity {

    @Id
    @Column("role_id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;


}