#!/usr/bin/env bash

# Builds application and docker image using Dockerfile and tags it based on org.opencontainers.image.version label in
# Dockerfile.

#######################################
# Script main function. Builds application and docker image using Dockerfile and tags it based on
# org.opencontainers.image.version label in Dockerfile.
# Arguments:
#   None.
#######################################
main() {
    export JAVA_HOME=/usr/lib/jvm/java-17
    mvn clean verify

    if [ ! -f Dockerfile ]; then
        echo "Dockerfile not found!"
        exit 1
    fi

    title="$(grep "org.opencontainers.image.title" Dockerfile | cut -f2 -d "=" | xargs)"
    version="$(grep "org.opencontainers.image.version" Dockerfile | cut -f2 -d "=" | xargs)"

    if [ -z "$title" ] || [ -z "$version" ]; then
        echo "Title or version not found in Dockerfile!"
        exit 1
    fi

    docker build \
      --label "org.opencontainers.image.created=$(date +%Y-%m-%dT%H:%M:%S%:z)" \
      --label "org.opencontainers.image.ref.name=$(git rev-parse HEAD)" \
      --label "org.opencontainers.image.revision=$(git rev-parse HEAD)" \
      -t "${title}:${version}" .
}

main "$@"
