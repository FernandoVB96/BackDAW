# Etapa 1: Build con Maven y JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Copia archivos de proyecto y descarga dependencias (para cachear)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY src ./src

# Compila el proyecto sin tests (para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la app con JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia el jar generado de la etapa build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto que usa tu app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
