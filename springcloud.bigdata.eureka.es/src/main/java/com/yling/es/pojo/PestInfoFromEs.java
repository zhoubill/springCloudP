package com.yling.es.pojo;

/**
 * @Author:WangKeSheng
 * @Description:
 * @Date:2018/10/28 22:41
 * @Modified:
 */
public class PestInfoFromEs {

    /**
     * pestId : 00425fa1-db51-49b9-a82f-7faf072533a3
     * createAt : 2018-10-26 19:05:23
     * updateAt : 2018-10-26 19:05:23
     * status : 0
     * name : 药用植物害虫银杏大蚕蛾
     * detail : 药用植物害虫银杏大蚕蛾详细
     * typeCode : 66a2e7a5-2bc7-499b-bc62-f34871f38b46
     */

    private String pestId;
    private String createAt;
    private String updateAt;
    private int status;
    private String name;
    private String detail;
    private String typeCode;

    public String getPestId() {
        return pestId;
    }

    public void setPestId(String pestId) {
        this.pestId = pestId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
