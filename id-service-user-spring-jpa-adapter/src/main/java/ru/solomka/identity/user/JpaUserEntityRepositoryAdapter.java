package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseCrudRepository;
import ru.solomka.identity.common.BaseJpaEntityRepositoryAdapter;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaUserEntityRepositoryAdapter extends BaseJpaEntityRepositoryAdapter<JpaUserEntity, UserEntity> implements UserRepository {

    @NonNull JpaUserRepository repository;

    public JpaUserEntityRepositoryAdapter(@NonNull JpaUserRepository repository,
                                          @NonNull Mapper<UserEntity, JpaUserEntity> mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username).map(mapper::mapToDomain);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::mapToDomain);
    }
}