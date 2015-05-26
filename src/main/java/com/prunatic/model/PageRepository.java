package com.prunatic.model;

public interface PageRepository {
    void add(Page page);

    Page pageByName(String name);
}
