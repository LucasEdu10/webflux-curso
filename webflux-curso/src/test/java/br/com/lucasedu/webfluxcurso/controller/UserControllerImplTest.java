package br.com.lucasedu.webfluxcurso.controller;

import br.com.lucasedu.webfluxcurso.entity.User;
import br.com.lucasedu.webfluxcurso.mapper.UserMapper;
import br.com.lucasedu.webfluxcurso.model.request.UserRequest;
import br.com.lucasedu.webfluxcurso.model.response.UserResponse;
import br.com.lucasedu.webfluxcurso.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    public static final String ID = "1234556";
    public static final String NAME = "Lucas";
    public static final String EMAIL = "Lucas@gmail.com";
    public static final String PASSWORD = "1234";
    public static final String BASE_URI = "/users";
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Test endpoint save with sucess")
    void testeSaveWithSucess() {
        UserRequest request = new UserRequest(NAME, EMAIL, PASSWORD);
        when(userService.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        verify(userService, times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with sucess")
    void testeSaveWithBadRequest() {
        UserRequest request = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo(BASE_URI)
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation Error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes");

    }

    @Test
    @DisplayName("Test endpoint findById with sucess")
    void findByIdWithSucess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        when(userService.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users/1234556")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(PASSWORD);

        verify(userService).findById(anyString());
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test endpoint findAll with sucess")
    void testFindAllWithSucess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        when(userService.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri(BASE_URI)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ID)
                .jsonPath("$.[0].name").isEqualTo(NAME)
                .jsonPath("$.[0].email").isEqualTo(EMAIL)
                .jsonPath("$.[0].password").isEqualTo(PASSWORD);

        verify(userService).findAll();
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test endpoint update with sucess")
    void testUpdateWithSucess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);
        final var userRequest = new UserRequest(NAME, EMAIL, PASSWORD);
        when(userService.update(anyString(), any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.patch().uri(BASE_URI+"/"+ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.password").isEqualTo(PASSWORD);

        verify(userService).update(anyString(), any(UserRequest.class));
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test endpoint delete with sucess")
    void testDeleteWithSucess() {
        when(userService.delete(anyString())).thenReturn(Mono.just(User.builder().build()));

        webTestClient.delete().uri(BASE_URI+"/"+ID)
                .exchange()
                .expectStatus().isOk();

        verify(userService).delete(anyString());
    }
}