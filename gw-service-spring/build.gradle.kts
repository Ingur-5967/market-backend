dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)


    implementation(rootProject.libs.springframework.spring.boot.starter.web)

    implementation(rootProject.libs.springframework.spring.boot.cloud.eureka.server)
    implementation(rootProject.libs.springframework.spring.boot.cloud.eureka.client)

    implementation(rootProject.libs.springframework.spring.boot.cloud.loadbalancer)

    implementation(rootProject.libs.springframework.spring.boot.cloud.starter)
    implementation(rootProject.libs.springframework.spring.boot.cloud.starter.gateway.mvc)

    implementation(rootProject.libs.springframework.spring.boot.cloud.depend)
}