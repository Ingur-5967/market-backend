dependencies {
    implementation(project(":id-service-refresh-token"))
    implementation(project(":id-service-token"))
    implementation(project(":id-service-common"))
    implementation(project(":id-service-common-spring-jpa"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}