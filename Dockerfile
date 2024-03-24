FROM openjdk:17-alpine

COPY ./build/libs/skilled_project-0.0.1-SNAPSHOT.jar /build/libs/skilled_project-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/build/libs/skilled_project-0.0.1-SNAPSHOT.jar"]
