dependencies {
    implementation(project(":product-service-comment"))
    implementation(project(":product-service-common"))
    implementation(project(":product-service-common-spring-jpa"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}