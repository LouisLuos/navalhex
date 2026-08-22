# Etapa 1: Build da aplicação usando Maven e Java 21
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia os arquivos de dependência primeiro para cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila o pacote .jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# Etapa 2: Imagem leve de execução com JRE 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Cria um usuário não-root para maior segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia apenas o .jar gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão (o Render define a variável PORT dinamicamente)
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
