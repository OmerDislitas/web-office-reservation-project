# ---- build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# backend projesini kopyala
COPY backend/pom.xml ./pom.xml
COPY backend/src ./src

RUN mvn -DskipTests clean package

# ---- run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Render PORT env verir (genelde 10000); Spring'in bunu kullanması lazım
ENV PORT=8080

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar /app/app.jar"]
