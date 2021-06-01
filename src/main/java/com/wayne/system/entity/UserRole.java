package com.wayne.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName(value = "t_user_role")
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = -3166012934498268403L;
	private Long userId;
	private Long roleId;
}