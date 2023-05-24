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
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException(format("Object Not Found. ID: %s, TYPE: %s",
                                id, User.class.getSimpleName()))
                ));
    }

    public Flux<User> findAll(){
        return repository.findAll();
    }
}
