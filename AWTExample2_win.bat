@echo off

REM SET JAVA_HOME=C:\j2sdk1.4.1_01

REM SET path=%JAVA_HOME%\bin

SET CLASS_PATH=./build/libs/chaosgraph-2.0.0.jar

java -classpath %CLASS_PATH% org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple2

pause