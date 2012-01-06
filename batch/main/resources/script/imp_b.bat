@ECHO OFF

SETLOCAL

    call setenv

    set BACKUP_DIR_TMP=%BACKUP_DIR%\bk_b

    echo.
    echo ********** 开始恢复批量前备份 ********** | tee -a  %BATCHLOGFILE%
    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%

    sqlplus / as sysdba @getsession.sql >> %BATCHLOGFILE%
    sqlplus / as sysdba @killsession.sql >> %BATCHLOGFILE%
    sqlplus / as sysdba @trunc_all.sql >> %BATCHLOGFILE%

    imp cbs/cbs@orcl file=%BACKUP_DIR_TMP%\db_b.dmp log=%BACKUP_DIR_TMP%\imp.log commit=y ignore=y full=y  2>> %BATCHLOGFILE%

    if ERRORLEVEL 1 goto errmsg

    echo.
    echo ********** 恢复批量前备份完成!********** | tee -a  %BATCHLOGFILE%
    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
    echo.
    goto end
    
:errmsg
   echo.
   echo.
   echo ********** 恢复批量前备份出现错误!********** | tee -a  %BATCHLOGFILE%
   echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** 忽略错误并继续吗?"
   if ERRORLEVEL 2 goto errmsg
   echo ** 忽略错误，继续处理......
   echo.
   echo.

:end
ENDLOCAL