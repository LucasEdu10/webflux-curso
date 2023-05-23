package br.com.lucasedu.webfluxcurso.model.request;

public record UserRequest(
   String name,
   String email,
   String password
) {}
