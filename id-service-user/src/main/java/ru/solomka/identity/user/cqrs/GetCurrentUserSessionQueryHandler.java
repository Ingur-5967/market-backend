package ru.solomka.identity.user.cqrs;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;

@RequiredArgsConstructor
public class GetCurrentUserSessionQueryHandler implements CommandHandler<Void, UserEntity> {

    @NonNull PrincipalService principalService;
    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryHandler;

    @Override
    public UserEntity handle(Void commandEntity) {

        if(!principalService.isAuthenticated())
            throw new RuntimeException("Not authenticated");

        PrincipalEntity entity = principalService.getPrincipal();

        return getEntityByIdQueryHandler.handle(new GetEntityByIdQuery(entity.getId()));
    }
}