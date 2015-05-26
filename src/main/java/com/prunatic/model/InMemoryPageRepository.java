package com.prunatic.model;

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

    public Page[] allPages() {
        return elements.toArray(new Page[elements.size()]);
    }
}
