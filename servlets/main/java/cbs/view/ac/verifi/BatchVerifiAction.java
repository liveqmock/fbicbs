package cbs.view.ac.verifi;

import cbs.common.IbatisManager;
import cbs.common.utils.MessageUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/10/27
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BatchVerifiAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BatchVerifiAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private String filepath;
    //private ActvchFI fi = new ActvchFI();
    private JinDieBean bean = new JinDieBean();
    private List<JinDieBean> errorList = new ArrayList<JinDieBean>();

    public String onBtnImpClick() {
        if(filepath == null){
            MessageUtil.addError("请选择文件.");
            return null;
        }
        try {
            String fileName = filepath.split("/")[filepath.split("/").length - 1];
            //String fileName = file.getFileName();
            String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("xls".equals(extension)) {
                //read2003Excel(file);
            } else if ("xlsx".equals(extension)) {
                read2007Excel(filepath);
            } else {
                throw new IOException("不支持的文件类型");
            }
        } catch (Exception ex) {
            MessageUtil.addError("导入失败.");
        }
        return null;
    }
    /**
     * 读取 office 2003 excel
     *
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public void read2003Excel(UploadedFile file) throws IOException {
        try {
            InputStream input = file.getInputstream();
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowLen = sheet.getLastRowNum();
            HSSFCell cell;
            String tmp = "";
            for (int i = 1; i <= rowLen; i++) {
                int cellNum = sheet.getRow(i).getLastCellNum();
                for (int j = 0; j < cellNum; j++) {
                    cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        if (cell.getCellType() == 1) {
                            tmp = cell.getStringCellValue().trim();
                            if ("".equals(tmp)){
                                continue;
                            }
                        } else if (cell.getCellType() == 0) {
                            tmp = NumberFormat.getNumberInstance().format(cell.getNumericCellValue()).replaceAll(",", "");
                            if ("0".equals(tmp)){
                                continue;
                            }
                        }else if (cell.getCellType()==3){
                            continue;
                        }
                    } else continue;
                    switch (j) {
                        case 0:
                            bean.setJdnum(tmp);
                            break;
                        case 1:
                            bean.setJddmt(tmp);
                            break;
                        default:
                            break;
                    }
                }
                //onCompare();
            }
        } catch (Exception ex) {
            MessageUtil.addError("录入内容异常.");
        }
        MessageUtil.addInfo("录入结束.");
    }

    /**
     * 读取Office 2007 excel
     */
    public String read2007Excel(String filepath) throws IOException {
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            int rowLen = sheet.getLastRowNum();
            XSSFCell cell;
            String tmp = "";
            for (int i = 1; i <= rowLen; i++) {
                int cellNum = sheet.getRow(i).getLastCellNum();
                for (int j = 0; j < cellNum; j++) {
                    cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        if (cell.getCellType() == 1) {
                            tmp = cell.getStringCellValue().trim();
                            if ("".equals(tmp)){
                                continue;
                            }
                        } else if (cell.getCellType() == 0) {
                            tmp = NumberFormat.getNumberInstance().format(cell.getNumericCellValue()).replaceAll(",", "");
                            if ("0".equals(tmp)){
                                continue;
                            }
                        }else if (cell.getCellType()==3){
                            continue;
                        }
                    } else continue;
                    switch (j) {
                        case 0:
                            bean.setJdnum(tmp);
                            break;
                        case 1:
                            bean.setJddmt(tmp);
                            break;
                        default:
                            break;
                    }
                }
                //onCompare();
            }
        } catch (Exception ex) {
            MessageUtil.addError("录入内容异常.");
        }
        MessageUtil.addInfo("录入结束.");
        return null;
    }

    public String onCompare(){
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // = = = = = = = = = = =  get set  = = = = = = = = =  = = = =

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public JinDieBean getBean() {
        return bean;
    }

    public void setBean(JinDieBean bean) {
        this.bean = bean;
    }

    public List<JinDieBean> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<JinDieBean> errorList) {
        this.errorList = errorList;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
