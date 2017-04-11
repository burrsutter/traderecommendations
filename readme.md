## Run for local development
allows for hot redeployment after .java files have been edited and saved

mvn compile vertx:run

## Run by creating a fat jar

mvn package

java -jar target/traderecommendations-1.0-SNAPSHOT.jar

## Run on OpenShift

#### Via the Java S2I
oc create -f https://gist.githubusercontent.com/tqvarnst/3ca512b01b7b7c1a1da0532939350e23/raw/3869a54c7dd960965f0e66907cdc3eba6d160cad/openjdk-s2i-imagestream.json

oc new-app redhat-openjdk18-openshift~https://github.com/burrsutter/traderecommendations

oc expose service traderecommendations

oc get routes

curl traderecommendations-trader.192.168.99.100.nip.io

