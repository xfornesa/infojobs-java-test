package com.prunatic.infrastructure.persistence.cms;

import com.prunatic.domain.cms.Page;
import com.prunatic.domain.cms.PageRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPageRepository implements PageRepository {
    List<Page> elements;

    public InMemoryPageRepository() {
        elements = new ArrayList<Page>();
    }

    public void add(Page page) {
        elements.add(page);
    }

    public Page pageByName(String name) {
        for( Page page: elements.toArray(new Page[elements.size()])) {
            if (page.name().equals(name)) {
                return page;
            }
        }

        return null;
    }

    public Page[] allPages() {
        return elements.toArray(new Page[elements.size()]);
    }
}
