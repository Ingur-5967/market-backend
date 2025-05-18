dependencies {
    implementation(project(":id-service-common"))
    implementation(project(":id-service-access-token"))
    implementation(project(":id-service-token"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}