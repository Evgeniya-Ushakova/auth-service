FROM openjdk:17-jdk-slim-buster

ARG APP_HOME=/app
WORKDIR $APP_HOME
COPY build/libs/auth-service.jar $APP_HOME/auth-service.jar

ENTRYPOINT exec java $JAVA_OPTS -jar ./auth-service.jar