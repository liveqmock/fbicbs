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
echo              ^|              1_ 日结单                               ^|
echo              ^|                                                      ^|
echo              ^|              2_ 余额表                               ^|
echo              ^|                                                      ^|
echo              ^|              3_ 月计表                               ^|
echo              ^|                                                      ^|
echo              ^|              4_ 总账表                               ^|
echo              ^|                                                      ^|
echo              ^|              5_ 对账单                               ^|
echo              ^|                                                      ^|
echo              ^|              6_ 对账单回执                           ^|
echo              ^|                                                      ^|
echo              ^|              7_ 损益表                               ^|
echo              ^|                                                      ^|
echo              ^|              8_ 对账单(条件)                         ^|
echo              ^|                                                      ^|
echo              ^|              9_ 总账(条件)                           ^|
echo              ^|                                                      ^|
echo              ^|             10_ 对账单回执(条件)                     ^|
echo              ^|                                                      ^|
echo              ^|             11_ 计息报表                             ^|
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
if "%UserChoice%"=="8" goto 8
if "%UserChoice%"=="9" goto 9
if "%UserChoice%"=="10" goto 10
if "%UserChoice%"=="11" goto 11
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
@echo 日结单打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\日结单.txt

@echo.
@echo 日结单打印结束...
@pause
@cls
@goto report

:2
@color a
@echo 余额表打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...

    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\简明余额表.txt

@echo.
@echo 余额表打印结束...
@pause
@cls
@goto report

:3
@color a
@echo 月计表打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\月计表.txt

@echo.
@echo 月计表打印结束...
@pause
@cls
@goto report

:4
@color a
@echo 总账表打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\总账.txt

@echo.
@echo 总账表打印结束...
@pause
@cls
@goto report

:5
@color a
@echo 对账单打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\对账单.txt

@echo.
@echo 对账单打印结束...
@pause
@cls
@goto report

:6
@color a
@echo 对账单回执打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\存款对账单回执.txt

@echo.
@echo 对账单回执打印结束...
@pause
@cls
@goto report

:7
@color a
@echo 损益表打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo 日期： %BusinessDate%
@echo 报表开始打印...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\损益表.txt

@echo.
@echo 损益表打印结束...
@pause
@cls
@goto report


:8
@color a
call nsmqry.bat
@echo.
@echo 对账单已出...
@pause
@cls
@goto report

:9
@echo 总账表（条件）打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
set beginPnum=""
set /p beginPnum=请输入起始页（必输项）：
set endPnum=""
set /p endPnum=请输入截止页（默认为最后一页）：

if %beginPnum%=="" goto error
set filename="总账"
@echo 日期： %BusinessDate%
@echo 报表开始打印...
    call job_invokerRpt.bat saveprtfile %BusinessDate% %filename% %beginPnum% %endPnum% > %BATCHROOTPATH%/tmp/SAVEPRTFILE
    set return_code=%1
    if "%return_code%" == "0" print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\prttmp.txt
@echo.
@echo 总账表打印结束...
@pause
@cls
@goto report

:10
@echo 对账单回执(条件)打印即将开始...
set BusinessDate=""
set /p BusinessDate=请输入要打印的报表日期（例:20110314，默认为上一业务执行日期）:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
set beginPnum=""
set /p beginPnum=请输入起始页（必输项）：
set endPnum=""
set /p endPnum=请输入截止页（默认为最后一页）：

if %beginPnum%=="" goto error
set filename="对账单回执"
@echo 日期： %BusinessDate%
@echo 报表开始打印...
    call job_invokerRpt.bat saveprtfile %BusinessDate% %filename% %beginPnum% %endPnum% > %BATCHROOTPATH%/tmp/SAVEPRTFILE
    set return_code=%1
    if "%return_code%" == "0" print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\prttmp.txt
@echo.
@echo 对账单回执打印结束...
@pause
@cls
@goto report

:11
@color c
@pause
 call acbintRpt.bat
@pause
@color a
@cls
@goto report

:error
   @echo 必须输入起始页码 请重新输入...
   @pause
   @goto report

