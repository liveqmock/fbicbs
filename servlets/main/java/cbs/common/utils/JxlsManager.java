package cbs.common.utils;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * EXCEL输出.
 */
public class JxlsManager {
    private static final Logger logger = LoggerFactory.getLogger(JxlsManager.class);

    public String exportDataToXls(String filename, String templateFileName, Map beansMap) {
        try {
            String reportPath = PropertyManager.getProperty("EXCEL_REPORT_ROOTPATH");
            String templateFileFullName = reportPath + templateFileName;
            outputExcel(beansMap, templateFileFullName, filename);


        } catch (Exception e) {
            logger.error("报表处理错误！", e);
            throw new RuntimeException("报表处理错误！", e);
        }
        return null;
    }

    private void outputExcel(Map beansMap, String templateFileName, String excelFilename) throws IOException {
        ServletOutputStream os = null;
        InputStream is = null;
        try {
            XLSTransformer transformer = new XLSTransformer();
            is = new BufferedInputStream(new FileInputStream(templateFileName));
            HSSFWorkbook wb = transformer.transformXLS(is, beansMap);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(excelFilename, "UTF-8"));
            response.setContentType("application/msexcel");
            wb.write(os);
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

}
