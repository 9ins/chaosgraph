#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export JAVA_EXEC=$JAVA_HOME/bin
export CLASS_PATH=./build/libs/chaosgraph-2.1.2.jar
$JAVA_EXEC/java -classpath $CLASS_PATH org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple2
