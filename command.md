# Generate entity

jhipster jdl artwork.jh

# Sonar analyst

./gradlew -Pprod clean check jacocoTestReport sonarqube -Dsonar.login=admin -Dsonar.password=admin
