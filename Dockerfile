FROM openjdk:21-jdk-slim-buster
WORKDIR /app
COPY target/libraryservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]