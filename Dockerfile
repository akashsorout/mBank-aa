FROM amazoncorretto:11-alpine
RUN mkdir /app
COPY target/mBank-aa-*.jar /app/mBank-aa.jar
EXPOSE 8080
WORKDIR /app
ENTRYPOINT ["java", "-jar", "mBank.jar"]