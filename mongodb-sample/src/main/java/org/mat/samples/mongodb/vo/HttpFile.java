package org.mat.samples.mongodb.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: E010925
 * Date: 29/11/12
 * Time: 16:32
 */
@XmlRootElement
public class HttpFile {

    private String fileName;
    private boolean fileDone;

    public HttpFile() {
    }

    public HttpFile(String fileName, boolean fileDone) {
        this.fileName = fileName;
        this.fileDone = fileDone;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isFileDone() {
        return fileDone;
    }

    public void setFileDone(boolean fileDone) {
        this.fileDone = fileDone;
    }


    @Override
    public String toString() {
        return "HttpFile{" +
                "fileName='" + fileName + '\'' +
                ", fileDone=" + fileDone +
                '}';
    }
}
