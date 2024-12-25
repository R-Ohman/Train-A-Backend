#!/usr/bin/env bash

# Builds all applications and all docker images using Dockerfile and tags it based on org.opencontainers.image.version
# label in Dockerfile.

#######################################
# Script main function. Builds all applications and all docker images using Dockerfile and tags it based on
# org.opencontainers.image.version label in Dockerfile.
# Arguments:
#   None.
#######################################
function main() {
    cd ./train-user/; sh ./build.sh; cd ..
    cd ./train-railway/; sh ./build.sh; cd ..
    cd ./train-gateway/; sh ./build.sh; cd ..
    cd ./train-eureka/; sh ./build.sh; cd ..
}

main "$@"

