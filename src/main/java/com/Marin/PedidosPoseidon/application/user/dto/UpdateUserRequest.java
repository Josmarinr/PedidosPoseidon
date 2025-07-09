package com.Marin.PedidosPoseidon.application.user.dto;

import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @Email(message = "El email debe ser válido")
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private Boolean active;

}
