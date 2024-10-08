package org.example.usuarios.repository;

import org.example.exceptions.UserNotFoundException;
import org.example.mappers.UserMapper;
import org.example.models.Usuario;
import org.example.usuarios.api.UserApiRest;
import org.example.usuarios.api.createupdatedelete.Request;
import org.example.usuarios.api.createupdatedelete.Response;
import org.example.usuarios.api.getAll.ResponseUserGetAll;
import org.example.usuarios.api.getById.ResponseUserGetByid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRemoteRepositoryTest {

    @Mock
    private UserApiRest userApiRest;

    @InjectMocks
    private UserRemoteRepository userRemoteRepository;

    @Test
    void getAll() {
        var user1 = ResponseUserGetAll.builder()
                .id(1)
                .name("Juan Perez")
                .username("juanp")
                .email("juan@example.com")
                .build();

        var user2 = ResponseUserGetAll.builder()
                .id(2)
                .name("Ana Gomez")
                .username("anag")
                .email("ana@example.com")
                .build();

        when(userApiRest.getAll()).thenReturn(CompletableFuture.completedFuture(List.of(user1, user2)));

        List<Usuario> usuarios = userRemoteRepository.getAll();

        assertEquals(2, usuarios.size());
        assertEquals("Juan Perez", usuarios.get(0).getName());
        assertEquals("Ana Gomez", usuarios.get(1).getName());

        verify(userApiRest, times(1)).getAll();
    }


    @Test
    void getByIdOK() {

        var response = ResponseUserGetByid.builder()
                .id(1)
                .name("Juan Perez")
                .username("juanp")
                .email("juan@example.com")
                .build();

        when(userApiRest.getById(1)).thenReturn(CompletableFuture.completedFuture(response));

        var res = userRemoteRepository.getById(1);

        assertEquals(1, res.getId());
        assertEquals("Juan Perez", res.getName());

        verify(userApiRest, times(1)).getById(1);
    }

    @Test
    void getByIdNotFound() {

        var response = ResponseUserGetByid.builder()
                .build();

        when(userApiRest.getById(2)).thenThrow(UserNotFoundException.class);

        var exception = assertThrows(UserNotFoundException.class, () -> userRemoteRepository.getById(2));

        assertEquals(UserNotFoundException.class, exception.getClass());

        verify(userApiRest, times(1)).getById(2);
    }



    @Test
    void createUserOK() {


        var request = Request.builder()
                .name("Juan Perez")
                .username("juanp")
                .email("juan@example.com")
                .build();

        var response = Response.builder()
                .id(1)
                .name("Juan Perez")
                .username("juanp")
                .email("juan@example.com")
                .build();
        var usuario = Usuario.builder()
                .id(1L)
                .name("Juan Perez")
                .username("juanp")
                .email("juan@example.com")
                .build();
        when(userApiRest.createUser(UserMapper.toRequest(usuario))).thenReturn(CompletableFuture.completedFuture(response));

        var res = userRemoteRepository.createUser(usuario);

        assertEquals(1, res.getId());
        assertEquals("Juan Perez", res.getName());

        verify(userApiRest, times(1)).createUser(request);
    }


    @Test
    void updateUser() {
    }

    @Test
    void deleteById() {
    }
}