package com.prunatic.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void createWithConstructor()
    {
        String aName = "aName";
        String aRole = "aRole";

        Page page = new Page(aName, aRole);

        assertEquals(aName, page.name());
        assertEquals(aRole, page.requiredRole());
    }
}