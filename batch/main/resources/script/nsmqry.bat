 @ECHO OFF

SETLOCAL

call setenv

:start
   set a1=""
   set/p a1=�����˺�:
   set a2=""
   set/p a2=������ʼҳ:
   set a3=""
   set/p a3=�����ֹҳ:
 if %a1%=="" goto error


:exec
     call job_invokerRpt.bat RPVD0003 %a1% %a2% %a3% > %BATCHROOTPATH%/tmp/RPVD0003_PART
     set return_code=%1
     if  "%return_code%" == "0" goto :print

:print
    @echo ����ʼ��ӡ...
          print /d:lpt1 D:\fbicbs\batch\report\stm\010\���˵�.txt
    @echo.
    @echo ���˵���ӡ����...
    @pause
    call cbsprt.bat

:error
   @echo ���������˺� ����������...
   goto start