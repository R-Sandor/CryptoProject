#!/bin/bash 
NPM_VERSION=`npm -v`
NODE_VERSION=`node --version`
if [ "$NPM_VERSION" != "8.5.5" ]; then
    echo "Please Install NPM version 8.5.5 or issue npm install -g npm@8.5.5"
    exit
fi 
if [ "$NODE_VERSION" != "v16.15.0" ]; then
    echo "Please Install the correct node."
    echo "nvm install 16.5.0"
    exit
fi 

## Borrowed from https://stackoverflow.com/questions/7334754/correct-way-to-check-java-version-from-bash-script
## glenn jackman
if [ `type -p java` ]; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"

else
    echo "no java"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 '/version/ {print $2}')
    if [[ "$version" < "17" ]]; then
        echo "version is less than 17"
        echo "Install or set the correct java version"
        exit
    fi
fi
echo "Starting up Crypto Benchmark"
cd frontend && npm install && npm run serve&
cd frontend
cd ../server
./gradlew clean build 
java -jar build/libs/CryptoProjectFinal-0.0.1-SNAPSHOT.jar

