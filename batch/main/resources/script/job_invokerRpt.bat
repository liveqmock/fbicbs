@ECHO OFF

SETLOCAL  EnableDelayedExpansion

    call setenv.bat

    REM java -DbasePath=%PRJPATH%/WEB-INF -DpropertyFile=%PRJPATH%\WEB-INF\classes\cbs\batch\report\%1\prop\J%1.xml  -DjobId=J%1 cbs.batch.common.BatchLauncher  >> %BATCHLOGFILE%

    REM java -DmoduleId=report  -DjobId=J%1 cbs.batch.common.BatchLauncher  %2 %3 %4 %5

    java -DmoduleId=report  -DjobId=%1 cbs.batch.common.BatchLauncher  %2 %3 %4 %5
ENDLOCAL