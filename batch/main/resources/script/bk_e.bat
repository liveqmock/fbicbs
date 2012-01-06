@ECHO OFF

SETLOCAL

    call setenv

    set BACKUP_DIR_TMP=%BACKUP_DIR%\bk_e

    del %BACKUP_DIR_TMP%\*.dmp | tee -a  %BATCHLOGFILE%

    echo.
    echo ********** 开始批量后完全备份 ********** | tee -a  %BATCHLOGFILE%

    call job_invoker.bat BCB8001 L %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg

    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j

    echo  备份操作日期:%SYSDATE% | tee -a  %BATCHLOGFILE%

    exp cbs/cbs@orcl file=%BACKUP_DIR_TMP%\db_b.dmp log=%BACKUP_DIR_TMP%\bk_e.log direct=y buffer=4096000   2>> %BATCHLOGFILE%

    if ERRORLEVEL 1 goto errmsg

    echo.
    echo **********备份bk_b bk_e bkrpt********* | tee -a  %BATCHLOGFILE%
    mkdir %BACKUP_DIR%\%SYSDATE%  >>  %BATCHLOGFILE%
	mkdir %BACKUP_DIR%\%SYSDATE%\bk_b >>  %BATCHLOGFILE%
	mkdir %BACKUP_DIR%\%SYSDATE%\bk_e  >>  %BATCHLOGFILE%
	mkdir %BACKUP_DIR%\%SYSDATE%\bkrpt  >>  %BATCHLOGFILE%

	copy %BACKUP_DIR%\bk_b\*.*  %BACKUP_DIR%\%SYSDATE%\bk_b  >>  %BATCHLOGFILE%
	copy %BACKUP_DIR%\bk_e\*.*  %BACKUP_DIR%\%SYSDATE%\bk_e   >>  %BATCHLOGFILE%
	xcopy /E %BATCHROOTPATH%\report\%SYSDATE%\*  %BACKUP_DIR%\%SYSDATE%\bkrpt >>  %BATCHLOGFILE%
	copy %BATCHROOTPATH%\tmp\*  %BACKUP_DIR%\%SYSDATE%\bkrpt  >>  %BATCHLOGFILE%
	copy %BATCHROOTPATH%\log\*  %BACKUP_DIR%\%SYSDATE%\bkrpt 
	rem copy %BATCHROOTPATH%\print\*  %BACKUP_DIR%\%SYSDATE%\bkrpt  >>  %BATCHLOGFILE%
	rem copy %BATCHROOTPATH%\print\rpfx2005  %BACKUP_DIR%\%SYSDATE%\bkrpt\.  >>  %BATCHLOGFILE%

    echo.
    echo ********** 批量后完全备份完成!********** | tee -a  %BATCHLOGFILE%
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