FROM eclipse-temurin:17-jdk

COPY . /usr/src/api
WORKDIR /usr/src/api
RUN ./gradlew build

FROM eclipse-temurin:17-jre
COPY --from=0 /usr/src/api/build/libs/covid-api-0.0.1-SNAPSHOT.jar /usr/src
WORKDIR /usr/src
ENTRYPOINT java -jar covid-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080