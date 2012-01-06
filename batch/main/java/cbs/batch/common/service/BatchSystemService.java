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
 * �������� sdfdate10
  * �������� getBizDate10
 */
public class BatchSystemService {

    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfdateYM = new SimpleDateFormat("yyyy��MM��");
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
     * ��ȡ��ǰ��������   8λ
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
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����", e);
        }
        return result;
    }

    /**
     * ��ȡ��ǰ��������   8λ
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
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����", e);
        }
        return crndat;
    }

    /**
     * ��ȡ��ǰ�������� 10λ
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
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����", e);
        }
        return result;
    }
    /**
     * ��ȡ��һ��������   8λ
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
            throw new RuntimeException("��ȡϵͳ��һҵ��ʱ�����", e);
        }
        return result;
    }

    /**
     * ��ȡ��һ�������� 10λ
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
            throw new RuntimeException("��ȡϵͳ��һҵ��ʱ�����", e);
        }
        return result;
    }

    /**
     * ��ȡ��ǰ��������   yyyy��MM��
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
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����", e);
        }
        return result;
    }

    /**
     * ��ȡ��ǰ���� ��� 2λ
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
