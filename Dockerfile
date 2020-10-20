FROM adoptopenjdk/openjdk11:ubi

ARG JAR_FILE=target/covid-0.0.1.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]