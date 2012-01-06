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
echo              ^|             CBS核心业务系统批量主控画面              ^|
echo              ^|                                                      ^|
echo              +------------------------------------------------------+
echo              ^|              1_ 批  量  前  备  份                   ^|
echo              ^|                                                      ^|
echo              ^|              2_ 系  统  批  量                       ^|
echo              ^|                                                      ^|
echo              ^|              3_ 批  量  后  备  份                   ^|
echo              ^|                                                      ^|
echo              ^|              4_ 恢复批量前备份数据                   ^|
echo              ^|                                                      ^|
echo              ^|              5_ 恢复批量后备份数据                   ^|
echo              ^|                                                      ^|
echo              ^|              6_ 报表打印                             ^|
echo              ^|                                                      ^|
echo              ^|              7_ 异  地  备  份                       ^|
echo              ^|                                                      ^|
echo              ^|              0_ 退  出                               ^|
echo              +------------------------------------------------------+
echo.
set /p UserChoice=请选择:
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
echo    请选择正确的菜单项!
goto start
:0
@pause
exit

:1
@color a
@echo 请确认所有柜员已退出...
@pause
@echo 批量前备份开始...
@pause

    call bk_b.bat

@echo.
@echo 批量前备份结束...
@pause
@cls
@goto start

:2
@color a
@echo 请确认已完成批量前备份...
@pause
@echo.
@echo 批量开始...
@echo 正在查询当前业务日期，请稍候...

    call job_invoker.bat BCB8001 C %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg
    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j
    echo.
    echo 当前业务日期:%SYSDATE% | tee -a  %BATCHLOGFILE%
    echo.
    choice /M  "是否继续?"
    if ERRORLEVEL 2 goto start
    call job_ac.bat

    call job_invoker.bat BCB8001 C %BATCHROOTPATH%\db > %BATCHROOTPATH%\tmp\BCB8001
    if ERRORLEVEL 1 goto errmsg
    for /F "tokens=1,2 delims==" %%i in (%BATCHROOTPATH%\db\SYSDATE.txt) do set SYSDATE=%%j
    echo.
    echo 当前业务日期:%SYSDATE% | tee -a  %BATCHLOGFILE%
    echo.

@echo 批量结束...
@echo.
@pause
cls
goto start

:3
@color a
@echo 批量后备份开始...
@pause
 call bk_e.bat
@echo 批量后备份结束...
@pause
cls
goto start

:4
@color c
@echo 恢复批量前备份开始...
@pause
  call imp_b.bat
@echo.
@echo 恢复批量前备份结束...
@pause
@color a
cls
goto start

:5
@color c
@echo 恢复批量后备份开始...
@pause
 call imp_e.bat
@echo 恢复批量后备份结束...
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
@echo 异地备份开始...
@pause
 call bk_yd.bat
@echo 异地备份结束...
@pause
cls
goto start

:errmsg
   echo.
   echo.
   echo ********** 查看当前日期错误!********** | tee -a  %BATCHLOGFILE%
   echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
   choice /M  "** 忽略错误并继续吗?"
   if ERRORLEVEL 2 goto errmsg
   echo ** 忽略错误，继续处理......
   echo.
   echo.
cls
goto start
ENDLOCAL
