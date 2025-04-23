FROM amazoncorretto:17-alpine3.18-jdk

EXPOSE 8083

RUN mkdir -p /app/

COPY target/aggregator-message-0.0.1-SNAPSHOT.jar /app/aggregator-message.jar

ENTRYPOINT ["java", "-jar", "/app/aggregator-message.jar"]
