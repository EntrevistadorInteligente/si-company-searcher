FROM amazoncorretto:17-alpine3.18-jdk

EXPOSE 8083

RUN mkdir -p /app/

COPY target/analizador-empresa-0.0.1-SNAPSHOT.jar /app/analizador-empresa.jar

ENTRYPOINT ["java", "-jar", "/app/analizador-empresa.jar"]
