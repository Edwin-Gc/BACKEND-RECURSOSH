# 1. Etapa de construcción (compila el proyecto con Maven)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar el pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (imagen liviana solo con Java y el JAR)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto que usa Spring Boot (Northflank lo detecta automáticamente)
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]
