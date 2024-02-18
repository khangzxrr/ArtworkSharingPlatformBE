./gradlew -Pprod bootJar jibDockerBuild
docker image rm khangvnse141026/artwork-sharing-platform
docker image tag artworksharingplatformjhipter khangvnse141026/artwork-sharing-platform
docker push khangvnse141026/artwork-sharing-platform:latest
