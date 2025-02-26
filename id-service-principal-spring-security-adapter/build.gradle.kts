dependencies {
    implementation(project(":id-service-common"))
    implementation(project(":id-service-principal"))

    implementation(rootProject.libs.springframework.spring.boot.starter.security)
}