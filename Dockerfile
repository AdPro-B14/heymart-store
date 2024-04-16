FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/heymart-auth
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USERNAME=heymart
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USERNAME} && \
    adduser -S -u ${USER_UID} -G ${USERNAME} ${USERNAME}

USER ${USERNAME}
WORKDIR /opt/heymart-auth
EXPOSE 8080
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/heymart-auth/build/libs/*.jar app.jar

ENTRYPOINT [ "java" ]
CMD [ "-jar", "app.jar" ]