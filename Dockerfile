FROM openjdk:21-jdk as builder
ENV SPRING_MAIN_MODULE=gw-service-spring
ARG JAR_FILE=${SPRING_MAIN_MODULE}/build/libs/${SPRING_MAIN_MODULE}.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
FROM openjdk:21-jdk
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]