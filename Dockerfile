FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/post_office_api-0.0.1-SNAPSHOT.jar /app/post_office_api.jar

ENTRYPOINT ["java", "-jar", "/app/post_office_api.jar"]

EXPOSE 8080