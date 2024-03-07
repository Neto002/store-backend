# Use a imagem do OpenJDK como base
FROM openjdk:23-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR construído pelo Maven para o diretório de trabalho
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta em que a aplicação Spring Boot será executada
EXPOSE 8080

# Comando para iniciar a aplicação Spring Boot quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]
