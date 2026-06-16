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

# Converte MYSQL_URL no formato mysql://user:senha@host:porta/banco
# para propriedades JDBC do Spring quando necessario.
ENTRYPOINT ["sh", "-c", "if [ -n \"$MYSQL_URL\" ] && [ -z \"$SPRING_DATASOURCE_URL\" ]; then MYSQL_NO_SCHEME=${MYSQL_URL#mysql://}; MYSQL_CREDS=${MYSQL_NO_SCHEME%@*}; MYSQL_HOST_DB=${MYSQL_NO_SCHEME#*@}; MYSQL_USER=${MYSQL_CREDS%%:*}; MYSQL_PASS=${MYSQL_CREDS#*:}; MYSQL_HOST_PORT=${MYSQL_HOST_DB%%/*}; MYSQL_DB=${MYSQL_HOST_DB#*/}; export SPRING_DATASOURCE_URL=\"jdbc:mysql://${MYSQL_HOST_PORT}/${MYSQL_DB}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC\"; export SPRING_DATASOURCE_USERNAME=$MYSQL_USER; export SPRING_DATASOURCE_PASSWORD=$MYSQL_PASS; fi; exec java -jar app.jar --spring.profiles.active=docker"]
