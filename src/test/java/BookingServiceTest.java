package com.max;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class BookingServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceTest.class);

    private BookingService bookingService;

    @BeforeEach
    public void setUp() throws CantBookException {
        logger.debug("Starting setup...");
        bookingService = mock(BookingService.class);
        doCallRealMethod().when(bookingService).book(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        logger.debug("Setup complete.");
    }

    @Test
    public void testSuccessfulBooking() throws CantBookException {
        logger.debug("Starting testSuccessfulBooking...");
        String userId = "userId";
        LocalDateTime from = LocalDateTime.now().plusDays(1);
        LocalDateTime to = from.plusHours(2);

        when(bookingService.checkTimeInBD(from, to)).thenReturn(true);
        when(bookingService.createBook(userId, from, to)).thenReturn(true);

        boolean result = bookingService.book(userId, from, to);
        logger.debug("Result of testSuccessfulBooking: {}", result);
        assertTrue(result);
        logger.debug("Ending testSuccessfulBooking.");
    }

    @Test
    public void testFailedBooking() {
        logger.debug("Starting testFailedBooking...");
        String userId = "userId";
        LocalDateTime from = LocalDateTime.now().plusDays(1);
        LocalDateTime to = from.plusHours(2);

        when(bookingService.checkTimeInBD(from, to)).thenReturn(false);

        logger.debug("Attempting to execute booking...");
        assertThrows(CantBookException.class, () -> {
            bookingService.book(userId, from, to);
        });
        logger.debug("Ending testFailedBooking.");
    }
}