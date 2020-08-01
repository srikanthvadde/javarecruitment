FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=build/libs/javacruitment-0.0.1.jar

# cd /opt/app
WORKDIR /opt/app

# cp build/libs/javacruitment-0.0.1.jar /opt/app/javacruitment.jar
COPY ${JAR_FILE} javacruitment.jar

# java -jar /opt/app/javacruitment.jar
ENTRYPOINT ["java","-jar","javacruitment.jar"]