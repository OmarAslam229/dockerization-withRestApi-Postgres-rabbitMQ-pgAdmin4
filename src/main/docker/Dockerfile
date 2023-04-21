# put jar file in src/main/docker folder

FROM openjdk:11

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]


## Base docker image
#FROM openjdk:11
#
##Create a new dorectory
##RUN mkdir /app
##
###Copy files from host mechine to image fileSYstem , in this case we have jar
##ADD ../../../build/libs/assignment-0.0.1-SNAPSHOT.jar springboot-docker.jar
##
##ADD target/spring-postgres-docker-0.0.1-SNAPSHOT.jar app.jar
#
#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} application.jar
##Furute commands
## WORKDIR /app
#
#EXPOSE 8081
#
##Running the main Class
## CMD java AssignmentApplication
#
#ENTRYPOINT ["java", "-jar", "application.jar"]
#
##open current driectory in terminal . project window -> Right click on projct title, open in -> terminal
##run this command docker build -t springboot-docker-firstimage:1.0 .
## to see all docker imagies use command docker images


