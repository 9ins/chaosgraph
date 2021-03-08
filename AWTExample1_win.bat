@echo off

REM SET JAVA_HOME=C:\Program Files\ojdkbuild\java-1.8.0-openjdk-1.8.0.242-1

REM SET JAVA_EXEC=%JAVA_HOME%\bin

SET CLASS_PATH=./build/libs/chaosgraph-2.0.0.jar

java -classpath %CLASS_PATH% org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple1

pause