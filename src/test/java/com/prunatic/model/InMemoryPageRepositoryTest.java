package com.prunatic.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryPageRepositoryTest {

    InMemoryPageRepository sut;

    @Before
    public void setUp() throws Exception {
        sut = new InMemoryPageRepository();
    }

    @Test
    public void shouldLetAddPages() throws Exception {
        String aName = "aUsername";
        String aRoles = "aRole";
        Page page = new Page(aName, aRoles);

        sut.add(page);

        assertEquals(1, sut.allPages().length);
    }

    @Test
    public void shouldFindPageByName() throws Exception {
        String aName = "aName";
        Page expectedPage = new Page(aName, "aRole");
        sut.add(expectedPage);

        Page actual = sut.pageByName(aName);

        assertSame(expectedPage, actual);
    }
}