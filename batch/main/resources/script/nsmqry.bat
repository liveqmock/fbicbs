 @ECHO OFF

SETLOCAL

call setenv

:start
   set a1=""
   set/p a1=输入账号:
   set a2=""
   set/p a2=输入起始页:
   set a3=""
   set/p a3=输入截止页:
 if %a1%=="" goto error


:exec
     call job_invokerRpt.bat RPVD0003 %a1% %a2% %a3% > %BATCHROOTPATH%/tmp/RPVD0003_PART
     set return_code=%1
     if  "%return_code%" == "0" goto :print

:print
    @echo 报表开始打印...
          print /d:lpt1 D:\fbicbs\batch\report\stm\010\对账单.txt
    @echo.
    @echo 对账单打印结束...
    @pause
    call cbsprt.bat

:error
   @echo 必须输入账号 请重新输入...
   goto start