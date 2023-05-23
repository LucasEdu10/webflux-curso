package br.com.lucasedu.webfluxcurso.model.response;

public record UserResponse(
        String id,
        String name,
        String email,
        String password
) {}
