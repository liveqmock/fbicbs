package cbs.common;

import cbs.common.utils.PropertyManager;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.code.model.ActorgExample;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import cbs.repository.platform.dao.PtdeptMapper;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.dao.PtenumainMapper;
import cbs.repository.platform.model.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
  * ��������ʽ������
 */
//@ManagedBean(eager = true)
public class SystemService {
    private static final Logger logger = LoggerFactory.getLogger(SystemService.class);

    static private SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();
    static private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    static private SimpleDateFormat sdfdateYM = new SimpleDateFormat("yyyy��MM��");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    static private SimpleDateFormat sdftime8 = new SimpleDateFormat("HH:mm:ss");

    public static String getSysidtAC() {
        return "8";
    }

    public static String getBatchResultFilePath() {
        return PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
    }

    //�Ƿ�����ƽǰ���н��ƽ��ƽ���飨Ĭ��Ϊ ���飩
    public static boolean isChkAllVchIsDCBalanced() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenumainMapper mainMapper = session.getMapper(PtenumainMapper.class);
            PtenumainExample mainExample = new PtenumainExample();
            Ptenumain main = mainMapper.selectByPrimaryKey("CHKALLVCHISBAL");
            if (main == null) {
                return true;  //Ĭ��Ϊ ����
            }

            PtenudetailMapper detailMapper = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample detailExample = new PtenudetailExample();
            detailExample.createCriteria().andEnutypeEqualTo("CHKALLVCHISBAL");
            List<Ptenudetail> detailList = detailMapper.selectByExample(detailExample);
            if (!detailList.isEmpty()) {
                Ptenudetail detail = detailList.get(0);
                if ("1".equals(detail.getEnuitemvalue())) {
                    return false;
                }
            }
            return true;  //Ĭ��Ϊ ����
        } catch (Exception e) {
            logger.error("��ȡ��ƽǰ���ƽ�����־��CHKALLVCHISBAL������", e);
            throw new RuntimeException("��ȡ��ƽǰ���ƽ�����־��CHKALLVCHISBAL������", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //��ȡchkflag�����ϴ�Ʊ���͸֧����־��  0=�ϼ���׼����ƽʱ���͸֧ �� 1=�¼���׼, ÿ�����붼Ҫ����Ƿ�͸֧
    public static String getChkOverDraftFlag() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample enudetailexm = new PtenudetailExample();
            enudetailexm.clear();
            enudetailexm.createCriteria().andEnutypeEqualTo("CHKALLVCH");
            List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
            if (enudetailList.isEmpty()) {
                return "1"; //Ĭ��ֵ
            }
            Ptenudetail enudetail = enudetailList.get(0);
            return enudetail.getEnuitemvalue();
        } catch (Exception e) {
            logger.error("��ȡchkflag���ִ���", e);
            throw new RuntimeException("��ȡchkflag���ִ���", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

/*
    //��ȡchkflag�����ϴ�Ʊ���͸֧����־��   haiyu
    public static String getChkflag() {
        SqlSession session = sessionFactory.openSession();
        PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample enudetailexm = new PtenudetailExample();
        enudetailexm.clear();
        enudetailexm.createCriteria().andEnutypeEqualTo("SYSTEMPARAM").andEnuitemvalueEqualTo("CHKALLVCH");
        List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
        Ptenudetail enudetail = enudetailList.get(0);
        return enudetail.getEnuitemexpand();
    }
*/

    // ��ȡ������
    public static String getDepnamByDeptid(String deptId) {
        String deptNam = null;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtdeptMapper mapper = session.getMapper(PtdeptMapper.class);
            Ptdept dept = mapper.selectByPrimaryKey(deptId);
            if (dept != null) {
                deptNam = dept.getDeptname();
            }
            return deptNam;
        } catch (Exception e) {
            logger.error("��ȡ���������ִ���", e);
            throw new RuntimeException("��ȡ���������ִ���", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //20120731 zhanrui  ��ȡ�������������кţ� ��Ҫ�������������ָ�����汾  ��ʱ���ã�UI�������ֲ�ʹ��Mybatis
    @Deprecated
    public static String getOrgInterBankCode() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActorgMapper mapper = session.getMapper(ActorgMapper.class);
            ActorgExample example = new ActorgExample();
            example.createCriteria().andOrglvlEqualTo("1");
            List<Actorg> records = mapper.selectByExample(example);
            if (records.isEmpty()) {
                logger.error("��ȡ���������кų��ִ���");
                return "";
            }
            String ibkcde = records.get(0).getIbkcde();
            if (ibkcde == null || "".equals(ibkcde)) {
                return "";
            }
            return ibkcde;
        } catch (Exception e) {
            logger.error("��ȡ���������кų��ִ���", e);
            throw new RuntimeException("��ȡ���������кų��ִ���", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /*
   * ��ȡ��ҳ�������*/
    public static int getLineLimit() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample enudetailexm = new PtenudetailExample();
            enudetailexm.clear();
            enudetailexm.createCriteria().andEnutypeEqualTo("LINELIMIT");
            List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
            Ptenudetail enudetail = enudetailList.get(0);
            return Integer.parseInt(enudetail.getEnuitemvalue());
        } catch (NumberFormatException e) {
            logger.error("��ȡ��ҳ����������ִ���", e);
            throw new RuntimeException("��ȡ��ҳ����������ִ���", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //TODO ϵͳ��������
    public static String getPdfFont() {
        return "c:\\windows\\fonts\\simsun.ttc,1";
    }

    public static String getBizDate() {
        //TODO
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdate.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("��ȡϵͳҵ��ʱ�����", e);
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static String getBizDate10() {
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdate10.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("��ȡϵͳҵ��ʱ�����", e);
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static String getBizDateYM() {
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdateYM.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("��ȡϵͳҵ��ʱ�����", e);
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Create by wanglichao
     * 2014-11-19
     * �ڶ��˵���ӡ��ʹ��Ĭ��ϵͳ����ǰһ��
     */
    public static Date sysDate11() {
        Date result;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sct.getCrndat();
        } catch (Exception e) {
            logger.error("��ȡϵͳҵ��ʱ�����", e);
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
    public static Date getIpydat() {
        Date ipydat = null;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            ipydat = sct.getIpydat();
        } catch (Exception e) {
            logger.error("��ȡϵͳҵ��ʱ�����", e);
            throw new RuntimeException("��ȡϵͳҵ��ʱ�����");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ipydat;
    }

    /**
     * ����"9999.88" ->  "9,999.88"
     *
     * @param str
     * @return
     */
    public static String formatStrAmt(String str) {
        NumberFormat n = NumberFormat.getNumberInstance();
        double d = 0.00;
        String outStr = null;
        try {
            d = Double.parseDouble(str);
            outStr = n.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    public static short getBizYear2() {
        String bizdate = getBizDate();
        return Short.parseShort(bizdate.substring(2, 4));
    }

    public static String getBizTime6() {
        return sdftime6.format(new Date());
    }

    public static String getBizTime8() {
        return sdftime8.format(new Date());
    }

}
