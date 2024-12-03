FROM gradle:8.10.0-jdk21-alpine AS builder
ENV GRADLE_USER_HOME=/cache
ENV WORKDIR=/usr/src/app
WORKDIR $WORKDIR
RUN --mount=type=bind,target=.,rw \
    --mount=type=cache,target=$GRADLE_USER_HOME \
    ./gradlew -i jooqCodegen &&  \
    ./gradlew -i bootJar --stacktrace && \
    mv $WORKDIR/build/libs/mbga.jar /mbga.jar
RUN echo "Cache stage completed" && ls -la /mbga.jar

FROM eclipse-temurin:21-jdk-alpine AS RUNNER
ENV WORKDIR=/usr/src/app
WORKDIR $WORKDIR
EXPOSE 8080
RUN mkdir -p /var/log
RUN apk add -q tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone
COPY --from=builder /mbga.jar ./mbga.jar
CMD java --add-opens java.base/java.lang=ALL-UNNAMED -jar ./mbga.jar
