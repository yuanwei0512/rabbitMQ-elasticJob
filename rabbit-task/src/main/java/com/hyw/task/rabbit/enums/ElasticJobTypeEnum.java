package com.hyw.task.rabbit.enums;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/23 11:11
 * Description：TODO
 */
public enum ElasticJobTypeEnum {

    /**
     * 简单类型job
     */
    SIMPLE("SimpleJob", "简单类型job"),
    DATAFLOW("DataflowJob", "流式类型job"),
    SCRIPT("ScriptJob", "脚本类型job");

    private final String type;

    private final String desc;

    private ElasticJobTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
