package cbs.batch.common;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-12-12
 * Time: 9:55:27
 * To change this template use File | Settings | File Templates.
 */
public class BatchParameterData  implements java.io.Serializable{
      
    private String[] commandLine;
    private String jobId;
    private String jobProcessId;
    private int exitStatus = 1;
    private String userId;
    private String moduleId;

    
    public String[] getCommandLine() {
        return this.commandLine;
    }

    
    public void setCommandLine(String[] commandLine) {
        this.commandLine = commandLine;
    }

    
    public String getJobId() {
        return this.jobId;
    }

    
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    
    public java.lang.String getJobProcessId() {
        return this.jobProcessId;
    }

    
    public void setJobProcessId(String jobProcessId) {
        this.jobProcessId = jobProcessId;
    }

    
    public int getExitStatus() {
        return this.exitStatus;
    }

    
    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    
    public java.lang.String getUserId() {
        return this.userId;
    }

    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    public java.lang.String getModuleId() {
        return this.moduleId;
    }

    
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    
    public String toString() {
        return new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("commandLine", this.commandLine)
                .append("jobId", this.jobId)
                .append("jobProcessId", this.jobProcessId)
                .append("exitStatus", this.exitStatus)
                .append("userId", this.userId)
                .append("moduleId", this.moduleId)
                .toString();
    }
}
