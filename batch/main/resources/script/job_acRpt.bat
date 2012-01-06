::##############################################################
::#  ������       job_acRpt.bat                                #
::#  ������:	 ����������ҵ                                  #
::#  ��д��:      haiyu                                        #
::##############################################################

@ECHO OFF

SETLOCAL

    call setenv
    echo.
    echo ********** ��ʼ���ɱ��� ********** | tee -a  %BATCHLOGFILE%

    echo ** RPACTVCH�սᵥ...... | tee -a %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTVCH > %BATCHROOTPATH%/tmp/RPACTVCH
    call :errexit %ERRORLEVEL% RPACTVCH

    echo ** RPTOBF����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPTOBF > %BATCHROOTPATH%/tmp/RPTOBF
    call :errexit %ERRORLEVEL% RPTOBF

    echo ** OldBalance����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat OldBalance > %BATCHROOTPATH%/tmp/OldBalance
    call :errexit %ERRORLEVEL% OldBalance

    echo ** RPXX1001D�ռƱ�...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPXX1001D > %BATCHROOTPATH%/tmp/RPXX1001D
    call :errexit %ERRORLEVEL% RPXX1001D

    echo ** RPXX1001M�¼Ʊ�...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPXX1001M > %BATCHROOTPATH%/tmp/RPXX1001M
    call :errexit %ERRORLEVEL% RPXX1001M

    echo ** RPACTSTM�ֻ���...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTSTM > %BATCHROOTPATH%/tmp/RPACTSTM
    call :errexit %ERRORLEVEL% RPACTSTM

    echo ** RPVD0003���˵�...... | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPVD0003 > %BATCHROOTPATH%/tmp/RPVD0003
    call :errexit %ERRORLEVEL% RPVD0003

    echo ** ActchkReceipt���˵���ִ...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat ActchkReceipt > %BATCHROOTPATH%/tmp/ActchkReceipt
    call :errexit %ERRORLEVEL% ActchkReceipt

    echo ** RPAX1001�����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPAX1001 > %BATCHROOTPATH%/tmp/RPAX1001
    call :errexit %ERRORLEVEL% RPAX1001

    echo ** RPACTGLF����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invokerRpt.bat RPACTGLF > %BATCHROOTPATH%/tmp/RPACTGLF
    call :errexit %ERRORLEVEL% RPACTGLF

    echo ********** ���ɱ������ ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%

     goto end

REM ==============================
REM ===�ӳ���
REM ==============================
:errexit
    set return_code=%1
    set prg_name=%2

    if  "%return_code%" == "0"  goto :eof

    :errexit_error
        call beep 3
        echo ************************************************************
        echo ** ִ�г���!!!  ������:%prg_name% |tee -a %BATCHLOGFILE%
        choice /M  "** ���Դ��󲢼�����?"
        if ERRORLEVEL 2 goto errexit_error
        echo ** ���Դ��󣬼�������......
        echo ************************************************************
        echo.
        echo.
goto :eof

REM *************************************
:end
ENDLOCAL