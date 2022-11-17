FROM openjdk:16-alpine3.13

WORKDIR /

COPY target/app-0.0.1-SNAPSHOT.jar /run.jar
COPY src/main/webapp/WEB-INF/cards /cards

CMD ["java", "-jar", "/run.jar"]