@ECHO OFF

    REM 设置批量CLASS路径
    SET PRJPATH=F:\svn-fbicbs\out\artifacts\fbicbs

    REM 设置批量主路径
    SET BATCHROOTPATH=d:\fbicbs\batch

    REM 设置批量备份主路径
    SET BACKUP_DIR=%BATCHROOTPATH%\backup

    REM 设置批量日志路径
    SET BATCHLOGFILE=%BATCHROOTPATH%\log\batchlog.txt

    SET classpath=%JAVA_HOME%\jre\lib\alt-rt.jar;%JAVA_HOME%\jre\lib\charsets.jar;%JAVA_HOME%\jre\lib\rt.jar

    SET classpath=%CLASSPATH%;%PRJPATH%\WEB-INF\classes

    FOR %%i IN ("%PRJPATH%\WEB-INF\lib\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fi
