FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE

WORKDIR /target

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]