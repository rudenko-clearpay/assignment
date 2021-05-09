# build environment
FROM maven:3.8.1-openjdk-16 as build
VOLUME .m2
COPY . ./
RUN mvn clean install -DskipTests

# run environment
FROM openjdk:16-jdk-alpine
COPY --from=build ./target/*.jar .
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker,e2e-testing","-jar","/app.jar"]