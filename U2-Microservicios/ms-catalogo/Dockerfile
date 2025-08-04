# Usa Java 21 también en la fase de compilación
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -Dmaven.test.skip=true  # <- Esta línea cambiada

# Usa Java 21 para ejecutar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
