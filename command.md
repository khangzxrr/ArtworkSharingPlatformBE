# Generate entity

jhipster jdl artwork.jh

# Sonar analyst

./gradlew -Pprod clean check jacocoTestReport sonarqube -Dsonar.login=admin -Dsonar.password=admin

# Generate changelog from entity

./gradlew liquibaseDiffChangelog -PrunList=diffLog

# Ngrok test webhook

ngrok http --host-header="localhost:9000" --domain=notably-cosmic-snake.ngrok-free.app 9000

# Build docker image

./build_docker_image.sh
