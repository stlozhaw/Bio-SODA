FROM maven:3.8.2-openjdk-16
COPY . /app/
WORKDIR /app
RUN mvn -Djavacpp.platform=linux-x86_64 package

FROM openjdk:11-jre-buster
WORKDIR /app
COPY --from=0 /app/target/output ./
VOLUME ["/app/language", "/app/config", "/app/sample_data"]
EXPOSE 8081
CMD ["java", "-cp", ".:language/*:lib/*:classes/", "ch.ethz.semdwhsearch.prototyp1.RunStandaloneSemDwhSearch"]
