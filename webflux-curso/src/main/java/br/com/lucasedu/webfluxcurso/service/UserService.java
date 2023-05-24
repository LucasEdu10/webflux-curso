package br.com.lucasedu.webfluxcurso.service;

import br.com.lucasedu.webfluxcurso.entity.User;
import br.com.lucasedu.webfluxcurso.mapper.UserMapper;
import br.com.lucasedu.webfluxcurso.model.request.UserRequest;
import br.com.lucasedu.webfluxcurso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    public Mono<User> save(final UserRequest request){
        return repository.save(userMapper.toEntity(request));
    }

    public Mono<User> find(final UserRequest request){
        return repository.find(userMapper.toEntity(request));
    }
}
