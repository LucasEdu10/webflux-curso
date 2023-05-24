package br.com.lucasedu.webfluxcurso.service.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
