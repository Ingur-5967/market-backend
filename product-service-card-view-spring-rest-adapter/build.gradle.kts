dependencies {
    implementation(project(":product-service-common"))
    implementation(project(":product-service-card-view"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}