 @ECHO OFF

SETLOCAL

call setenv
call lastdtqry.bat

:start
   set a0=%SYSDATE%
   set/p a0=�������������ڣ���:20110314��Ĭ��Ϊ��һҵ��ִ�����ڣ�:
   set a1=""
   set/p a1=�������˺�:
   set a2=""
   set/p a2=��������ʼҳ:
   set a3=""
   set/p a3=�������ֹҳ:
 if %a1%=="" goto error


:exec
     call job_invokerRpt.bat RPVD0003 %a0% %a1% %a2% %a3% > %BATCHROOTPATH%/tmp/RPVD0003_PART
     set return_code=%1
     if  "%return_code%" == "0" goto :print

:print
    @echo ����ʼ��ӡ...
          print /d:%PRINTERPORT% %BATCHROOTPATH%\report\stm\010\���˵�.txt
    @echo.
    @echo ���˵���ӡ����...
    @pause
    call cbsprt.bat

:error
   @echo ���������˺� ����������...
   goto start