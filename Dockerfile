FROM maven:3.6.3-jdk-8 as build

WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn clean
RUN mvn package

FROM tomcat:9.0-alpine

COPY --from=build /app/target/Loghme.war /usr/local/tomcat/webapps/ROOT.war
RUN rm -r ./webapps/ROOT

CMD ["catalina.sh", "run"]