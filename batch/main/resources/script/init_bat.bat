::##############################################################
::#  ������       init_bat.bat                                  #
::#  ������:	 ���������ҵ:  ϵͳ��ʼ��                        #
::#  ��д��:      zr                                            #
::##############################################################
@ECHO OFF

SETLOCAL

    call setenv

    echo.
    echo ********** ��ʼ����ϵͳ��ʼ������ ******** | tee -a  %BATCHLOGFILE%

    echo ** BCB85519�޸Ķ��ʵ��ֻ��ʳ��ʷ�ʽ���������ã�... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85519 > %BATCHROOTPATH%/tmp/BCB85519
    call :errexit %ERRORLEVEL% BCB85519

    echo ** BCB8565дACTBAH��ʷ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8565 > %BATCHROOTPATH%/tmp/BCB8565
    call :errexit %ERRORLEVEL% BCB8565

    echo ** BCB8513�� ACT,FXE,PLE �� �����������...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8513 Y > %BATCHROOTPATH%/tmp/BCB8513
    call :errexit %ERRORLEVEL% BCB8513

    echo ** BCB8514�����ʷ����������...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8514 > %BATCHROOTPATH%/tmp/BCB8514
    call :errexit %ERRORLEVEL% BCB8514

    echo ** BCB8574����������ʷ����������...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8574 > %BATCHROOTPATH%/tmp/BCB8574
    call :errexit %ERRORLEVEL% BCB8574

    echo ** BCB8558����ƽ�����ڶ���)...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8558 Y> %BATCHROOTPATH%/tmp/BCB8558
    call :errexit %ERRORLEVEL% BCB8558

    echo ** BCB8563д��ʷ��¼...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8563 > %BATCHROOTPATH%/tmp/BCB8563
    call :errexit %ERRORLEVEL% BCB8563

    echo ** BCB8561ϵͳ��ʼ��...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8561 > %BATCHROOTPATH%/tmp/BCB8561
    call :errexit %ERRORLEVEL% BCB8561

    echo ** Delvchɾ��ACFTVC, ACFVCH , ACFCTV...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Delvch > %BATCHROOTPATH%/tmp/Delvch
    call :errexit %ERRORLEVEL% Delvch

    if exist %BATCHROOTPATH%\db\intflag  (
        echo ** Acbaccm��δ��Ϣ������...... | tee -a  %BATCHLOGFILE%
        call job_invoker.bat Acbaccm > %BATCHROOTPATH%/tmp/Acbaccm
        call :errexit %ERRORLEVEL% Acbaccm
        
        echo ** BCB8513�� ACT,FXE,PLE �� �����������...... | tee -a  %BATCHLOGFILE%
        call job_invoker.bat BCB8513 D > %BATCHROOTPATH%/tmp/BCB8513
        call :errexit %ERRORLEVEL% BCB8513

        call :vchpros
        call :vcherr "��δ��Ϣ�ʻ�����"

        echo ** BCB8529��ʧ���ủ����...... | tee -a  %BATCHLOGFILE%
        echo %date% %time%  | tee -a  %BATCHLOGFILE%
        call job_invoker.bat BCB8529 > %BATCHROOTPATH%/tmp/BCB8529
        call :errexit %ERRORLEVEL% BCB8529
    )

    REM TODO:��ؼ�����Ϣ �������ʶ

    echo ** Act2obf����OBF...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Act2obf > %BATCHROOTPATH%/tmp/Act2obf
    call :errexit %ERRORLEVEL% Act2obf

    REM TODO:�����ʻ���Ŀ����(�����벻�� ��Ŀ��)
    REM TODO:�����ʻ�������(������� ��Ŀ����)
    REM TODO:�´����

    echo ** getirt������Ч����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Getirt > %BATCHROOTPATH%/tmp/getirt
    call :errexit %ERRORLEVEL% getirt

    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
    echo ********** ϵͳ��ʼ������ȫ����� ******** | tee -a  %BATCHLOGFILE%
    pause

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

REM ==============================
REM ===�ӳ���
REM ==============================
:vcherr
    set intr_name=%1
    if not exist %BATCHROOTPATH%\report\VCHERRFLG  goto :eof
        echo.
        echo ************************************************************
        echo ** %intr_name% ��Ʊ��ƽ,�����ü�¼,��֪ͨ����ά����Ա...... |tee -a %BATCHLOGFILE%
        echo ************************************************************
        echo.
        pause
goto :eof

REM ==============================
REM ===�ӳ���
REM ==============================
:vchpros
    REM echo "��Ʊ��ƽ��־���......"  ???
    echo ********** ��ʼ���մ�Ʊ���ʴ��� ********** | tee -a  %BATCHLOGFILE%

    echo ** ��Ʊ���ƽ����...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8521 > %BATCHROOTPATH%/tmp/BCB8521
    call :errexit %ERRORLEVEL% BCB8521

    echo ** ��������...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8522 > %BATCHROOTPATH%/tmp/BCB8522
    call :errexit %ERRORLEVEL% BCB8522

    echo ** �¿��ʻ��޸�SBL LBL...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85510 > %BATCHROOTPATH%/tmp/BCB85510
    call :errexit %ERRORLEVEL% BCB85510

    echo ** ��Ʊ����...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8523 > %BATCHROOTPATH%/tmp/BCB8523
    call :errexit %ERRORLEVEL% BCB8523

    echo ********** ���մ�Ʊ���ʴ������ ********** | tee -a  %BATCHLOGFILE%

goto :eof


REM *************************************
:end
ENDLOCAL
