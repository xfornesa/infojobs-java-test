package com.prunatic.domain.authentication;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    @Test
    public void shouldKeepUserRoles()
    {
        String[] someRoles = {"aRole"};
        UserSession sut = new UserSession(someRoles);

        String[] actual = sut.userRoles();

        assertArrayEquals(someRoles, actual);
    }

    @Test
    public void shouldLetIncreaseExpiringTime()
    {
        String[] someRoles = {"aRole"};
        UserSession sut = new UserSession(someRoles);
        DateTime beforeExpiringDate = sut.expiresAt();
        int minutes = 3;

        sut.increaseExpiringDate(minutes);

        DateTime afterExpiringDate = sut.expiresAt();
        Interval interval = new Interval(beforeExpiringDate, afterExpiringDate);
        int diffInMinutes = (int) interval.toDuration().getStandardMinutes();
        assertEquals(minutes, diffInMinutes);
    }
}