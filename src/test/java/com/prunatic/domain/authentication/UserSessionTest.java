package com.prunatic.domain.authentication;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    private UserSession sut;

    @Before
    public void setUp() throws Exception {
        sut = new UserSession("aUsername", new String[]{"aRole"});
    }

    @Test
    public void shouldKeepUserRoles()
    {
        String[] actual = sut.userRoles();

        assertArrayEquals(new String[]{"aRole"}, actual);
    }

    @Test
    public void shouldSetExpiringTimeSomeMinutesAfterNow()
    {
        DateTime actual = sut.expiresAt();

        assertTrue(actual.isAfterNow());
    }

    @Test
    public void shouldLetIncreaseExpiringTime()
    {
        DateTime beforeExpiringDate = sut.expiresAt();
        int minutes = 3;

        sut.increaseExpiringDate(minutes);

        DateTime afterExpiringDate = sut.expiresAt();
        Interval interval = new Interval(beforeExpiringDate, afterExpiringDate);
        int diffInMinutes = (int) interval.toDuration().getStandardMinutes();
        assertEquals(minutes, diffInMinutes);
    }

    @Test
    public void shouldLetExpire()
    {
        sut.expire();

        DateTime expiringTime = sut.expiresAt();
        assertTrue(expiringTime.isBeforeNow());
    }
}