# market-backend (Setup guide)

## Порядок запуска сервисов:

Необходимый компонент: java-21

Порядок важен, так как некоторые сервисы регистрируются в сети других

1. gateway-server-service
2. gateway-request-service
3. identity-user-service
4. product-service
5. order-service

Перед запуском сервиса соберите каждый через Dockerfile (Подробнее процесс расписан в сервисах)
