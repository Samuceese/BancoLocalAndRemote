package org.example.mappers;

import org.example.api.createupdatedelete.Request;
import org.example.api.createupdatedelete.Response;
import org.example.api.getAll.ResponseUserGetAll;
import org.example.api.getById.ResponseUserGetByid;
import org.example.models.Usuario;
import org.example.repository.UserRemoteRepository;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapper {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserMapper.class);


    public static Usuario toUserFromCreate(ResponseUserGetAll responseUserGetAll) {
        return Usuario.builder()
                .id((long) responseUserGetAll.getId())
                .name(responseUserGetAll.getName())
                .username(responseUserGetAll.getUsername())
                .email(responseUserGetAll.getEmail())
                .build();
    }

    public static Usuario toUserFromCreate(ResponseUserGetByid responseUserGetByid) {
        return Usuario.builder()
                .id((long) responseUserGetByid.getId())
                .name(responseUserGetByid.getName())
                .username(responseUserGetByid.getUsername())
                .email(responseUserGetByid.getEmail())
                .build();
    }

    public static Usuario toUserFromCreate(Response response) {
        return Usuario.builder()
                .id((long) response.getId())
                .name(response.getName())
                .username(response.getUsername())
                .email(response.getEmail())
                .createdAt(LocalDateTime.parse(response.getCreatedAt()))
                .updatedAt(LocalDateTime.parse(response.getUpdatedAt()))
                .build();
    }

    public static Request toRequest(Usuario usuario) {
        return Request.builder()
                .name(usuario.getName())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .build();
    }

    public static Usuario toUserFromUpdate(Response response, int id) {
        return Usuario.builder()
                .id((long) id)
                .name(response.getName())
                .username(response.getUsername())
                .email(response.getEmail())
                .createdAt(LocalDateTime.parse(response.getCreatedAt()))
                .updatedAt(LocalDateTime.parse(response.getUpdatedAt()))
                .build();
    }


}
