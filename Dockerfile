FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /usr/app
COPY build/libs/patients-0.0.1-SNAPSHOT.jar mediscreen-patients.jar
CMD ["java", "-jar", "mediscreen-patients.jar"]