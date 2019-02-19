package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-31 15:07:12
 */
@TableName("sys_mem")
public class SysMemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 身份证号
	 */
	private String card;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标记  -1：已删除  0：正常
	 */
	private Integer delFlag;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：身份证号
	 */
	public void setCard(String card) {
		this.card = card;
	}
	/**
	 * 获取：身份证号
	 */
	public String getCard() {
		return card;
	}
	/**
	 * 设置：简介
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取：简介
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：删除标记  -1：已删除  0：正常
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标记  -1：已删除  0：正常
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
