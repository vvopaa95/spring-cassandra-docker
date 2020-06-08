# syntax=docker/dockerfile:experimental
FROM sapmachine/jdk11:latest as build
#Working directory inside container
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
#Build jar
RUN ./mvnw install -DskipTests
#Unzip jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM sapmachine/jdk11:latest
#Directory in docker container for saving files
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY wait-for-it.sh /usr/bin/wait-for-it.sh
RUN chmod +x /usr/bin/wait-for-it.sh
ENTRYPOINT ["wait-for-it.sh", "-t", "300", "cassandra-node-3:9042","--", "java","-cp","app:app/lib/*","com.vvopaa.cassandra.Application"]