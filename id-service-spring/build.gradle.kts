dependencies {

    implementation(libs.springframework.spring.boot.starter.actuator)
    implementation(libs.springframework.spring.boot.starter.data.jpa)
    implementation(libs.springframework.spring.boot.starter.security)
    implementation(libs.springframework.spring.boot.starter.web)
    implementation(libs.springframework.spring.boot.kafka)
    implementation(libs.springframework.spring.boot.loadbalancer)

    implementation(libs.springframework.spring.boot.eureka.client)

    implementation(libs.nimbus.jose.jwt)

    runtimeOnly(libs.postgresql.postgresql)

    implementation(project(":id-service-common"))
    implementation(project(":id-service-common-spring-jpa"))

    implementation(project(":id-service-principal"))
    implementation(project(":id-service-principal-spring-security-adapter"))

    implementation(project(":id-service-user"))
    implementation(project(":id-service-user-spring-jpa-adapter"))
    implementation(project(":id-service-user-spring-rest-adapter"))

    implementation(project(":id-service-authentication"))
    implementation(project(":id-service-authentication-spring-rest-adapter"))
    implementation(project(":id-service-authentication-spring-secure-adapter"))

    implementation(project(":id-service-token"))
    implementation(project(":id-service-access-token"))
    implementation(project(":id-service-token-nimbusds-adapter"))

    implementation(project(":id-service-refresh-token"))
    implementation(project(":id-service-refresh-token-spring-jpa-adapter"))
}