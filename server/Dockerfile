# build environment
FROM maven:3.8.1-openjdk-16 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:16-jdk-alpine
WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker,e2e-testing","-jar","/app/app.jar"]