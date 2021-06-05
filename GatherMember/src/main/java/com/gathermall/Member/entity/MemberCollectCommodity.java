package com.gathermall.Member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员收藏的商品
 *
 */
@Data
@TableName("user_member_collect_commodity")
public class MemberCollectCommodity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * commodity_id
	 */
	private Long commodityId;
	/**
	 * commodity_name
	 */
	private String commodityName;
	/**
	 * commodity_img
	 */
	private String commodityImg;
	/**
	 * create_time
	 */
	private Date createTime;

}
