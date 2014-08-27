::##############################################################
::#  程序名       JOB_AC.BAT                                   #
::#  程序功能:	  会计批量作业:  对公日处理                    #
::#  编写者:      zr                                           #
::##############################################################

@ECHO OFF

SETLOCAL

    call setenv

    echo.
    rem cat > %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\log\*.*
    echo ********** 对公日处理开始 ********** >> %BATCHLOGFILE%
    echo ********** 清理旧报表 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\report\*.* >> %BATCHLOGFILE%
    echo y | del %BATCHROOTPATH%\tmp\*.*  >> %BATCHLOGFILE%

    if exist %BATCHROOTPATH%\db\intflag  (
       del %BATCHROOTPATH%\db\intflag
    ) else (
       echo ** BCB8513清 ACT,FXE,PLE 日 发生额，发生数...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8513 D > %BATCHROOTPATH%/tmp/BCB8513
       call :errexit %ERRORLEVEL% BCB8513
    )

    echo ** BCB8512生成当前利率...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8512 > %BATCHROOTPATH%/tmp/BCB8512
    call :errexit %ERRORLEVEL% BCB8512

    echo ** 当日联机传票入账...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call :vchpros
    call :vcherr

    echo ********** BCB8541确定不计息余额 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8541 > %BATCHROOTPATH%/tmp/BCB8541
    call :errexit %ERRORLEVEL% BCB8541

    echo ********** BCB8542同业账户每日累加积数 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8542 > %BATCHROOTPATH%/tmp/BCB8542
    call :errexit %ERRORLEVEL% BCB8542

    echo ********** BCB8543存款账户每日累加积数 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8543 N > %BATCHROOTPATH%/tmp/BCB8543
    call :errexit %ERRORLEVEL% BCB8543

    echo ********** ATB8917计提应付利息 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat ATB8917 > %BATCHROOTPATH%/tmp/ATB8917
::    call :errexit %ERRORLEVEL% ATB8917
    echo ** 计提传票入账...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call :vchpros
::    call :vcherr

    echo ********** ACBINT存款账户计息 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Acbint > %BATCHROOTPATH%/tmp/Acbint
    call :errexit %ERRORLEVEL% Acbint

    if exist %BATCHROOTPATH%\db\intflag  (
       echo ** BCB8543计息日二次计息...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8543 Y > %BATCHROOTPATH%/tmp/BCB8543
       call :errexit %ERRORLEVEL% BCB8543
    )


::账务核对
    call :check

:: 生成总账...
    echo ********** BCB8531修改当前总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8531 Y> %BATCHROOTPATH%/tmp/BCB8531
    call :errexit %ERRORLEVEL% BCB8531

    echo ********** BCB8532总帐竖平检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8532> %BATCHROOTPATH%/tmp/BCB8532
    call :errexit %ERRORLEVEL% BCB8532

    echo ********** BCB8533总帐横平检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8533> %BATCHROOTPATH%/tmp/BCB8533
    call :errexit %ERRORLEVEL% BCB8533

    echo ********** BCB8534总帐特征检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8534> %BATCHROOTPATH%/tmp/BCB8534
    call :errexit %ERRORLEVEL% BCB8534

    echo ********** acbckcgl总帐对应科目检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Acbckcgl> %BATCHROOTPATH%/tmp/Acbckcgl
    call :errexit %ERRORLEVEL% Acbckcgl

    echo ********** BCB8535总分平帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8535> %BATCHROOTPATH%/tmp/BCB8535
    call :errexit %ERRORLEVEL% BCB8535

    echo ********** BCB8564生成总账 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8564> %BATCHROOTPATH%/tmp/BCB8564
    call :errexit %ERRORLEVEL% BCB8564

:: 账户账页处理...
    echo ********** BCB8529挂失冻结户处理 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8529> %BATCHROOTPATH%/tmp/BCB8529
    call :errexit %ERRORLEVEL% BCB8529

    echo ********** BCB85211核对 ACT 与 OBF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85211> %BATCHROOTPATH%/tmp/BCB85211
    call :errexit %ERRORLEVEL% BCB85211

    echo ********** BCB85511处理当日对帐单 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85511> %BATCHROOTPATH%/tmp/BCB85511
    call :errexit %ERRORLEVEL% BCB85511

    echo ********** BCB85512处理处理LSM ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB85512> %BATCHROOTPATH%/tmp/BCB85512
::    call :errexit %ERRORLEVEL% BCB85512

    echo ********** BCB85513处理当日分户帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85513> %BATCHROOTPATH%/tmp/BCB85513
    call :errexit %ERRORLEVEL% BCB85513

    echo ********** BCB85517对帐单分户帐余额与ACT核对 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85517> %BATCHROOTPATH%/tmp/BCB85517
    call :errexit %ERRORLEVEL% BCB85517

    echo ********** BCB85516处理关户对帐单分户帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85516> %BATCHROOTPATH%/tmp/BCB85516
    call :errexit %ERRORLEVEL% BCB85516

    echo ********** BCB85518sbl特殊日期帐页处理sbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85518SBL> %BATCHROOTPATH%/tmp/BCB85518SBL
    call :errexit %ERRORLEVEL% BCB85518SBL

    echo ********** BCB85518lbl特殊日期帐页处理lbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85518LBL> %BATCHROOTPATH%/tmp/BCB85518LBL
    call :errexit %ERRORLEVEL% BCB85518LBL

::报表准备
    echo ********** BCB8554生成一级科目总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8554> %BATCHROOTPATH%/tmp/BCB8554
    call :errexit %ERRORLEVEL% BCB8554

    echo ********** BCB8571资产负债数据采集 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8571> %BATCHROOTPATH%/tmp/BCB8571
::    call :errexit %ERRORLEVEL% BCB8571

    echo ********** BCB85541生成资产负债总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB85541> %BATCHROOTPATH%/tmp/BCB85541
::    call :errexit %ERRORLEVEL% BCB85541

    echo ********** BCB8555生成损益帐 PLF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8555> %BATCHROOTPATH%/tmp/BCB8555
::    call :errexit %ERRORLEVEL% BCB8555

    echo ********** BCB8556生成资产负债余额 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat BCB8556> %BATCHROOTPATH%/tmp/BCB8556
::    call :errexit %ERRORLEVEL% BCB8556

    echo ********** BCB85210批量关户 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85210> %BATCHROOTPATH%/tmp/BCB85210
    call :errexit %ERRORLEVEL% BCB85210

::生成报表
    call job_acRpt.bat


::系统初始化
    call init_bat.bat

    echo ********** 会计初始化完成! ********** | tee -a  %BATCHLOGFILE%
    echo.
    echo ********** 日处理结束! ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo.

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

REM ==============================
REM ===子程序：
REM ==============================
:vcherr
    set intr_name=%1
    if not exist %BATCHROOTPATH%\report\VCHERRFLG goto :eof
        echo.
        echo ************************************************************
        echo ** %intr_name% 传票不平,请做好记录,并通知批量维护人员...... |tee -a %BATCHLOGFILE%
        echo ************************************************************
        echo.
        pause
goto :eof

REM ==============================
REM ===子程序：
REM ==============================
:vchpros

    REM echo "传票不平标志检查......"  ???

    echo.
    echo ********** 开始当日传票入帐处理 ********** | tee -a  %BATCHLOGFILE%

    echo ** BCB8521传票借贷平衡检查...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8521 > %BATCHROOTPATH%/tmp/BCB8521
    call :errexit %ERRORLEVEL% BCB8521

    echo ** BCB8522批量开户...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8522 > %BATCHROOTPATH%/tmp/BCB8522
    call :errexit %ERRORLEVEL% BCB8522

    echo ** BCB85510新开帐户修改SBL LBL...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85510 > %BATCHROOTPATH%/tmp/BCB85510
    call :errexit %ERRORLEVEL% BCB85510

    echo ** BCB8523传票入帐...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8523 > %BATCHROOTPATH%/tmp/BCB8523
    call :errexit %ERRORLEVEL% BCB8523

    echo ********** 当日传票入帐处理完成 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%

goto :eof

REM ==============================
REM ===子程序：
REM ==============================
:check
    echo.
    echo ********** 开始进行明细核对处理 ********** | tee -a  %BATCHLOGFILE%

    echo ** BCB8524核对ACTACT...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8524 > %BATCHROOTPATH%/tmp/BCB8524l
    call :errexit %ERRORLEVEL% BCB8524

    rem echo ** BCB8525核对ACTACT与ACTFXE...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8525 > %BATCHROOTPATH%/tmp/BCB8525l
    rem call :errexit %ERRORLEVEL% BCB8525

    echo ** BCB8526核对ACTACT与ACTPLE...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8526 > %BATCHROOTPATH%/tmp/BCB8526l
    call :errexit %ERRORLEVEL% BCB8526

    rem echo ** BCB8527核对ACTACT与ACTOSF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8527 > %BATCHROOTPATH%/tmp/BCB8527l
    rem call :errexit %ERRORLEVEL% BCB8527

    rem echo ** BCB8528核对ACTACT与ACTOUF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat BCB8528 > %BATCHROOTPATH%/tmp/BCB8528l
    rem call :errexit %ERRORLEVEL% BCB8528

    rem echo ** ckcard核对ACTACT与ACTOSF...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat Tfbckcard > %BATCHROOTPATH%/tmp/ckcard
    rem call :errexit %ERRORLEVEL% Tfbckcard

    rem echo ** Tfbckact核对ACTOSF与ACTACT...... | tee -a  %BATCHLOGFILE%
    rem echo %date% %time%  | tee -a  %BATCHLOGFILE%
    rem call job_invoker.bat Tfbckact > %BATCHROOTPATH%/tmp/ckact
    rem call :errexit %ERRORLEVEL% Tfbckact

    echo ********** 明细核对处理全部完成 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    echo.
goto :eof

REM *************************************
:end
ENDLOCAL
