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
echo              ^|             CBS����ҵ��ϵͳ�����ӡ����              ^|
echo              ^|                                                      ^|
echo              +------------------------------------------------------+
echo              ^|              1_ �սᵥ                               ^|
echo              ^|                                                      ^|
echo              ^|              2_ ����                               ^|
echo              ^|                                                      ^|
echo              ^|              3_ �¼Ʊ�                               ^|
echo              ^|                                                      ^|
echo              ^|              4_ ���˱�                               ^|
echo              ^|                                                      ^|
echo              ^|              5_ ���˵�                               ^|
echo              ^|                                                      ^|
echo              ^|              6_ ���˵���ִ                           ^|
echo              ^|                                                      ^|
echo              ^|              7_ �����                               ^|
echo              ^|                                                      ^|
echo              ^|              8_ ���˵�(����)                         ^|
echo              ^|                                                      ^|
echo              ^|              9_ ����(����)                           ^|
echo              ^|                                                      ^|
echo              ^|             10_ ���˵���ִ(����)                     ^|
echo              ^|                                                      ^|
echo              ^|             11_ ��Ϣ����                             ^|
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
if "%UserChoice%"=="8" goto 8
if "%UserChoice%"=="9" goto 9
if "%UserChoice%"=="10" goto 10
if "%UserChoice%"=="11" goto 11
cls
echo.
echo.
echo.
echo    ��ѡ����ȷ�Ĳ˵���!
goto report
:0
call cbsmain.bat
cls

:1
@color a
@echo �սᵥ��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\�սᵥ.txt

@echo.
@echo �սᵥ��ӡ����...
@pause
@cls
@goto report

:2
@color a
@echo �����ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...

    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\��������.txt

@echo.
@echo �����ӡ����...
@pause
@cls
@goto report

:3
@color a
@echo �¼Ʊ��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\�¼Ʊ�.txt

@echo.
@echo �¼Ʊ��ӡ����...
@pause
@cls
@goto report

:4
@color a
@echo ���˱��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\����.txt

@echo.
@echo ���˱��ӡ����...
@pause
@cls
@goto report

:5
@color a
@echo ���˵���ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\���˵�.txt

@echo.
@echo ���˵���ӡ����...
@pause
@cls
@goto report

:6
@color a
@echo ���˵���ִ��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\�����˵���ִ.txt

@echo.
@echo ���˵���ִ��ӡ����...
@pause
@cls
@goto report

:7
@color a
@echo ������ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\�����.txt

@echo.
@echo ������ӡ����...
@pause
@cls
@goto report


:8
@color a
call nsmqry.bat
@echo.
@echo ���˵��ѳ�...
@pause
@cls
@goto report

:9
@echo ���˱���������ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
set beginPnum=""
set /p beginPnum=��������ʼҳ���������
set endPnum=""
set /p endPnum=�������ֹҳ��Ĭ��Ϊ���һҳ����

if %beginPnum%=="" goto error
set filename="����"
@echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    call job_invokerRpt.bat saveprtfile %BusinessDate% %filename% %beginPnum% %endPnum% > %BATCHROOTPATH%/tmp/SAVEPRTFILE
    set return_code=%1
    if "%return_code%" == "0" print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\prttmp.txt
@echo.
@echo ���˱��ӡ����...
@pause
@cls
@goto report

:10
@echo ���˵���ִ(����)��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
set beginPnum=""
set /p beginPnum=��������ʼҳ���������
set endPnum=""
set /p endPnum=�������ֹҳ��Ĭ��Ϊ���һҳ����

if %beginPnum%=="" goto error
set filename="���˵���ִ"
@echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    call job_invokerRpt.bat saveprtfile %BusinessDate% %filename% %beginPnum% %endPnum% > %BATCHROOTPATH%/tmp/SAVEPRTFILE
    set return_code=%1
    if "%return_code%" == "0" print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\prttmp.txt
@echo.
@echo ���˵���ִ��ӡ����...
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
   @echo ����������ʼҳ�� ����������...
   @pause
   @goto report

