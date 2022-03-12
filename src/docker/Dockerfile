FROM adoptopenjdk/openjdk11:alpine-jre
# Refer to Maven build -> finalName
ARG JAR_FILE

# cd /opt/app
WORKDIR /target

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
