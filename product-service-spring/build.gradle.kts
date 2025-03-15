dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)
    implementation(rootProject.libs.springframework.spring.boot.starter.security)
    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springframework.spring.boot.starter.cache)
    implementation(rootProject.libs.springframework.spring.boot.starter.validation)

    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    implementation(rootProject.libs.springframework.spring.boot.cloud.eureka.client)
    implementation(rootProject.libs.springframework.spring.boot.cloud.loadbalancer)

    implementation(rootProject.libs.minio.client)

    runtimeOnly(rootProject.libs.postgresql.postgresql)

    implementation(project(":product-service-common"))
    implementation(project(":product-service-common-spring-jpa"))

    implementation(project(":product-service-product"))
    implementation(project(":product-service-product-spring-jpa-adapter"))
    implementation(project(":product-service-product-spring-rest-adapter"))

    implementation(project(":product-service-comment"))
    implementation(project(":product-service-comment-spring-jpa-adapter"))
    implementation(project(":product-service-comment-spring-rest-adapter"))

    implementation(project(":product-service-minio"))

    implementation(project(":product-service-card-view"))
    implementation(project(":product-service-card-view-spring-minio-adapter"))
    implementation(project(":product-service-card-view-spring-rest-adapter"))

}