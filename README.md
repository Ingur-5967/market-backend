# market-backend (Setup guide)

## Порядок запуска сервисов:

Необходимые компоненты: java-21, docker dekstop
Docker: [https://www.docker.com/products/docker-desktop/]

- Запустите Docker Dekstop
- Откройте команду строку от имени администратора и введите команду: docker network create -d bridge eureka_network
- После этого можете переходить к запуску сервисов 

Порядок важен запуска сервисов:

1. gateway-server-service
2. gateway-request-service
3. identity-user-service
4. product-service
5. order-service

Перед запуском сервиса соберите каждый через Dockerfile (Подробнее процесс расписан в сервисах. Каждая ветра - сервис)
