FROM openjdk:8-jre-alpine
# Refer to Maven build -> finalName
ARG JAR_FILE=build/libs/miniproject-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /target

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]