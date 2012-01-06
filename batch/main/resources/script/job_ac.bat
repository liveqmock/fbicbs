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
    call job_invoker.bat acbint > %BATCHROOTPATH%/tmp/acbint
    call :errexit %ERRORLEVEL% acbint

    if exist %BATCHROOTPATH%\db\intflag  (
       echo ** BCB8543��Ϣ�ն��μ�Ϣ...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8543 Y > %BATCHROOTPATH%/tmp/BCB8543
       call :errexit %ERRORLEVEL% BCB8543
    )


::����˶�
    call :check

:: ��������...
    echo ********** bcb8531�޸ĵ�ǰ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8531 Y> %BATCHROOTPATH%/tmp/bcb8531
    call :errexit %ERRORLEVEL% bcb8531

    echo ********** bcb8532������ƽ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8532> %BATCHROOTPATH%/tmp/bcb8532
    call :errexit %ERRORLEVEL% bcb8532

    echo ********** bcb8533���ʺ�ƽ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8533> %BATCHROOTPATH%/tmp/bcb8533
    call :errexit %ERRORLEVEL% bcb8533

    echo ********** bcb8534����������� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8534> %BATCHROOTPATH%/tmp/bcb8534
    call :errexit %ERRORLEVEL% bcb8534

    echo ********** acbckcgl���ʶ�Ӧ��Ŀ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat acbckcgl> %BATCHROOTPATH%/tmp/acbckcgl
    call :errexit %ERRORLEVEL% acbckcgl

    echo ********** bcb8535�ܷ�ƽ�� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8535> %BATCHROOTPATH%/tmp/bcb8535
    call :errexit %ERRORLEVEL% bcb8535

    echo ********** bcb8564�������� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8564> %BATCHROOTPATH%/tmp/bcb8564
    call :errexit %ERRORLEVEL% bcb8564

:: �˻���ҳ����...
    echo ********** bcb8529��ʧ���ủ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8529> %BATCHROOTPATH%/tmp/bcb8529
    call :errexit %ERRORLEVEL% bcb8529

    echo ********** bcb85211�˶� ACT �� OBF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85211> %BATCHROOTPATH%/tmp/bcb85211
    call :errexit %ERRORLEVEL% bcb85211

    echo ********** bcb85511�����ն��ʵ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85511> %BATCHROOTPATH%/tmp/bcb85511
    call :errexit %ERRORLEVEL% bcb85511

    echo ********** bcb85512������LSM ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb85512> %BATCHROOTPATH%/tmp/bcb85512
::    call :errexit %ERRORLEVEL% bcb85512

    echo ********** bcb85513�����շֻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85513> %BATCHROOTPATH%/tmp/bcb85513
    call :errexit %ERRORLEVEL% bcb85513

    echo ********** bcb85517���ʵ��ֻ��������ACT�˶� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85517> %BATCHROOTPATH%/tmp/bcb85517
    call :errexit %ERRORLEVEL% bcb85517

    echo ********** bcb85516����ػ����ʵ��ֻ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85516> %BATCHROOTPATH%/tmp/bcb85516
    call :errexit %ERRORLEVEL% bcb85516

    echo ********** bcb85518sbl����������ҳ����sbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85518sbl> %BATCHROOTPATH%/tmp/bcb85518sbl
    call :errexit %ERRORLEVEL% bcb85518sbl

    echo ********** bcb85518lbl����������ҳ����lbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85518lbl> %BATCHROOTPATH%/tmp/bcb85518lbl
    call :errexit %ERRORLEVEL% bcb85518lbl

::����׼��
    echo ********** bcb8554����һ����Ŀ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8554> %BATCHROOTPATH%/tmp/bcb8554
    call :errexit %ERRORLEVEL% bcb8554

    echo ********** bcb8571�ʲ���ծ���ݲɼ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8571> %BATCHROOTPATH%/tmp/bcb8571
::    call :errexit %ERRORLEVEL% bcb8571

    echo ********** bcb85541�����ʲ���ծ���� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb85541> %BATCHROOTPATH%/tmp/bcb85541
::    call :errexit %ERRORLEVEL% bcb85541

    echo ********** bcb8555���������� PLF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8555> %BATCHROOTPATH%/tmp/bcb8555
::    call :errexit %ERRORLEVEL% bcb8555

    echo ********** bcb8556�����ʲ���ծ��� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8556> %BATCHROOTPATH%/tmp/bcb8556
::    call :errexit %ERRORLEVEL% bcb8556

    echo ********** bcb85210�����ػ� ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85210> %BATCHROOTPATH%/tmp/bcb85210
    call :errexit %ERRORLEVEL% bcb85210

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
