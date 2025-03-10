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

    "product-service-comment",
    "product-service-comment-spring-jpa-adapter",
    "product-service-comment-spring-rest-adapter",

    "product-service-card-view",
    "product-service-card-view-spring-minio-adapter",
    "product-service-card-view-spring-rest-adapter",

    "product-service-minio"
).forEach {
    include(it)
}