package com.prunatic.domain.web;

import com.prunatic.domain.web.Page;

public interface PageRepository {
    void add(Page page);

    Page pageByName(String name);
}
