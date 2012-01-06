::##############################################################
::#  程序名       job_acRpt.bat                                #
::#  程序功能:	 报表批量作业                                  #
::#  编写者:      haiyu                                        #
::##############################################################

@ECHO OFF

SETLOCAL

    call setenv
    echo.
    echo ********** 开始生成报表 ********** | tee -a  %BATCHLOGFILE%

    echo ** RPACTVCH日结单...... | tee -a %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTVCH > %BATCHROOTPATH%/tmp/RPACTVCH
    call :errexit %ERRORLEVEL% RPACTVCH

    echo ** RPTOBF余额表...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPTOBF > %BATCHROOTPATH%/tmp/RPTOBF
    call :errexit %ERRORLEVEL% RPTOBF

    echo ** OldBalance余额表...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat OldBalance > %BATCHROOTPATH%/tmp/OldBalance
    call :errexit %ERRORLEVEL% OldBalance

    echo ** RPXX1001D日计表...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPXX1001D > %BATCHROOTPATH%/tmp/RPXX1001D
    call :errexit %ERRORLEVEL% RPXX1001D

    echo ** RPXX1001M月计表...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPXX1001M > %BATCHROOTPATH%/tmp/RPXX1001M
    call :errexit %ERRORLEVEL% RPXX1001M

    echo ** RPACTSTM分户账...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTSTM > %BATCHROOTPATH%/tmp/RPACTSTM
    call :errexit %ERRORLEVEL% RPACTSTM

    echo ** RPVD0003对账单...... | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPVD0003 > %BATCHROOTPATH%/tmp/RPVD0003
    call :errexit %ERRORLEVEL% RPVD0003

    echo ** ActchkReceipt对账单回执...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat ActchkReceipt > %BATCHROOTPATH%/tmp/ActchkReceipt
    call :errexit %ERRORLEVEL% ActchkReceipt

    echo ** RPAX1001损益表...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPAX1001 > %BATCHROOTPATH%/tmp/RPAX1001
    call :errexit %ERRORLEVEL% RPAX1001

    echo ** RPACTGLF总账...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTGLF > %BATCHROOTPATH%/tmp/RPACTGLF
    call :errexit %ERRORLEVEL% RPACTGLF

    echo ********** 生成报表完成 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%

     goto end

REM ==============================
REM ===子程序：
REM ==============================
:errexit
    set return_code=%1
    set prg_name=%2

    if  "%return_code%" == "0"  goto :eof

    :errexit_error
        call beep 3
        echo ************************************************************
        echo ** 执行出错!!!  程序名:%prg_name% |tee -a %BATCHLOGFILE%
        choice /M  "** 忽略错误并继续吗?"
        if ERRORLEVEL 2 goto errexit_error
        echo ** 忽略错误，继续处理......
        echo ************************************************************
        echo.
        echo.
goto :eof

REM *************************************
:end
ENDLOCAL