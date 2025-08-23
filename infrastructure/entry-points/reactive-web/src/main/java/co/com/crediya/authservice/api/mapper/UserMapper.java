package co.com.crediya.authservice.api.mapper;

import co.com.crediya.authservice.api.dto.UserRequestDTO;
import co.com.crediya.authservice.api.dto.UserResponseDTO;
import co.com.crediya.authservice.model.role.Role;
import co.com.crediya.authservice.model.user.User;

public class UserMapper {

    public static User toDomain(UserRequestDTO dto, Role role) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDocumentNumber(dto.getDocumentNumber());
        user.setPhone(dto.getPhone());
        user.setRole(role);
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .documentNumber(user.getDocumentNumber())
                .phone(user.getPhone())
                .baseSalary(user.getBaseSalary())
                .build();
    }

}
