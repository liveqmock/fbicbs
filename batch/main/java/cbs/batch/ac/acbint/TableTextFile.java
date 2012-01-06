package cbs.batch.ac.acbint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-6-2
 * Time: ����2:22
 * To change this template use File | Settings | File Templates.
 */
public class TableTextFile {
    private String title;                                 // ����
    private String[] linesAfterTitle;            // ����������ÿ���ַ�Ԫ��ռһ��
    private int[] elementWidths;              // ÿ���п��
    private String[] headElements;            // �б���
    private int[] alignFlags;                       //  �ж��������飬0--����룬1--�м���룬2--�Ҷ���
    private List<String[]> elementsList = new ArrayList<String[]>();                     // ������
    private String[] fileBottomLines;                // �ļ�������,ÿ���ַ�Ԫ��ռһ��
    private StringBuilder fileBuilder = new StringBuilder();
    private static String chgLineFlag = "\r\n";
    private String oneLine;

    // �����ļ������ַ���
    public String toString() {
        // �����б���
        if (title != null) {
            fileBuilder.append(title).append(chgLineFlag).append(chgLineFlag);
        }
        // ���Ӹ�����
        appendArrayToLines(linesAfterTitle);
        fileBuilder.append(chgLineFlag);
        //  �����б���
        appendArrayToLine(adjustSpacestrElements(headElements));
        fileBuilder.append(chgLineFlag);
        String aLine = oneLine;
        //makeLineSpace();
        // ����������
        int listCnt = 0;
        for (String[] strArray : elementsList) {
            appendArrayToLine(adjustSpacestrElements(strArray));
            fileBuilder.append(chgLineFlag);
            listCnt++;
            if (listCnt % 5 == 0) {
                if (aLine != null) {
                    fileBuilder.append(aLine).append(chgLineFlag);
                }
            }
        }
        fileBuilder.append(chgLineFlag);
        // �����ļ�������
        appendArrayToLines(fileBottomLines);
        return fileBuilder.toString();
    }

    // ��ӿո����У���ɶ���
    private String[] adjustSpacestrElements(String[] strElements) {

        if (strElements == null || strElements.length <= 0
                || strElements.length != alignFlags.length || strElements.length != elementWidths.length) {
            throw new RuntimeException("�������ݣ��ж��������飬�п������ĳ����в�һ�¡�");
        }
        String[] newElements = strElements;
        for (int i = 0; i < strElements.length; i++) {
            // 0--����룬1--�м���룬2--�Ҷ���
            switch (alignFlags[i]) {
                case 0:
                    newElements[i] = addRightSpace(strElements[i], elementWidths[i]);
                    break;
                case 1:
                    newElements[i] = addBothSideSpace(strElements[i], elementWidths[i]);
                    break;
                case 2:
                    newElements[i] = addLeftSpace(strElements[i], elementWidths[i]);
                    break;
            }
        }
        return newElements;
    }

    /**
     * �Ҷ���  ����һ���ַ����� �������������ܿ��
     *
     * @return
     */
    private String addLeftSpace(String content, int width) {
        StringBuilder strBuilder = new StringBuilder();
        if (content == null) return null;
        for (int i = 0; i < (width - content.getBytes().length); i++) {
            strBuilder.append(" ");
        }
        return strBuilder.append(content).toString();
    }

    /**
     * �����  ����һ���ַ����� �������������ܿ��
     *
     * @return
     */
    private String addRightSpace(String content, int width) {
        StringBuilder strBuilder = new StringBuilder();
        if (content == null) return null;
        strBuilder.append(content);
        for (int i = 0; i < (width - content.getBytes().length); i++) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    /**
     * �м����  ����һ���ַ����� �������������ܿ��
     *
     * @return
     */
    private String addBothSideSpace(String content, int width) {
        StringBuilder strBuilder = new StringBuilder();
        if (content == null) return null;
        for (int i = 0; i < (width - content.getBytes().length / 2); i++) {
            strBuilder.append(" ");
        }
        strBuilder.append(content);
        for (int i = 0; i < (width - content.getBytes().length / 2); i++) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    // ���Ӽ�¼
    public void addElementsToList(String[] elements) {
        elementsList.add(elements);
    }

    // ����ַ��������Ԫ�أ�ÿ��Ԫ����Ϊһ��
    private void appendArrayToLines(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            for (String str : strArray) {
                fileBuilder.append(str).append("\r\n");
            }
        }
    }

    //  ����ַ��������Ԫ�ص��ļ���һ��
    private void appendArrayToLine(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            for (String str : strArray) {
                fileBuilder.append(str);
            }
        }
    }

    // �������������ݿ�ȣ�ÿ����������¼�����һ����
    private String makeLineSpace() {
        StringBuilder spaceBuilder = new StringBuilder();
        if (elementWidths != null) {
            for (int width : elementWidths) {
                for (int i = 0; i < width; i++) {
                    spaceBuilder.append("-");
                }
            }
        }
        return spaceBuilder.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getLinesAfterTitle() {
        return linesAfterTitle;
    }

    public void setLinesAfterTitle(String[] linesAfterTitle) {
        this.linesAfterTitle = linesAfterTitle;
    }

    public int[] getElementWidths() {
        return elementWidths;
    }

    public void setElementWidths(int[] elementWidths) {
        this.elementWidths = elementWidths;
    }

    public String[] getHeadElements() {
        return headElements;
    }

    public void setHeadElements(String[] headElements) {
        this.headElements = headElements;
    }

    public int[] getAlignFlags() {
        return alignFlags;
    }

    public void setAlignFlags(int[] alignFlags) {
        this.alignFlags = alignFlags;
    }

    public List<String[]> getElementsList() {
        return elementsList;
    }

    public void setElementsList(List<String[]> elementsList) {
        this.elementsList = elementsList;
    }

    public String[] getFileBottomLines() {
        return fileBottomLines;
    }

    public void setFileBottomLines(String[] fileBottomLines) {
        this.fileBottomLines = fileBottomLines;
    }

    public StringBuilder getFileBuilder() {
        return fileBuilder;
    }

    public void setFileBuilder(StringBuilder fileBuilder) {
        this.fileBuilder = fileBuilder;
    }

    public static String getChgLineFlag() {
        return chgLineFlag;
    }

    public static void setChgLineFlag(String chgLineFlag) {
        TableTextFile.chgLineFlag = chgLineFlag;
    }

    public String getOneLine() {
        return oneLine;
    }

    public void setOneLine(String oneLine) {
        this.oneLine = oneLine;
    }
}
