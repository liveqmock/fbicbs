package cbs.batch.common.service;

import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import cbs.common.utils.PropertyManager;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-16
 * Time: 20:58:24
 * To change this template use File | Settings | File Templates.
 */
/*
 * updated by zhangxiaobo
 * Date: 2011-03-10
 * 新增变量 sdfdate10
  * 新增方法 getBizDate10
 */
public class BatchSystemService {

    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfdateYM = new SimpleDateFormat("yyyy年MM月");
    private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    private SimpleDateFormat sdftime8 = new SimpleDateFormat("HH:mm:ss");
    @Inject
    private ActsctMapper actsctMapper;

    public String getSysidtAC() {
        return "8";
    }

    public String getBatchResultFilePath() {
        return PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
    }

    /**
     * 获取当前工作日期   8位
     * @return
     */
    public String getBizDate() {
        //TODO
        String result = "";
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            result = sdfdate.format(sct.getCrndat());
        } catch (Exception e) {
            throw new RuntimeException("获取系统业务时间错误！", e);
        }
        return result;
    }

    /**
     * 获取当前工作日期   8位
     * @return
     */
    public Date getCrnDat() {
        Date crndat = null;
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            crndat = sct.getCrndat();
        } catch (Exception e) {
            throw new RuntimeException("获取系统业务时间错误！", e);
        }
        return crndat;
    }

    /**
     * 获取当前工作日期 10位
     * @return
     */
    public String getBizDate10() {
        String result = "";
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            result = sdfdate10.format(sct.getCrndat());
        } catch (Exception e) {
            throw new RuntimeException("获取系统业务时间错误！", e);
        }
        return result;
    }
    /**
     * 获取上一工作日期   8位
     * @return
     */
    public String getLastBizDate() {
        //TODO
        String result = "";
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            result = sdfdate.format(sct.getLwkday());
        } catch (Exception e) {
            throw new RuntimeException("获取系统上一业务时间错误！", e);
        }
        return result;
    }

    /**
     * 获取上一工作日期 10位
     * @return
     */
    public String getLastBizDate10() {
        String result = "";
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            result = sdfdate10.format(sct.getLwkday());
        } catch (Exception e) {
            throw new RuntimeException("获取系统上一业务时间错误！", e);
        }
        return result;
    }

    /**
     * 获取当前工作日期   yyyy年MM月
     * @return
     */

    public String getBizDateYM() {
        String result = "";
        try {
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = actsctMapper.selectByExample(example).get(0);
            result = sdfdateYM.format(sct.getCrndat());
        } catch (Exception e) {
            throw new RuntimeException("获取系统业务时间错误！", e);
        }
        return result;
    }

    /**
     * 获取当前工作 年份 2位
     * @return
     */


    public short getBizYear2() {
        String bizdate = getBizDate();
        return Short.parseShort(bizdate.substring(2, 4));
    }


    //===

    public ActsctMapper getActsctMapper() {
        return actsctMapper;
    }

    public void setActsctMapper(ActsctMapper actsctMapper) {
        this.actsctMapper = actsctMapper;
    }
}
