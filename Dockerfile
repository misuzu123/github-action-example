FROM openjdk:21-jdk-slim
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} training.jar
ENTRYPOINT ["java","-jar","training.jar"]
