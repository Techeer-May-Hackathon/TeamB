ARG JAR_FILE=/home/source/java/build/libs/sv-project-0.0.1-SNAPSHOT.jar

FROM gradle:7.3.3-jdk11 as compile
WORKDIR /home/source/java
COPY . .
RUN chown -R gradle .
USER gradle
RUN gradle clean build

FROM openjdk:11-jdk
ARG JAR_FILE
COPY --from=compile ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
