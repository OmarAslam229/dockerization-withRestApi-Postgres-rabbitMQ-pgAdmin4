# Base docker image
FROM openjdk:11

#Create a new dorectory
RUN mkdir /app

#Copy files from host mechine to image fileSYstem , in this case we have jar
ADD gradle/wrapper/gradle-wrapper.jar springboot-docker.jar

#Furute commands
# WORKDIR /app

#Running the main Class
# CMD java AssignmentApplication

ENTRYPOINT ["java", "-jar", "springboot-docker.jar"]

#open current driectory in terminal . project window -> Right click on projct title, open in -> terminal
#run this command docker build -t springboot-docker-firstimage:1.0 .
# to see all docker imagies use command docker images
