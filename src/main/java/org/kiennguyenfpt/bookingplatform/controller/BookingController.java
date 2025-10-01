package org.kiennguyenfpt.bookingplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kiennguyenfpt.bookingplatform.config.APIRoute;
import org.kiennguyenfpt.bookingplatform.dto.BaseRequest;
import org.kiennguyenfpt.bookingplatform.dto.BaseResponse;
import org.kiennguyenfpt.bookingplatform.dto.booking.*;
import org.kiennguyenfpt.bookingplatform.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // create booking
    @PostMapping(APIRoute.BOOKINGS)
    public ResponseEntity<BaseResponse<BookingResponse>> createBooking(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody BaseRequest<CreateBookingRequest> request) {
        try {
            String token = authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : authHeader;
            BookingResponse response = bookingService.createBooking(token, request.getRequestParameters());
            return ResponseEntity.ok(new BaseResponse<>(
                request.getRequestTrace(), response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(
                request.getRequestTrace(), "03010001", e.getMessage()));
        }
    }

    // approve booking
    @PatchMapping(APIRoute.PHOTOGRAPHERS + "/bookings/approve/{bookingId}")
    public ResponseEntity<BaseResponse<Void>> approveBooking(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable BigInteger bookingId
    ) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7).trim() : authHeader.trim();
        bookingService.approveBooking(token, bookingId);
        return ResponseEntity.ok(new BaseResponse<>(UUID.randomUUID().toString(), null));
    }

    // reject booking
    @PatchMapping(APIRoute.PHOTOGRAPHERS + "/bookings/reject/{bookingId}")
    public ResponseEntity<BaseResponse<Void>> rejectBooking(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable BigInteger bookingId,
            @Valid @RequestBody(required = false) BaseRequest<RejectBookingRequest> request
    ) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7).trim() : authHeader.trim();
        String reason = (request != null && request.getRequestParameters() != null)
                ? request.getRequestParameters().getReasonReject()
                : null;

        bookingService.rejectBooking(token, bookingId, reason);
        return ResponseEntity.ok(new BaseResponse<>(UUID.randomUUID().toString(), null));
    }

    // Get my bookings
    @GetMapping(APIRoute.BOOKINGS + "/me")
    public ResponseEntity<BaseResponse<List<BookingResponse>>> getMyBookings(
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : authHeader;

            List<BookingResponse> response = bookingService.getMyBookings(token);
            return ResponseEntity.ok(new BaseResponse<>(
                UUID.randomUUID().toString(), response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(UUID.randomUUID().toString(), "03010001", e.getMessage()));
        }
    }

    // Get booking by id
    @GetMapping(APIRoute.BOOKINGS + "/{bookingId}")
    public ResponseEntity<BaseResponse<BookingResponse>> getBookingById(
            @PathVariable String bookingId) {
        try {
            BookingResponse response = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok(new BaseResponse<>(
                    UUID.randomUUID().toString(), response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(
                    UUID.randomUUID().toString(), "03010001", e.getMessage()));
        }
    }

    // Get all bookings for a photographer
    @GetMapping(APIRoute.BOOKINGS + "/photographer")
    public ResponseEntity<BaseResponse<List<BookingResponse>>> getPhotographerBookings(
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.startsWith("Bearer ")
                    ? authHeader.substring(7)
                    : authHeader;
            List<BookingResponse> response = bookingService.getAllPhotographerBookings(token);
            return ResponseEntity.ok(new BaseResponse<>(
                    UUID.randomUUID().toString(), response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(
                    UUID.randomUUID().toString(), "01030018", e.getMessage()));
        }
    }

    // Get all bookings (admin)
    @GetMapping(APIRoute.BOOKINGS)
    public ResponseEntity<BaseResponse<List<BookingResponse>>> getAllBookings() {
        try {
            List<BookingResponse> response = bookingService.getAll();
            return ResponseEntity.ok(new BaseResponse<>(
                UUID.randomUUID().toString(), response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(
                UUID.randomUUID().toString(), "03010001", e.getMessage()));
        }
    }
    
    @GetMapping("/stats/photographer")
    public ResponseEntity<BaseResponse<Object>> getPhotographerBookingStats(
            @RequestHeader("Authorization") String token,
            @RequestParam("requestTrace") String requestTrace) {
        try {
            Object response = bookingService.getPhotographerBookingStats(token);
            return ResponseEntity.ok(new BaseResponse<>(
                requestTrace, response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(
                requestTrace, "03050001", e.getMessage()));
        }
    }
}