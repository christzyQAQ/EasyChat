package com.easychat.entity.vo;

import java.io.Serializable;
import java.util.List;

public class UpdateVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8251364354844091120L;

    private String id;
    private Long size;
    private String flieName;  
    private String version;
    private String outerLink; 
    private List<String> updateList;
    private Integer fileType;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public String getFlieName() {
        return flieName;
    }
    public void setFlieName(String flieName) {
        this.flieName = flieName;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getOuterLink() {
        return outerLink;
    }
    public void setOuterLink(String outerLink) {
        this.outerLink = outerLink;
    }
    public List<String> getUpdateList() {
        return updateList;
    }
    public void setUpdateList(List<String> updateList) {
        this.updateList = updateList;
    }
    public Integer getFileType() {
        return fileType;
    }
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }


    
    



}
