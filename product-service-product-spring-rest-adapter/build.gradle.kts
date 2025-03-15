dependencies {
    implementation(project(":product-service-product"))
    implementation(project(":product-service-common"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}