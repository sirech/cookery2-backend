FROM adoptopenjdk/openjdk14:jre-14.0.1_7-alpine

WORKDIR /app
EXPOSE 4003

ENV ENV='dev'

RUN apk add --update --no-cache dumb-init \
  && rm -rf /var/cache/apk/*

COPY build/libs/*.jar app.jar

RUN adduser -D runner

USER runner
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["java", "-Dspring.profiles.active=${ENV}", "-jar", "app.jar" ]
