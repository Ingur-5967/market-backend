rootProject.name = "product-service"

listOf(
    "product-service-spring",

    "product-service-product",
    "product-service-product-spring-jpa-adapter",
    "product-service-product-spring-rest-adapter",

    "product-service-common",
    "product-service-common-spring-jpa",

    "product-service-principal",
    "product-service-principal-spring-security-adapter",

    "product-service-user-snapshot",
    "product-service-user-snapshot-spring-jpa-adapter",

    "product-service-comment",
    "product-service-comment-spring-jpa-adapter",
    "product-service-comment-spring-rest-adapter"
).forEach {
    include(it)
}