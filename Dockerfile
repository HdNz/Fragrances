FROM amd64/openjdk:21
ADD target/hd-nz-1.0-SNAPSHOT-runner.jar .
ADD fragrances.json .
EXPOSE 8080
ENV PORT=8080

CMD java -Dquarkus.http.port=${PORT} -jar hd-nz-1.0-SNAPSHOT-runner.jar