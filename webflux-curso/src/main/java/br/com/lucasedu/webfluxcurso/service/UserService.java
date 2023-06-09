package br.com.lucasedu.webfluxcurso.service;

import br.com.lucasedu.webfluxcurso.entity.User;
import br.com.lucasedu.webfluxcurso.mapper.UserMapper;
import br.com.lucasedu.webfluxcurso.model.request.UserRequest;
import br.com.lucasedu.webfluxcurso.repository.UserRepository;
import br.com.lucasedu.webfluxcurso.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    public Mono<User> save(final UserRequest request){
        return repository.save(userMapper.toEntity(request));
    }

    public Mono<User> findById(final String id){
        return handleNotFound(repository.findById(id), id);
    }

    public Flux<User> findAll(){
        return repository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest request){
        return findById(id)
                .map(entity -> userMapper.toEntity(request, entity))
                .flatMap(repository::save);
    }

    public Mono<User> delete(final String id){
        return handleNotFound(repository.findAndRemove(id), id);
    }

    private <T> Mono<T> handleNotFound(Mono<T> mono, String id){
        return mono.switchIfEmpty(Mono.error(
                new ObjectNotFoundException(format("Object Not Found. ID: %s, TYPE: %s",
                        id, User.class.getSimpleName()))
        ));
    }
}
