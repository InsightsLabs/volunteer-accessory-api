FROM openjdk:17

EXPOSE 8080

WORKDIR /applications

COPY target/volunteerAccessoryApi-0.0.1-SNAPSHOT.jar /applications/volunteerAccessoryApi.jar

ENTRYPOINT ["java", "-jar", "volunteerAccessoryApi.jar"]