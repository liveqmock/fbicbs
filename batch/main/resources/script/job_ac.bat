::##############################################################
::#  ������       JOB_AC.BAT                                   #
::#  ������:	  ���������ҵ:  �Թ��մ���                    #
::#  ��д��:      zr                                           #
::##############################################################

@ECHO OFF

SETLOCAL

    call setenv

    echo.
    rem cat > %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\log\*.*
    echo ********** �Թ��մ���ʼ ********** >> %BATCHLOGFILE%
    echo ********** ����ɱ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\report\*.* >> %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\tmp\*.*  >> %BATCHLOGFILE%

    if exist %BATCHROOTPATH%\db\intflag  (
       del %BATCHROOTPATH%\db\intflag
    ) else (
       echo ** BCB8513�� ACT,FXE,PLE �� �����������...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8513 D > %BATCHROOTPATH%/tmp/BCB8513
       call :errexit %ERRORLEVEL% BCB8513
    )

    echo ** BCB8512���ɵ�ǰ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8512 > %BATCHROOTPATH%/tmp/BCB8512
    call :errexit %ERRORLEVEL% BCB8512

    echo ** ����������Ʊ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call :vchpros
    call :vcherr

    echo ********** BCB8541ȷ������Ϣ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8541 > %BATCHROOTPATH%/tmp/BCB8541
    call :errexit %ERRORLEVEL% BCB8541

    echo ********** BCB8542ͬҵ�˻�ÿ���ۼӻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8542 > %BATCHROOTPATH%/tmp/BCB8542
    call :errexit %ERRORLEVEL% BCB8542

    echo ********** BCB8543����˻�ÿ���ۼӻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8543 N > %BATCHROOTPATH%/tmp/BCB8543
    call :errexit %ERRORLEVEL% BCB8543

    echo ********** ATB8917����Ӧ����Ϣ ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat ATB8917 > %BATCHROOTPATH%/tmp/ATB8917
::    call :errexit %ERRORLEVEL% ATB8917
    echo ** ���ᴫƱ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call :vchpros
::    call :vcherr

    echo ********** ACBINT����˻���Ϣ ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Acbint > %BATCHROOTPATH%/tmp/Acbint
    call :errexit %ERRORLEVEL% Acbint

    if exist %BATCHROOTPATH%\db\intflag  (
       echo ** BCB8543��Ϣ�ն��μ�Ϣ...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8543 Y > %BATCHROOTPATH%/tmp/BCB8543
       call :errexit %ERRORLEVEL% BCB8543
    )


::����˶�
    call :check

:: ��������...
    echo ********** BCB8531�޸ĵ�ǰ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8531 Y> %BATCHROOTPATH%/tmp/BCB8531
    call :errexit %ERRORLEVEL% BCB8531

    echo ********** BCB8532������ƽ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8532> %BATCHROOTPATH%/tmp/BCB8532
    call :errexit %ERRORLEVEL% BCB8532

    echo ********** BCB8533���ʺ�ƽ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8533> %BATCHROOTPATH%/tmp/BCB8533
    call :errexit %ERRORLEVEL% BCB8533

    echo ********** BCB8534����������� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8534> %BATCHROOTPATH%/tmp/BCB8534
    call :errexit %ERRORLEVEL% BCB8534

    echo ********** acbckcgl���ʶ�Ӧ��Ŀ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Acbckcgl> %BATCHROOTPATH%/tmp/Acbckcgl
    call :errexit %ERRORLEVEL% Acbckcgl

    echo ********** BCB8535�ܷ�ƽ�� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8535> %BATCHROOTPATH%/tmp/BCB8535
    call :errexit %ERRORLEVEL% BCB8535

    echo ********** BCB8564�������� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8564> %BATCHROOTPATH%/tmp/BCB8564
    call :errexit %ERRORLEVEL% BCB8564

:: �˻���ҳ����...
    echo ********** BCB8529��ʧ���ủ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8529> %BATCHROOTPATH%/tmp/BCB8529
    call :errexit %ERRORLEVEL% BCB8529

    echo ********** BCB85211�˶� ACT �� OBF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85211> %BATCHROOTPATH%/tmp/BCB85211
    call :errexit %ERRORLEVEL% BCB85211

    echo ********** BCB85511�����ն��ʵ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85511> %BATCHROOTPATH%/tmp/BCB85511
    call :errexit %ERRORLEVEL% BCB85511

    echo ********** BCB85512������LSM ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB85512> %BATCHROOTPATH%/tmp/BCB85512
::    call :errexit %ERRORLEVEL% BCB85512

    echo ********** BCB85513�����շֻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85513> %BATCHROOTPATH%/tmp/BCB85513
    call :errexit %ERRORLEVEL% BCB85513

    echo ********** BCB85517���ʵ��ֻ��������ACT�˶� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85517> %BATCHROOTPATH%/tmp/BCB85517
    call :errexit %ERRORLEVEL% BCB85517

    echo ********** BCB85516����ػ����ʵ��ֻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85516> %BATCHROOTPATH%/tmp/BCB85516
    call :errexit %ERRORLEVEL% BCB85516

    echo ********** BCB85518sbl����������ҳ����sbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85518SBL> %BATCHROOTPATH%/tmp/BCB85518SBL
    call :errexit %ERRORLEVEL% BCB85518SBL

    echo ********** BCB85518lbl����������ҳ����lbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85518LBL> %BATCHROOTPATH%/tmp/BCB85518LBL
    call :errexit %ERRORLEVEL% BCB85518LBL

::����׼��
    echo ********** BCB8554����һ����Ŀ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8554> %BATCHROOTPATH%/tmp/BCB8554
    call :errexit %ERRORLEVEL% BCB8554

    echo ********** BCB8571�ʲ���ծ���ݲɼ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8571> %BATCHROOTPATH%/tmp/BCB8571
::    call :errexit %ERRORLEVEL% BCB8571

    echo ********** BCB85541�����ʲ���ծ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB85541> %BATCHROOTPATH%/tmp/BCB85541
::    call :errexit %ERRORLEVEL% BCB85541

    echo ********** BCB8555���������� PLF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8555> %BATCHROOTPATH%/tmp/BCB8555
::    call :errexit %ERRORLEVEL% BCB8555

    echo ********** BCB8556�����ʲ���ծ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8556> %BATCHROOTPATH%/tmp/BCB8556
::    call :errexit %ERRORLEVEL% BCB8556

    echo ********** BCB85210�����ػ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85210> %BATCHROOTPATH%/tmp/BCB85210
    call :errexit %ERRORLEVEL% BCB85210

::���ɱ���
    call job_acRpt.bat


::ϵͳ��ʼ��
    call init_bat.bat

    echo ********** ��Ƴ�ʼ�����! ********** | tee -a  %BATCHLOGFILE%
    echo.
    echo ********** �մ������! ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo.

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
    if not exist %BATCHROOTPATH%\report\VCHERRFLG goto :eof
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

    echo.
    echo ********** ��ʼ���մ�Ʊ���ʴ��� ********** | tee -a  %BATCHLOGFILE%

    echo ** BCB8521��Ʊ���ƽ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8521 > %BATCHROOTPATH%/tmp/BCB8521
    call :errexit %ERRORLEVEL% BCB8521

    echo ** BCB8522��������...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8522 > %BATCHROOTPATH%/tmp/BCB8522
    call :errexit %ERRORLEVEL% BCB8522

    echo ** BCB85510�¿��ʻ��޸�SBL LBL...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85510 > %BATCHROOTPATH%/tmp/BCB85510
    call :errexit %ERRORLEVEL% BCB85510

    echo ** BCB8523��Ʊ����...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8523 > %BATCHROOTPATH%/tmp/BCB8523
    call :errexit %ERRORLEVEL% BCB8523

    echo ********** ���մ�Ʊ���ʴ������ ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%

goto :eof

REM ==============================
REM ===�ӳ���
REM ==============================
:check
    echo.
    echo ********** ��ʼ������ϸ�˶Դ��� ********** | tee -a  %BATCHLOGFILE%

    echo ** BCB8524�˶�ACTACT...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8524 > %BATCHROOTPATH%/tmp/BCB8524l
    call :errexit %ERRORLEVEL% BCB8524

    rem echo ** BCB8525�˶�ACTACT��ACTFXE...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8525 > %BATCHROOTPATH%/tmp/BCB8525l
    rem call :errexit %ERRORLEVEL% BCB8525

    echo ** BCB8526�˶�ACTACT��ACTPLE...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8526 > %BATCHROOTPATH%/tmp/BCB8526l
    call :errexit %ERRORLEVEL% BCB8526

    rem echo ** BCB8527�˶�ACTACT��ACTOSF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8527 > %BATCHROOTPATH%/tmp/BCB8527l
    rem call :errexit %ERRORLEVEL% BCB8527

    rem echo ** BCB8528�˶�ACTACT��ACTOUF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8528 > %BATCHROOTPATH%/tmp/BCB8528l
    rem call :errexit %ERRORLEVEL% BCB8528

    rem echo ** ckcard�˶�ACTACT��ACTOSF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat Tfbckcard > %BATCHROOTPATH%/tmp/ckcard
    rem call :errexit %ERRORLEVEL% Tfbckcard

    rem echo ** Tfbckact�˶�ACTOSF��ACTACT...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat Tfbckact > %BATCHROOTPATH%/tmp/ckact
    rem call :errexit %ERRORLEVEL% Tfbckact

    echo ********** ��ϸ�˶Դ���ȫ����� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo.
goto :eof

REM *************************************
:end
ENDLOCAL
