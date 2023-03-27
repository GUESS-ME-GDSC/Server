FROM openjdk:11
WORKDIR /var/deploy
EXPOSE 8080
ARG JAR_FILE=./build/libs/guessme-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# env
ENTRYPOINT ["java","-jar","/app.jar"]