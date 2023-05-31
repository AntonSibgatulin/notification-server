FROM openjdk:17
ADD /target/notification-server-0.0.1-SNAPSHOT.jar run.jar
ENTRYPOINT ["java","-jar","run.jar"]