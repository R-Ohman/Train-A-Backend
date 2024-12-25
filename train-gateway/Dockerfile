FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="train-gateway"
LABEL org.opencontainers.image.authors="Ruslan (R-Ohman) Rabadanov"
LABEL org.opencontainers.image.source="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.url="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.vendor="Gdańsk University of Technology"
LABEL org.opencontainers.image.version="1.0.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Train A app, gateway module."
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="1.0.0-SNAPSHOT"

ENV SERVER_PORT=8080

ENV TRAIN_GATEWAY_HOST=localhost:8080

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8084/eureka
ENV EUREKA_INSTANCE_INSTANCE_ID=1

EXPOSE 8080

COPY target/train-gateway-${VERSION}.jar /opt/train-gateway/train-gateway.jar

CMD ["java", "-jar", "/opt/train-gateway/train-gateway.jar"]
