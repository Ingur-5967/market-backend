rootProject.name = "identity-user-service"

listOf(
    "id-service-access-token",
    "id-service-access-token-spring-rest-adapter",

    "id-service-authentication",
    "id-service-authentication-spring-rest-adapter",
    "id-service-authentication-spring-secure-adapter",

    "id-service-common",
    "id-service-common-spring-jpa",

    "id-service-principal",
    "id-service-principal-spring-security-adapter",

    "id-service-refresh-token",
    "id-service-refresh-token-spring-jpa-adapter",
    "id-service-refresh-token-spring-rest-adapter",

    "id-service-spring",

    "id-service-token",
    "id-service-token-nimbusds-adapter",

    "id-service-user",
    "id-service-user-spring-jpa-adapter",
    "id-service-user-spring-kafka-adapter",
    "id-service-user-spring-rest-adapter",
).forEach {
    include(it)
}