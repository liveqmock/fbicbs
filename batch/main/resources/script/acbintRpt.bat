@echo off
echo.
echo.
color a
cls
@echo off
 call lastdtqry.bat
:report
echo.
echo.
echo              +------------------------------------------------------+
echo              ^|                                                      ^|
echo              ^|             CBS核心业务系统报表打印画面              ^|
echo              ^|                                                      ^|
echo              +------------------------------------------------------+
echo              ^|              1_ 利息通知单                           ^|
echo              ^|                                                      ^|
echo              ^|              2_ 计息清单                             ^|
echo              ^|                                                      ^|
echo              ^|              3_ 未计息清单                           ^|
echo              ^|                                                      ^|
echo              ^|              4_ 计息传票                             ^|
echo              ^|                                                      ^|
echo              ^|              5_ 转账借方凭证                         ^|
echo              ^|                                                      ^|
echo              ^|              6_ 利息通知单（按页打印）               ^|
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
cls
echo.
echo.
echo.
echo    请选择正确的菜单项!
goto report
:0
call cbsmain.bat
cls

:1
@color a
@echo 利息通知单打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入计息日期（例:20110620）:
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\存款计息通知单8910out.txt
@echo.
@echo 利息通知单打印结束...
@pause
@cls
@goto report

:2
@color a
@echo 利息清单打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入计息日期（例:20110620）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...

    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\存款计息清单8910lst.txt

@echo.
@echo 利息清单打印结束...
@pause
@cls
@goto report

:3
@color a
@echo 未计息清单打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入计息日期（例:20110620）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\存款未计息清单8910err.txt

@echo.
@echo 未计息清单打印结束...
@pause
@cls
@goto report

:4
@color a
@echo 计息传票打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入计息日期（例:20110620）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\存款计息传票8910vch.txt

@echo.
@echo 计息传票打印结束...
@pause
@cls
@goto report

:5
@color a
@echo 转账借方凭证打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入计息日期（例:20110620）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\转账借方凭证.txt

@echo.
@echo 转账借方凭证打印结束...
@pause
@cls
@goto report

:6
@echo 利息通知单按页打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110620）:
set beginPage=""
set /p beginPage=请输入起始页（必输项）：
set endPage=""
set /p endPage=请输入截止页（必输项）：

if %beginPage%=="" goto error
set filename="存款计息通知单8910out.txt"
@echo 日期： %BusinessDate%
@echo 报表开始打印...
    call job_invokerRpt.bat PageAcbint %BusinessDate% %filename% %beginPage% %endPage% > %BATCHROOTPATH%/tmp/pageacbint
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\利息通知单8910out_按页.txt
@echo.
@echo 利息通知单按页打印结束...
@pause
@cls
@goto report


