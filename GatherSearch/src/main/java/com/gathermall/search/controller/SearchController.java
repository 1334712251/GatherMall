package com.gathermall.search.controller;


import com.gathermall.common.utils.R;
import com.gathermall.search.service.MallSearchService;
import com.gathermall.search.vo.SearchParam;
import com.gathermall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("querySearch")
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    /**
     * 自动将页面提交过来的所有请求参数封装成我们指定的对象
     * @param param
     * @return
     */
    @GetMapping(value = "/search")
    public R listPage(SearchParam param, HttpServletRequest request) {

        param.setQueryString(request.getQueryString());

        //1、根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);



        return R.ok().put("data",result);
    }

}
