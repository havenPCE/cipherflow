package model;


import java.io.Serializable;
import java.util.Date;

public class EFile implements Serializable {
    private String fileName;
    private String filePath;
    private Date lastEncrypted;

    public EFile() {
    }

    public EFile(String fileName, String filePath, Date lastEncrypted) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastEncrypted = lastEncrypted;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastEncrypted() {
        return lastEncrypted;
    }

    public void setLastEncrypted(Date lastEncrypted) {
        this.lastEncrypted = lastEncrypted;
    }

    @Override
    public String toString() {
        return "EFile{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", lastEncrypted=" + lastEncrypted +
                '}';
    }
}
