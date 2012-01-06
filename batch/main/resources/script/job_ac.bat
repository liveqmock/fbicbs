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
    call job_invoker.bat acbint > %BATCHROOTPATH%/tmp/acbint
    call :errexit %ERRORLEVEL% acbint

    if exist %BATCHROOTPATH%\db\intflag  (
       echo ** BCB8543计息日二次计息...... | tee -a  %BATCHLOGFILE%
       echo %date% %time%  | tee -a  %BATCHLOGFILE%
       call job_invoker.bat BCB8543 Y > %BATCHROOTPATH%/tmp/BCB8543
       call :errexit %ERRORLEVEL% BCB8543
    )


::账务核对
    call :check

:: 生成总账...
    echo ********** bcb8531修改当前总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8531 Y> %BATCHROOTPATH%/tmp/bcb8531
    call :errexit %ERRORLEVEL% bcb8531

    echo ********** bcb8532总帐竖平检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8532> %BATCHROOTPATH%/tmp/bcb8532
    call :errexit %ERRORLEVEL% bcb8532

    echo ********** bcb8533总帐横平检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8533> %BATCHROOTPATH%/tmp/bcb8533
    call :errexit %ERRORLEVEL% bcb8533

    echo ********** bcb8534总帐特征检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8534> %BATCHROOTPATH%/tmp/bcb8534
    call :errexit %ERRORLEVEL% bcb8534

    echo ********** acbckcgl总帐对应科目检查 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat acbckcgl> %BATCHROOTPATH%/tmp/acbckcgl
    call :errexit %ERRORLEVEL% acbckcgl

    echo ********** bcb8535总分平帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8535> %BATCHROOTPATH%/tmp/bcb8535
    call :errexit %ERRORLEVEL% bcb8535

    echo ********** bcb8564生成总账 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8564> %BATCHROOTPATH%/tmp/bcb8564
    call :errexit %ERRORLEVEL% bcb8564

:: 账户账页处理...
    echo ********** bcb8529挂失冻结户处理 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8529> %BATCHROOTPATH%/tmp/bcb8529
    call :errexit %ERRORLEVEL% bcb8529

    echo ********** bcb85211核对 ACT 与 OBF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85211> %BATCHROOTPATH%/tmp/bcb85211
    call :errexit %ERRORLEVEL% bcb85211

    echo ********** bcb85511处理当日对帐单 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85511> %BATCHROOTPATH%/tmp/bcb85511
    call :errexit %ERRORLEVEL% bcb85511

    echo ********** bcb85512处理处理LSM ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb85512> %BATCHROOTPATH%/tmp/bcb85512
::    call :errexit %ERRORLEVEL% bcb85512

    echo ********** bcb85513处理当日分户帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85513> %BATCHROOTPATH%/tmp/bcb85513
    call :errexit %ERRORLEVEL% bcb85513

    echo ********** bcb85517对帐单分户帐余额与ACT核对 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85517> %BATCHROOTPATH%/tmp/bcb85517
    call :errexit %ERRORLEVEL% bcb85517

    echo ********** bcb85516处理关户对帐单分户帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85516> %BATCHROOTPATH%/tmp/bcb85516
    call :errexit %ERRORLEVEL% bcb85516

    echo ********** bcb85518sbl特殊日期帐页处理sbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85518sbl> %BATCHROOTPATH%/tmp/bcb85518sbl
    call :errexit %ERRORLEVEL% bcb85518sbl

    echo ********** bcb85518lbl特殊日期帐页处理lbl ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85518lbl> %BATCHROOTPATH%/tmp/bcb85518lbl
    call :errexit %ERRORLEVEL% bcb85518lbl

::报表准备
    echo ********** bcb8554生成一级科目总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb8554> %BATCHROOTPATH%/tmp/bcb8554
    call :errexit %ERRORLEVEL% bcb8554

    echo ********** bcb8571资产负债数据采集 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8571> %BATCHROOTPATH%/tmp/bcb8571
::    call :errexit %ERRORLEVEL% bcb8571

    echo ********** bcb85541生成资产负债总帐 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb85541> %BATCHROOTPATH%/tmp/bcb85541
::    call :errexit %ERRORLEVEL% bcb85541

    echo ********** bcb8555生成损益帐 PLF ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8555> %BATCHROOTPATH%/tmp/bcb8555
::    call :errexit %ERRORLEVEL% bcb8555

    echo ********** bcb8556生成资产负债余额 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
::    call job_invoker.bat bcb8556> %BATCHROOTPATH%/tmp/bcb8556
::    call :errexit %ERRORLEVEL% bcb8556

    echo ********** bcb85210批量关户 ********** | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat bcb85210> %BATCHROOTPATH%/tmp/bcb85210
    call :errexit %ERRORLEVEL% bcb85210

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
