@ECHO OFF

SETLOCAL

    call setenv

    set BACKUP_DIR_TMP=%BACKUP_DIR%\bk_e

    echo.
    echo ********** ��ʼ�ָ������󱸷� ********** | tee -a  %BATCHLOGFILE%
    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%

    sqlplus / as sysdba @getsession.sql >> %BATCHLOGFILE%
    sqlplus / as sysdba @killsession.sql >> %BATCHLOGFILE%
    sqlplus / as sysdba @trunc_all.sql >> %BATCHLOGFILE%

    imp cbs/cbs@orcl file=%BACKUP_DIR_TMP%\db_b.dmp log=%BACKUP_DIR_TMP%\imp.log commit=y ignore=y full=y  2>> %BATCHLOGFILE%

    if ERRORLEVEL 1 goto errmsg

    echo.
    echo ********** �ָ������󱸷����!********** | tee -a  %BATCHLOGFILE%
    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
    echo.
    goto end
    
:errmsg
   echo.
   echo.
   echo ********** �ָ������󱸷ݳ��ִ���!********** | tee -a  %BATCHLOGFILE%
   echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** ���Դ��󲢼�����?"
   if ERRORLEVEL 2 goto errmsg
   echo ** ���Դ��󣬼�������......
   echo.
   echo.

:end
ENDLOCAL