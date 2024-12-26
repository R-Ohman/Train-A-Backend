FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="train-railway"
LABEL org.opencontainers.image.authors="Ruslan (R-Ohman) Rabadanov"
LABEL org.opencontainers.image.source="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.url="https://github.com/R-Ohman/Train-A-Backend"
LABEL org.opencontainers.image.vendor="Gdańsk University of Technology"
LABEL org.opencontainers.image.version="1.0.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Train A app, railway module."
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="1.0.0-SNAPSHOT"

ENV SERVER_PORT=8080

ENV SPRING_CONFIG_IMPORT=optional:configserver:http://localhost:8085
ENV EUREKA_INSTANCE_INSTANCE_ID=1

EXPOSE 8080

COPY target/train-railway-${VERSION}.jar /opt/train-railway/train-railway.jar

CMD ["java", "-jar", "/opt/train-railway/train-railway.jar"]
