dependencies {
    implementation(project(":id-service-common"))
    implementation(project(":id-service-common-spring-jpa"))
    implementation(project(":id-service-principal"))
    implementation(project(":id-service-user"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}