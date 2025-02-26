dependencies {
    implementation(project(":id-service-common"))
    implementation(project(":id-service-principal"))
    implementation(project(":id-service-token"))

    implementation(rootProject.libs.nimbus.jose.jwt)
}