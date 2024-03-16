FROM maven:3.9.5-amazoncorretto-17 as maven

WORKDIR /usr/src/app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

ARG JAR_FILE=selfordermanagement.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=live","-jar","app.jar"]
