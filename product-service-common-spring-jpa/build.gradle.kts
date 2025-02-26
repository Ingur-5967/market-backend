dependencies {
    implementation(project(":product-service-common"))
    implementation(project(":product-service-principal"))

    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}