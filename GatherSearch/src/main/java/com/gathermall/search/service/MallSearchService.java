package com.gathermall.search.service;

import com.gathermall.search.vo.SearchParam;
import com.gathermall.search.vo.SearchResult;

public interface MallSearchService {


    SearchResult search(SearchParam param);
}
