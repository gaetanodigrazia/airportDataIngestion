# Step 1: Usa un'immagine Maven per costruire il progetto
FROM maven:3.8.4-openjdk-17 AS build

# Copia il progetto nella directory di lavoro all'interno del container
COPY . /app
WORKDIR /app

# Compila il progetto e costruisci il JAR
RUN mvn clean package -DskipTests

# Step 2: Usa un'immagine JRE
FROM openjdk:17-jdk-slim

# Copia il JAR dal build
COPY --from=build /app/target/*.jar /app/app.jar

# Porta esposta per il servizio
EXPOSE 8080

# Comando di avvio dell'applicazione
ENTRYPOINT ["java", "-jar", "/app/app.jar"]