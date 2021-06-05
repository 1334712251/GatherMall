package com.gathermall.Member.dao;

import com.gathermall.Member.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberDao extends BaseMapper<Member> {
	
}
