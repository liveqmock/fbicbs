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
echo              ^|              1_ ��Ϣ֪ͨ��                           ^|
echo              ^|                                                      ^|
echo              ^|              2_ ��Ϣ�嵥                             ^|
echo              ^|                                                      ^|
echo              ^|              3_ δ��Ϣ�嵥                           ^|
echo              ^|                                                      ^|
echo              ^|              4_ ��Ϣ��Ʊ                             ^|
echo              ^|                                                      ^|
echo              ^|              5_ ת�˽跽ƾ֤                         ^|
echo              ^|                                                      ^|
echo              ^|              6_ ��Ϣ֪ͨ������ҳ��ӡ��               ^|
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
@echo ��Ϣ֪ͨ����ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=�������Ϣ���ڣ���:20110620��:
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\����Ϣ֪ͨ��8910out.txt
@echo.
@echo ��Ϣ֪ͨ����ӡ����...
@pause
@cls
@goto report

:2
@color a
@echo ��Ϣ�嵥��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=�������Ϣ���ڣ���:20110620��:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...

    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\����Ϣ�嵥8910lst.txt

@echo.
@echo ��Ϣ�嵥��ӡ����...
@pause
@cls
@goto report

:3
@color a
@echo δ��Ϣ�嵥��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=�������Ϣ���ڣ���:20110620��:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\���δ��Ϣ�嵥8910err.txt

@echo.
@echo δ��Ϣ�嵥��ӡ����...
@pause
@cls
@goto report

:4
@color a
@echo ��Ϣ��Ʊ��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=�������Ϣ���ڣ���:20110620��:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\����Ϣ��Ʊ8910vch.txt

@echo.
@echo ��Ϣ��Ʊ��ӡ����...
@pause
@cls
@goto report

:5
@color a
@echo ת�˽跽ƾ֤��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=�������Ϣ���ڣ���:20110620��:
if %BusinessDate%=="" set BusinessDate=%SYSDATE%
echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\%BusinessDate%\010\ת�˽跽ƾ֤.txt

@echo.
@echo ת�˽跽ƾ֤��ӡ����...
@pause
@cls
@goto report

:6
@echo ��Ϣ֪ͨ����ҳ��ӡ������ʼ...
set BusinessDate=""
set /p BusinessDate=������Ҫ��ӡ�ı������ڣ���:20110620��:
set beginPage=""
set /p beginPage=��������ʼҳ���������
set endPage=""
set /p endPage=�������ֹҳ���������

if %beginPage%=="" goto error
set filename="����Ϣ֪ͨ��8910out.txt"
@echo ���ڣ� %BusinessDate%
@echo ����ʼ��ӡ...
    call job_invokerRpt.bat PageAcbint %BusinessDate% %filename% %beginPage% %endPage% > %BATCHROOTPATH%/tmp/pageacbint
    print /d:%PRINTERPORT% %BATCHROOTPATH%\report\tmp\��Ϣ֪ͨ��8910out_��ҳ.txt
@echo.
@echo ��Ϣ֪ͨ����ҳ��ӡ����...
@pause
@cls
@goto report


