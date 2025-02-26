package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaUserEntityRepositoryAdapter implements UserRepository {

    @NonNull CrudUserRepository userRepository;

    @NonNull Mapper<UserEntity, JpaUserEntity> mapper;

    @Override
    public UserEntity create(UserEntity entity) {
        System.out.println(entity);
        JpaUserEntity jpaUserEntity = mapper.mapToInfrastructure(entity);
        jpaUserEntity = userRepository.save(jpaUserEntity);
        return mapper.mapToDomain(jpaUserEntity);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        JpaUserEntity jpaUserEntity = mapper.mapToInfrastructure(entity);
        jpaUserEntity = userRepository.save(jpaUserEntity);
        return mapper.mapToDomain(jpaUserEntity);
    }

    @Override
    public UserEntity deleteById(UUID id) {
        JpaUserEntity searchJpaUserEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);
        userRepository.delete(searchJpaUserEntity);
        return mapper.mapToDomain(searchJpaUserEntity);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username).map(mapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email).map(mapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id).map(mapper::mapToDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }
}