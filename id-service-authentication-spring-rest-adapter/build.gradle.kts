dependencies {
    implementation(project(":id-service-user"))
    implementation(project(":id-service-token"))
    implementation(project(":id-service-authentication"))
    implementation(project(":id-service-common"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}