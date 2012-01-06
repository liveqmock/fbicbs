package cbs.batch.common;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-12-15
 * Time: 16:37:59
 * To change this template use File | Settings | File Templates.
 */

import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.*;

public class BatchLauncher {

    private static final String SYS_KEY_PROPERTYFILE = "propertyFile";
    private static final String PFILE_KEY_BASEPATH = "basePath";
    private static final String PFILE_KEY_CLASSPATH = "classPath";
    private static final String PFILE_KEY_JARPATH = "jarPath";
    private static final String PFILE_KEY_JARNAME = ".jarName";
    private static final int EXITSTATUS_BATCHMAIN_STARTERROR = 120;
    private static final String TARGET_CLASS_NAME = "cbs.batch.common.CommonBatch";
    //    private static final String TARGET_CLASS_NAME = "cbs.batch.common.CommonTest";
    private static final String TARGET_CLASS_METHOD_NAME = "batchMain";

    public static void main(String[] args) {
        try {
            new BatchLauncher().start(args);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        System.exit(EXITSTATUS_BATCHMAIN_STARTERROR);
    }

    private void start(String[] args) {

        try {
            Class clazz = this.getClass().getClassLoader().loadClass(TARGET_CLASS_NAME);
            Class[] methodTypes = new Class[]{String[].class};
            Method method = clazz.getMethod(TARGET_CLASS_METHOD_NAME, methodTypes);
            Object[] invokeArgs = new Object[]{args};

            method.invoke(null, invokeArgs);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Class not found！", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Method 访问失败！", ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Method 未发现！", ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(TARGET_CLASS_NAME + "." + TARGET_CLASS_METHOD_NAME + "运行出现异常！",
                    ex.getCause());
        }
    }

    /*
        private void start(String[] args) {

            String fileName = (System.getProperty(SYS_KEY_PROPERTYFILE));

            if (fileName == null || fileName.length() == 0) {
                throw new RuntimeException("System Error:" + SYS_KEY_PROPERTYFILE + "文件错误！");
            }

            //File file = new File(fileName);
            Properties prop = getProperties(fileName);

            List<String> classpathList = makeClasspathList(prop);

            boolean errorFlag = false;
            URL[] urls = new URL[classpathList.size()];

            try {
                for (int i = 0; i < classpathList.size(); i++) {
                    File classpath = new File(classpathList.get(i));
                    urls[i] = classpath.toURI().toURL();
                }
            } catch (MalformedURLException ex) {
                errorFlag = true;
                System.err.println(ex.getMessage());
            }

            if (errorFlag) {
                throw new RuntimeException("Classpath 指定错误！");
            }

            //-
            URLClassLoader classLoader = new URLClassLoader(urls);
            Thread.currentThread().setContextClassLoader(classLoader);


            try {
                Class clazz = classLoader.loadClass(TARGET_CLASS_NAME);
                Class[] methodTypes = new Class[]{String[].class, Properties.class};
                Method method = clazz.getMethod(TARGET_CLASS_METHOD_NAME, methodTypes);
                Object[] invokeArgs = new Object[]{args, prop};

                method.invoke(null, invokeArgs);
            } catch (ClassNotFoundException ex) {
                dumpClassPath(classLoader);
                throw new RuntimeException("Class 未找到！", ex);
            } catch (IllegalAccessException ex) {
                dumpClassPath(classLoader);
                throw new RuntimeException("Method 访问失败！", ex);
            } catch (NoSuchMethodException ex) {
                dumpClassPath(classLoader);
                throw new RuntimeException("Method 未发现！", ex);
            } catch (InvocationTargetException ex) {
                dumpClassPath(classLoader);
                throw new RuntimeException(TARGET_CLASS_NAME + "." + TARGET_CLASS_METHOD_NAME + "运行出现异常！",
                        ex.getCause());
            }
        }

    */
    private void dumpClassPath(URLClassLoader classLoader) {
        System.err.println("System class path:" + System.getProperty("java.class.path"));
        System.err.println("Additional class path:" + Arrays.toString(classLoader.getURLs()));
    }

    private List<String> makeClasspathList(Properties prop) {

        boolean errorFlag = false;
        List<String> list = new ArrayList<String>();

        //System.out.println(System.getProperty("user.dir"));
        //System.out.println(System.getProperty("java.class.path"));

        String basePath = (System.getProperty(PFILE_KEY_BASEPATH));
        if (basePath == null) {
            basePath = "";
        }
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }

        for (int i = 1; ; i++) {
            boolean result = false;
            String classpathKey = PFILE_KEY_CLASSPATH + i;
            String classpath = prop.getProperty(classpathKey);

            if (classpath != null && classpath.length() != 0) {
                classpath = classpath.trim();
                if (!classpath.endsWith("/")) {
                    classpath += "/";
                }
                if (classpath.startsWith("basePath:")) {
                    classpath = basePath + classpath.split(":")[1];
                }
                list.add(classpath);
                result = true;
                File file = new File(classpath);

                if (!file.isDirectory()) {
                    errorFlag = true;
                    System.err.println("[" + classpathKey + "]:[" + classpath + "] 不存在！");
                }
            }
            String jarpathKey = PFILE_KEY_JARPATH + i;
            String jarpath = prop.getProperty(jarpathKey);


            if (jarpath != null && jarpath.length() != 0) {
                jarpath = jarpath.trim();
                if (jarpath.startsWith("basePath:")) {
                    jarpath = basePath + jarpath.split(":")[1];
                }

                if (!jarpath.endsWith(File.separator)) {
                    jarpath += File.separator;
                }
                for (int j = 1; ; j++) {
                    String jarNameKey = jarpathKey + PFILE_KEY_JARNAME + j;
                    String jarName = prop.getProperty(jarNameKey);
                    if (jarName == null) {
                        break;
                    }
                    String jarNameWithPath = jarpath + jarName.trim();
                    File file = new File(jarNameWithPath);
                    list.add(jarNameWithPath);
                    result = true;
                    if (!file.canRead()) {
                        errorFlag = true;
                        System.err.println("[" + jarpathKey + "]:[" + jarNameKey + "]:[" + jarNameWithPath + "]不能读取。");
                    }
                }
            }
            if (!result) {
                break;
            }
        }
        if (errorFlag) {
            throw new RuntimeException("Classpath 定义失败！");
        }
        return list;
    }

    private Properties getProperties(String file) {
        InputStream is = null;
        Properties prop = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(file);
            //is = new BufferedInputStream(new FileInputStream(file));
            prop = new Properties();
            prop.loadFromXML(is);
            is.close();
            is = null;
            return prop;
        } catch (InvalidPropertiesFormatException ex) {
            SAXParseException saxex = (SAXParseException) ex.getCause();
            String msg = file + "(" + saxex.getLineNumber() + ":" + saxex.getColumnNumber() + "):" + saxex.getMessage();
            throw new RuntimeException(msg, ex);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("文件不存在", ex);
        } catch (IOException ex) {
            throw new RuntimeException("文件读取错误！", ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    System.err.println("文件处理错误");
                }
                is = null;
            }
        }
    }

}
