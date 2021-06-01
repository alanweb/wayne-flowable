package com.wayne.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName(value = "t_role")
public class Role implements Serializable {
	private static final long serialVersionUID = -1714476694755654924L;
	@TableId
	private Long roleId;
	private String roleName;
	private String remark;
}