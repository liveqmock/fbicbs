@echo off
echo.
echo.
color a
cls
@echo off

SETLOCAL

call setenv.bat

:start
echo.
echo.
echo              +------------------------------------------------------+
echo              ^|                                                      ^|
echo              ^|             CBS����ҵ��ϵͳ�������ػ���              ^|
echo              ^|                                                      ^|
echo              +------------------------------------------------------+
echo              ^|              1_ ��  ��  ǰ  ��  ��                   ^|
echo              ^|                                                      ^|
echo              ^|              2_ ϵ  ͳ  ��  ��                       ^|
echo              ^|                                                      ^|
echo              ^|              3_ ��  ��  ��  ��  ��                   ^|
echo              ^|                                                      ^|
echo              ^|              4_ �ָ�����ǰ��������                   ^|
echo              ^|                                                      ^|
echo              ^|              5_ �ָ������󱸷�����                   ^|
echo              ^|                                                      ^|
echo              ^|              6_ �����ӡ                             ^|
echo              ^|                                                      ^|
echo              ^|              7_ ��  ��  ��  ��                       ^|
echo              ^|                                                      ^|
echo              ^|              0_ ��  ��                               ^|
echo              +------------------------------------------------------+
echo.
set /p UserChoice=��ѡ��:
if "%UserChoice%"=="0" goto 0
if "%UserChoice%"=="1" goto 1
if "%UserChoice%"=="2" goto 2
if "%UserChoice%"=="3" goto 3
if "%UserChoice%"=="4" goto 4
if "%UserChoice%"=="5" goto 5
if "%UserChoice%"=="6" goto 6
if "%UserChoice%"=="7" goto 7

cls
echo.
echo.
echo.
echo    ��ѡ����ȷ�Ĳ˵���!
goto start
:0
@pause
exit

:1
@color a
@echo ��ȷ�����й�Ա���˳�...
@pause
@echo ����ǰ���ݿ�ʼ...
@pause

    call bk_b.bat

@echo.
@echo ����ǰ���ݽ���...
@pause
@cls
@goto start

:2
@color a
@echo ��ȷ�����������ǰ����...
@pause
@echo.
@echo ������ʼ...
@echo ���ڲ�ѯ��ǰҵ�����ڣ����Ժ�...

    call job_invoker.bat BCB8001 C %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg
    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j
    echo.
    echo ��ǰҵ������:%SYSDATE% | tee -a  %BATCHLOGFILE%
    echo.
    choice /M  "�Ƿ����?"
    if ERRORLEVEL 2 goto start
    call job_ac.bat

    call job_invoker.bat BCB8001 C %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg
    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j
    echo.
    echo ��ǰҵ������:%SYSDATE% | tee -a  %BATCHLOGFILE%
    echo.

@echo ��������...
@echo.
@pause
cls
goto start

:3
@color a
@echo �����󱸷ݿ�ʼ...
@pause
 call bk_e.bat
@echo �����󱸷ݽ���...
@pause
cls
goto start

:4
@color c
@echo �ָ�����ǰ���ݿ�ʼ...
@pause
  call imp_b.bat
@echo.
@echo �ָ�����ǰ���ݽ���...
@pause
@color a
cls
goto start

:5
@color c
@echo �ָ������󱸷ݿ�ʼ...
@pause
 call imp_e.bat
@echo �ָ������󱸷ݽ���...
@pause
@color a
cls
goto start

:6
@color c
@pause
 call cbsprt.bat
@pause
@color a
cls
goto start

:7
@color a
@echo ��ر��ݿ�ʼ...
@pause
 call bk_yd.bat
@echo ��ر��ݽ���...
@pause
cls
goto start

:errmsg
   echo.
   echo.
   echo ********** �鿴��ǰ���ڴ���!********** | tee -a  %BATCHLOGFILE%
   echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** ���Դ��󲢼�����?"
   if ERRORLEVEL 2 goto errmsg
   echo ** ���Դ��󣬼�������......
   echo.
   echo.
cls
goto start
ENDLOCAL
