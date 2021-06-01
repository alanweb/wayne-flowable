package com.wayne.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -7573904024872252113L;
    private Long roleId;
    private Long menuId;
}