FROM openjdk:11
ENV GOOGLE_APPLICATION_CREDENTIALS=./involuted-span-377818-befce0f2e2e3.json
ARG JAR_FILE=./build/libs/guessme-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY ./src/main/resources/involuted-span-377818-befce0f2e2e3.json ./
# env
ENTRYPOINT ["java","-jar","/app.jar"]
