dependencies {
    implementation(project(":product-service-product"))
    implementation(project(":product-service-common"))
    implementation(project(":product-service-common-spring-web"))
    implementation(project(":product-service-common-spring-jpa"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}