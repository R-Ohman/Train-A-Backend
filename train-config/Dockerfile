FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="train-config"
LABEL org.opencontainers.image.authors="Ruslan (R-Ohman) Rabadanov"
LABEL org.opencontainers.image.source="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.url="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.vendor="Gdańsk University of Technology"
LABEL org.opencontainers.image.version="1.0.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Train A app, config server module."
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="1.0.0-SNAPSHOT"

ENV SERVER_PORT=8080

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8084/eureka
ENV EUREKA_INSTANCE_INSTANCE_ID=1

ENV SPRING_PROFILES_ACTIVE=native

ENV SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCH_LOCATIONS=file:/var/train-config/configuration

VOLUME /var/train-config/configuration

EXPOSE 8080

COPY target/train-config-${VERSION}.jar /opt/train-config/train-config.jar

CMD ["java", "-jar", "/opt/train-config/train-config.jar"]