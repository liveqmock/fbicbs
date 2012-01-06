package cbs.batch.ac.acbint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-6-2
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class TableTextFile {
    private String title;                                 // 标题
    private String[] linesAfterTitle;            // 副标题区，每个字符元素占一行
    private int[] elementWidths;              // 每个列宽度
    private String[] headElements;            // 列标题
    private int[] alignFlags;                       //  列对齐标记数组，0--左对齐，1--中间对齐，2--右对齐
    private List<String[]> elementsList = new ArrayList<String[]>();                     // 列内容
    private String[] fileBottomLines;                // 文件结束区,每个字符元素占一行
    private StringBuilder fileBuilder = new StringBuilder();
    private static String chgLineFlag = "\r\n";
    private String oneLine;

    // 生成文件内容字符串
    public String toString() {
        // 增加列标题
        if (title != null) {
            fileBuilder.append(title).append(chgLineFlag).append(chgLineFlag);
        }
        // 增加副标题
        appendArrayToLines(linesAfterTitle);
        fileBuilder.append(chgLineFlag);
        //  增加列标题
        appendArrayToLine(adjustSpacestrElements(headElements));
        fileBuilder.append(chgLineFlag);
        String aLine = oneLine;
        //makeLineSpace();
        // 增加列内容
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
        // 增加文件结束区
        appendArrayToLines(fileBottomLines);
        return fileBuilder.toString();
    }

    // 添加空格到列中，完成对齐
    private String[] adjustSpacestrElements(String[] strElements) {

        if (strElements == null || strElements.length <= 0
                || strElements.length != alignFlags.length || strElements.length != elementWidths.length) {
            throw new RuntimeException("数组内容，列对齐标记数组，列宽度数组的长度有不一致。");
        }
        String[] newElements = strElements;
        for (int i = 0; i < strElements.length; i++) {
            // 0--左对齐，1--中间对齐，2--右对齐
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
     * 右对齐  参数一：字符内容 参数二：数据总宽度
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
     * 左对齐  参数一：字符内容 参数二：数据总宽度
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
     * 中间对齐  参数一：字符内容 参数二：数据总宽度
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

    // 增加记录
    public void addElementsToList(String[] elements) {
        elementsList.add(elements);
    }

    // 添加字符串数组的元素，每个元素视为一行
    private void appendArrayToLines(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            for (String str : strArray) {
                fileBuilder.append(str).append("\r\n");
            }
        }
    }

    //  添加字符串数组的元素到文件中一行
    private void appendArrayToLine(String[] strArray) {
        if (strArray != null && strArray.length > 0) {
            for (String str : strArray) {
                fileBuilder.append(str);
            }
        }
    }

    // 计算列所有内容宽度，每增加五条记录则填充一横线
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
