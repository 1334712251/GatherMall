package com.gathermall.Member.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("GatherMember")
public interface MemberFeignServer {
}
