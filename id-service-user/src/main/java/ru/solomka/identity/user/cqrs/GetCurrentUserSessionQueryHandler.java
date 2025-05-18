package ru.solomka.identity.user.cqrs;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;

@RequiredArgsConstructor
public class GetCurrentUserSessionQueryHandler implements CommandHandler<Object, UserEntity> {

    @NonNull PrincipalService principalService;
    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryHandler;

    @Override
    public UserEntity handle(Object commandEntity) {

        System.out.println(principalService.isAuthenticated());
        System.out.println(principalService.getPrincipal());

        if(!principalService.isAuthenticated())
            throw new RuntimeException("Not authenticated");

        PrincipalEntity entity = principalService.getPrincipal();

        System.out.println(entity);

        return getEntityByIdQueryHandler.handle(new GetEntityByIdQuery(entity.getId()));
    }
}