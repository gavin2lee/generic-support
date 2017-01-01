@echo off
setlocal

set "CLASSPATH=./libs/commons-codec-1.9.jar"
set "CLASSPATH=%CLASSPATH%;./libs/commons-io-2.5.jar"
set "CLASSPATH=%CLASSPATH%;./libs/commons-lang3-3.4.jar"
set "CLASSPATH=%CLASSPATH%;./libs/jcl-over-slf4j-1.7.13.jar"
set "CLASSPATH=%CLASSPATH%;./libs/logback-classic-1.1.3.jar"
set "CLASSPATH=%CLASSPATH%;./libs/logback-core-1.1.3.jar"
set "CLASSPATH=%CLASSPATH%;./libs/slf4j-api-1.7.13.jar"
set "CLASSPATH=%CLASSPATH%;./libs/support-common-util-1.0.0.0-SNAPSHOT.jar"

set "EXECUTOR="

if not "%JAVA_HOME%" == "" (
    set "EXECUTOR=%JAVA_HOME%\bin\java.exe"
) else (
		set "EXECUTOR=java"
)

set "MAIN=com.lachesis.support.common.util.crypt.bootstrap.ConfigCryptTool"

rem echo %CLASSPATH%


%EXECUTOR% -cp %CLASSPATH% %MAIN% %*