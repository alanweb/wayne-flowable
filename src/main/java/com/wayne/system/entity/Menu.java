package com.wayne.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 7187628714679791771L;
    public static final String TYPE_MENU = "0";
    public static final String TYPE_BUTTON = "1";
    @TableId
    private Long menuId;
    private Long parentId;
    private String menuName;
    private String url;
    private String perms;
    private String icon;
    private String type;
    private Long orderNum;
    private Date createTime;
    private Date modifyTime;
}