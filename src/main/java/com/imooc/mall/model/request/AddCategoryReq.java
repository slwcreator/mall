package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCategoryReq {

    @Size(min = 2, max = 5, message = "分类目录名称长度必须在2到5之间")
    @NotNull(message = "分类目录名称不能为空")
    private String name;

    @Max(value = 3, message = "分类目录最多为3级")
    @NotNull(message = "分类目录级别不能为空")
    private Integer type;

    @NotNull(message = "父id不能为空")
    private Integer parentId;

    @NotNull(message = "目录展示时的排序不能为空")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
