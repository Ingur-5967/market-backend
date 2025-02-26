# market-backend (Setup guide)

## Порядок запуска сервисов:

Необходимые компоненты: java-21, docker dekstop
Docker: [https://www.docker.com/products/docker-desktop/]

Перед началом запуска микросервисов необходимо запустить docker!

Порядок важен, так как некоторые сервисы регистрируются в сети других

1. gateway-server-service
2. gateway-request-service
3. identity-user-service
4. product-service
5. order-service

Перед запуском сервиса соберите каждый через Dockerfile (Подробнее процесс расписан в сервисах)
