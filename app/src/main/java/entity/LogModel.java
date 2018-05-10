package entity;

/**
 * Created by kai on 2018/5/10.
 */

public class LogModel {

    private String projectContent;
    private String projectName;
    private String projectStage;
    private boolean isVisiable = false;

    public LogModel(String projectContent, String projectName, String projectStage) {
        this.projectContent = projectContent;
        this.projectName = projectName;
        this.projectStage = projectStage;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
    }

    public boolean isVisiable() {
        return isVisiable;
    }

    public void setVisiable(boolean isVisiable) {
        this.isVisiable = isVisiable;
    }
}
