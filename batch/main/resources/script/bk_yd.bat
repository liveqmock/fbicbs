@ECHO OFF

SETLOCAL

    call setenv

    echo.
    echo ********** 开始异地备份 ********** | tee -a  %BATCHLOGFILE%

    call job_invoker.bat BCB8001 L %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg

    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j

    echo  备份操作日期:%SYSDATE% | tee -a  %BATCHLOGFILE%

    mkdir z:\%SYSDATE%  >>  %BATCHLOGFILE%
    xcopy /s %BACKUP_DIR%\%SYSDATE%  z:\%SYSDATE%  >>  %BATCHLOGFILE%
    echo.
    echo ********** 异地备份完成!********** | tee -a  %BATCHLOGFILE%
    echo.
    goto end

:errmsg
   echo.
   echo.
   echo ********** 批量后完全备份错误!********** | tee -a  %BATCHLOGFILE%
   echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** 忽略错误并继续吗?"
   if ERRORLEVEL 2 goto errmsg
   echo ** 忽略错误，继续处理......
   echo.
   echo.

:end
ENDLOCAL