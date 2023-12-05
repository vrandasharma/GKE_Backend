# BUILD STAGE
FROM maven:3.6-openjdk-17 as build
ENV  MAVEN_OPTS -Xss100M
COPY pom.xml /home/app/
WORKDIR /home/app
COPY src /home/app/src
RUN mvn clean package
ENTRYPOINT ["java","-jar","/home/app/target/TestProject-0.0.1-SNAPSHOT.jar"]
#
## PACKAGE STAGE
#FROM openjdk:17
#COPY --from=build /home/app/target/TestProject-0.0.1-SNAPSHOT.jar /home/app/TestProject-0.0.1-SNAPSHOT.jar
#WORKDIR /home/app
#ENTRYPOINT ["java","-jar","/home/app/TestProject-0.0.1-SNAPSHOT.jar"]