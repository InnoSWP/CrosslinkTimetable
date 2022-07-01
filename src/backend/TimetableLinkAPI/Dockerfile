FROM maven as builder
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY . .
RUN mvn package

FROM openjdk:17
#EXPOSE 8080
COPY --from=builder /usr/src/app/target/*.jar ./
ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]
