@ECHO OFF

    REM ��������CLASS·��
    SET PRJPATH=F:\svn-fbicbs\out\artifacts\fbicbs

    REM ����������·��
    SET BATCHROOTPATH=d:\fbicbs\batch

    REM ��������������·��
    SET BACKUP_DIR=%BATCHROOTPATH%\backup

    REM ����������־·��
    SET BATCHLOGFILE=%BATCHROOTPATH%\log\batchlog.txt

    SET classpath=%JAVA_HOME%\jre\lib\alt-rt.jar;%JAVA_HOME%\jre\lib\charsets.jar;%JAVA_HOME%\jre\lib\rt.jar

    SET classpath=%CLASSPATH%;%PRJPATH%\WEB-INF\classes

    FOR %%i IN ("%PRJPATH%\WEB-INF\lib\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%~fi
