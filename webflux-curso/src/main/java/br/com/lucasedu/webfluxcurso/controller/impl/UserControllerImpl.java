package br.com.lucasedu.webfluxcurso.controller.impl;

import br.com.lucasedu.webfluxcurso.controller.UserController;
import br.com.lucasedu.webfluxcurso.model.request.UserRequest;
import br.com.lucasedu.webfluxcurso.model.response.UserResponse;
import br.com.lucasedu.webfluxcurso.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Mono<Void>> save(final UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(request)
                        .then());
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> find(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Flux<UserResponse>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Mono<Void>> delete(String id) {
        return null;
    }
}