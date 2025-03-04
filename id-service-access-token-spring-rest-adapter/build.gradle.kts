dependencies {
    implementation(project(":id-service-access-token"))
    implementation(project(":id-service-token"))
    implementation(project(":id-service-common"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
}