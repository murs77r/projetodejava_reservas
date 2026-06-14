# Etapa 1: compila o projeto com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom primeiro para aproveitar cache de dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copia o restante do codigo e gera o .jar
COPY src ./src
RUN mvn -q -DskipTests package

# Etapa 2: imagem final apenas com Java para executar o jar
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o jar gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Inicia a aplicacao usando o perfil docker
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
