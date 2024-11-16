package org.example.hsf301.services;

import org.example.hsf301.dtos.request.BookingTourDetailRequest;
import org.example.hsf301.pojos.BookingTourDetail;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.repos.*;

import java.util.List;

public class BookingTourDetailService implements IBookingTourDetailService {
    private final IBookingTourDetailRepository bookingTourDetailRepository;
    private final ITourRepository tourRepository;
    private final IBookingRepository bookingRepository;
    public BookingTourDetailService(String name) {
        bookingTourDetailRepository = new BookingTourDetailRepository(name);
        tourRepository = new TourRepository(name);
        bookingRepository = new BookingRepository(name);
    }
    @Override
    public BookingTourDetail addBookingTourDetail(BookingTourDetailRequest bookingTourDetailRequest) {
        BookingTourDetail bookingTourDetail = new BookingTourDetail();
//        if(bookingTourDetailRequest == null) {
//            throw new IllegalArgumentException("bookingTourDetailRequest cannot be null");
//        }
//        Bookings bookings = bookingRepository.findById(bookingTourDetailRequest.getBookingID());
//        Tours tours = tourRepository.findById(bookingTourDetailRequest.getTourID());
//        if(bookings == null) {
//            throw new IllegalArgumentException("bookings cannot be null");
//        }
//        if(tours == null) {
//            throw new IllegalArgumentException("tours cannot be null");
//        }
//        bookingTourDetail.setParticipant(bookingTourDetailRequest.getParticipant());
//        bookingTourDetail.setTourId(tours);
//        bookingTourDetail.setBooking(bookings);
//        bookingTourDetailRepository.save(bookingTourDetail);
        return bookingTourDetail;
    }

    @Override
    public BookingTourDetail updateBookingTourDetail(Long bookingTourDetailId, BookingTourDetailRequest bookingTourDetailRequest) throws Exception {
        BookingTourDetail bookingTourDetail = bookingTourDetailRepository.findById(bookingTourDetailId);
//        if(bookingTourDetail == null) {
//            throw new IllegalArgumentException("bookingTourDetail cannot be null");
//        }
//        Bookings bookings = bookingRepository.findById(bookingTourDetailRequest.getBookingID());
//        if(bookings == null) {
//            throw new IllegalArgumentException("bookings cannot be null");
//        }
//        Tours tours = tourRepository.findById(bookingTourDetailRequest.getTourID());
//        if(tours == null) {
//            throw new IllegalArgumentException("tours cannot be null");
//        }
//        bookingTourDetail.setParticipant(bookingTourDetailRequest.getParticipant());
//        bookingTourDetail.setTourId(tours);
//        bookingTourDetail.setBooking(bookings);
//        bookingTourDetailRepository.update(bookingTourDetail);
        return bookingTourDetail;
    }

    @Override
    public void deleteBookingTourDetail(Long bookingTourDetailId) {
        Bookings bookings = bookingRepository.findById(bookingTourDetailId);
        if(bookings == null) {
            throw new IllegalArgumentException("bookings cannot be null");
        }
        bookingTourDetailRepository.delete(bookingTourDetailId);

    }

    @Override
    public BookingTourDetail getBookingTourDetail(Long bookingTourDetailId) {
      BookingTourDetail bookingTourDetail = bookingTourDetailRepository.findById(bookingTourDetailId);
      if(bookingTourDetail == null) {
          throw new IllegalArgumentException("bookingTourDetail cannot be null");
      }
      return bookingTourDetail;
    }

    @Override
    public List<BookingTourDetail> getAllBookingTourDetails() {
        List<BookingTourDetail> bookingTourDetails = bookingTourDetailRepository.getAll();
        if(bookingTourDetails == null) {
            throw new IllegalArgumentException("bookingTourDetails cannot be null");
        }
        return bookingTourDetails;
    }

    @Override
    public List<BookingTourDetail> bookingTourDetails(Long bookingID) {
        return bookingTourDetailRepository.findByBookingId(bookingID);
    }
}
