@ECHO OFF

SETLOCAL

    call setenv

    echo.
    echo ********** ��ʼ��ر��� ********** | tee -a  %BATCHLOGFILE%

    call job_invoker.bat BCB8001 L %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg

    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j

    echo  ���ݲ�������:%SYSDATE% | tee -a  %BATCHLOGFILE%

    mkdir z:\%SYSDATE%  >>  %BATCHLOGFILE%
    xcopy /s %BACKUP_DIR%\%SYSDATE%  z:\%SYSDATE%  >>  %BATCHLOGFILE%
    echo.
    echo ********** ��ر������!********** | tee -a  %BATCHLOGFILE%
    echo.
    goto end

:errmsg
   echo.
   echo.
   echo ********** ��������ȫ���ݴ���!********** | tee -a  %BATCHLOGFILE%
   echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** ���Դ��󲢼�����?"
   if ERRORLEVEL 2 goto errmsg
   echo ** ���Դ��󣬼�������......
   echo.
   echo.

:end
ENDLOCAL