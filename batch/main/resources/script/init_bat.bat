::##############################################################
::#  程序名       init_bat.bat                                  #
::#  程序功能:	 会计批量作业:  系统初始化                        #
::#  编写者:      zr                                            #
::##############################################################
@ECHO OFF

SETLOCAL

    call setenv

    echo.
    echo ********** 开始进行系统初始化处理 ******** | tee -a  %BATCHLOGFILE%

    echo ** BCB85519修改对帐单分户帐出帐方式（下日起用）... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85519 > %BATCHROOTPATH%/tmp/BCB85519
    call :errexit %ERRORLEVEL% BCB85519

    echo ** BCB8565写ACTBAH历史数据...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8565 > %BATCHROOTPATH%/tmp/BCB8565
    call :errexit %ERRORLEVEL% BCB8565

    echo ** BCB8513清 ACT,FXE,PLE 月 发生额，发生数...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8513 Y > %BATCHROOTPATH%/tmp/BCB8513
    call :errexit %ERRORLEVEL% BCB8513

    echo ** BCB8514清总帐发生额，发生数...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8514 > %BATCHROOTPATH%/tmp/BCB8514
    call :errexit %ERRORLEVEL% BCB8514

    echo ** BCB8574清核算码总帐发生额，发生数...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8574 > %BATCHROOTPATH%/tmp/BCB8574
    call :errexit %ERRORLEVEL% BCB8574

    echo ** BCB8558计算平均余额（第二次)...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8558 Y> %BATCHROOTPATH%/tmp/BCB8558
    call :errexit %ERRORLEVEL% BCB8558

    echo ** BCB8563写历史记录...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8563 > %BATCHROOTPATH%/tmp/BCB8563
    call :errexit %ERRORLEVEL% BCB8563

    echo ** BCB8561系统初始化...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8561 > %BATCHROOTPATH%/tmp/BCB8561
    call :errexit %ERRORLEVEL% BCB8561

    echo ** Delvch删除ACFTVC, ACFVCH , ACFCTV...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Delvch > %BATCHROOTPATH%/tmp/Delvch
    call :errexit %ERRORLEVEL% Delvch

    if exist %BATCHROOTPATH%\db\intflag  (
        echo ** Acbaccm清未结息户积数...... | tee -a  %BATCHLOGFILE%
        call job_invoker.bat Acbaccm > %BATCHROOTPATH%/tmp/Acbaccm
        call :errexit %ERRORLEVEL% Acbaccm
        
        echo ** BCB8513清 ACT,FXE,PLE 月 发生额，发生数...... | tee -a  %BATCHLOGFILE%
        call job_invoker.bat BCB8513 D > %BATCHROOTPATH%/tmp/BCB8513
        call :errexit %ERRORLEVEL% BCB8513

        call :vchpros
        call :vcherr "清未结息帐户积数"

        echo ** BCB8529挂失冻结户处理...... | tee -a  %BATCHLOGFILE%
        echo %date% %time%  | tee -a  %BATCHLOGFILE%
        call job_invoker.bat BCB8529 > %BATCHROOTPATH%/tmp/BCB8529
        call :errexit %ERRORLEVEL% BCB8529
    )

    REM TODO:冲回计提利息 清试算标识

    echo ** Act2obf生成OBF...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Act2obf > %BATCHROOTPATH%/tmp/Act2obf
    call :errexit %ERRORLEVEL% Act2obf

    REM TODO:调整帐户科目归属(核算码不变 科目变)
    REM TODO:调整帐户核算码(核算码变 科目不变)
    REM TODO:下传码表

    echo ** getirt利率生效处理...... | tee -a  %BATCHLOGFILE%
    echo %date% %time%  | tee -a  %BATCHLOGFILE%
    call job_invoker.bat Getirt > %BATCHROOTPATH%/tmp/getirt
    call :errexit %ERRORLEVEL% getirt

    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2% | tee -a  %BATCHLOGFILE%
    echo ********** 系统初始化处理全部完成 ******** | tee -a  %BATCHLOGFILE%
    pause

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
    if not exist %BATCHROOTPATH%\report\VCHERRFLG  goto :eof
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
    echo ********** 开始当日传票入帐处理 ********** | tee -a  %BATCHLOGFILE%

    echo ** 传票借贷平衡检查...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8521 > %BATCHROOTPATH%/tmp/BCB8521
    call :errexit %ERRORLEVEL% BCB8521

    echo ** 批量开户...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8522 > %BATCHROOTPATH%/tmp/BCB8522
    call :errexit %ERRORLEVEL% BCB8522

    echo ** 新开帐户修改SBL LBL...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB85510 > %BATCHROOTPATH%/tmp/BCB85510
    call :errexit %ERRORLEVEL% BCB85510

    echo ** 传票入帐...... | tee -a  %BATCHLOGFILE%
    call job_invoker.bat BCB8523 > %BATCHROOTPATH%/tmp/BCB8523
    call :errexit %ERRORLEVEL% BCB8523

    echo ********** 当日传票入帐处理完成 ********** | tee -a  %BATCHLOGFILE%

goto :eof


REM *************************************
:end
ENDLOCAL
