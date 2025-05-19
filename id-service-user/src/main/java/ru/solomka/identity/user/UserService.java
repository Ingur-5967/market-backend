package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends EntityService<UserEntity> {

    @NonNull UserRepository userRepository;

    @NonNull EntityNotificationService<UserEntity> userEntityNotificationService;

    public UserService(@NonNull UserRepository userRepository,
                       @NonNull PrincipalService principalService,
                       @NonNull EntityNotificationService<UserEntity> userEntityNotificationService) {
        super(userRepository, principalService);
        this.userRepository = userRepository;
        this.userEntityNotificationService = userEntityNotificationService;
    }

    @Override
    public @NotNull UserEntity create(@NotNull UserEntity entity) {
        entity.setCreatedAt(Instant.now());
        userEntityNotificationService.notifyCreated(entity);
        return repository.create(entity);
    }

    @Override
    public @NotNull UserEntity deleteById(@NotNull UUID id) throws EntityNotFoundException {
        UserEntity userEntity = super.deleteById(id);
        userEntityNotificationService.notifyDeleted(userEntity);
        return userEntity;
    }

    public Optional<UserEntity> findByUsername(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }
}