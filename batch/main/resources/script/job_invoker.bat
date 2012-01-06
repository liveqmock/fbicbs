@ECHO OFF

SETLOCAL  EnableDelayedExpansion

    call setenv.bat

    REM java -DbasePath=%PRJPATH%/WEB-INF -DpropertyFile=%PRJPATH%\WEB-INF\classes\cbs\batch\ac\%1\prop\J%1.xml  -DjobId=J%1 cbs.batch.common.BatchLauncher  >> %BATCHLOGFILE%

    java -DmoduleId=ac  -DjobId=J%1 cbs.batch.common.BatchLauncher  %2 %3 %4

ENDLOCAL