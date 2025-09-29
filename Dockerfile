# Usa una imagen base con Maven y JDK 17
FROM maven:3.8.5-openjdk-17-slim AS build

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo pom.xml y las dependencias para el cacheo
COPY pom.xml .
COPY src ./src

# Compila y empaqueta la aplicación
RUN mvn clean package -DskipTests

# Usa una imagen base más ligera para la fase de ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR desde la fase de compilación
COPY --from=build /app/target/interview-discovery-server*.jar /app/interview-discovery-server.jar

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app/interview-discovery-server.jar"]



#FROM openjdk:17-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]