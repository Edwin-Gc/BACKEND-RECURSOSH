# =========================
# ETAPA 1: Build con Maven
# =========================
FROM docker.io/library/maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# ETAPA 2: Runtime
# =========================
FROM docker.io/library/eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Instalar tzdata (opcional, para timezone)
RUN apt-get update && apt-get install -y tzdata && rm -rf /var/lib/apt/lists/*

# Copiar solo el JAR generado
COPY --from=builder /app/target/*SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]
