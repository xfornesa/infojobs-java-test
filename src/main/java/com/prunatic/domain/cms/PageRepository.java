package com.prunatic.domain.cms;

import com.prunatic.domain.cms.Page;

public interface PageRepository {
    void add(Page page);

    Page pageByName(String name);
}
