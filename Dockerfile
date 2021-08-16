FROM adoptopenjdk/openjdk11
WORKDIR /
ADD Audit-severity-SNAPSHOT.jar Audit-severity-SNAPSHOT.jar
CMD java -jar Audit-severity-SNAPSHOT.jar