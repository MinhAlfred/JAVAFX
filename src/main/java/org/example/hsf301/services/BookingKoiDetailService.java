package org.example.hsf301.services;

import org.example.hsf301.dtos.request.BookingKoiDetailRequest;
import org.example.hsf301.pojos.*;
import org.example.hsf301.repos.*;

import java.util.List;

public class BookingKoiDetailService implements IBookingKoiDetailService{
    private final IBookingKoiDetailRepository bookingKoiDetailRepo;
    private final IKoiRepository koiRepository;
    private final IBookingRepository bookingRepository;
    private final IKoiFarmService koiFarmService;

    public BookingKoiDetailService(String name) {
        bookingKoiDetailRepo = new BookingKoiDetailRepository(name);
        koiRepository = new KoiRepository(name);
        bookingRepository = new BookingRepository(name);
        koiFarmService = new KoiFarmService(name);
    }
    @Override
    public BookingKoiDetail createKoiDetail(BookingKoiDetailRequest bookingKoiDetailRequest, Long bookingId) {
        Bookings bookings = bookingRepository.findById(bookingId);
        if(bookings == null) {
            throw new IllegalArgumentException("BookingKoiDetail with id " + bookingId + " not found");
        }
        KoiFarms koiFarms = koiFarmService.findById(bookingKoiDetailRequest.getFarmId());
        if(koiFarms == null) {
            throw new IllegalArgumentException("Koi Farm " + bookingKoiDetailRequest.getFarmId() + " not found");
        }
        Koi koi = koiRepository.findById(bookingKoiDetailRequest.getKoiId());
        if(koi == null) {
            throw new IllegalArgumentException("Koi " + bookingKoiDetailRequest.getKoiId() + " not found");
        }
        BookingKoiDetail bookingKoiDetail = new BookingKoiDetail();
        bookingKoiDetail.setBooking(bookings);
        bookingKoiDetail.setKoi(koi);
        bookingKoiDetail.setKoiFarms(koiFarms);
        bookingKoiDetail.setQuantity(bookingKoiDetailRequest.getQuantity());
        bookingKoiDetail.setUnitPrice(bookingKoiDetailRequest.getUnitPrice());
        bookingKoiDetail.setTotalAmount(bookingKoiDetailRequest.getUnitPrice() * bookingKoiDetailRequest.getQuantity());
        bookingKoiDetailRepo.save(bookingKoiDetail);

        float totalBookingAmount = bookings.getTotalAmount();
        totalBookingAmount += bookingKoiDetail.getTotalAmount();
        bookings.setTotalAmount(totalBookingAmount);
        bookings.setVatAmount((bookings.getTotalAmount()-bookings.getDiscountAmount())*bookings.getVat());
        bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
        bookingRepository.update(bookings);

        return bookingKoiDetail;
    }

    @Override
    public void deletebyBookingKoiDetail(Long bookingKoiDetailId,Account staff) {
        BookingKoiDetail bookingKoiDetail = bookingKoiDetailRepo.findById(bookingKoiDetailId);
        if(bookingKoiDetail == null) {
            throw new IllegalArgumentException("BookingKoiDetail with id " + bookingKoiDetailId + " not found");
        }
        Bookings bookings = bookingKoiDetail.getBooking();
        float totalBookingAmount = bookings.getTotalAmount();
        totalBookingAmount -= bookingKoiDetail.getTotalAmount();
        bookings.setTotalAmount(totalBookingAmount);
        bookings.setVatAmount(bookings.getVat()*(bookings.getTotalAmount()- bookings.getDiscountAmount()));
        bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
        bookings.setUpdatedBy(staff);

        bookingKoiDetailRepo.delete(bookingKoiDetailId);
        bookingRepository.update(bookings);

    }

    @Override
    public List<BookingKoiDetail> bookingKoiDetails(Long bookingID) {
        return bookingKoiDetailRepo.findByBookingId(bookingID);
    }

    @Override
    public BookingKoiDetail updateBookingKoiDetail(Long id, BookingKoiDetailRequest bookingKoiDetailRequest,Account staff) {
        BookingKoiDetail bookingKoiDetail = bookingKoiDetailRepo.findById(id);
        if(bookingKoiDetail == null) {
            throw new IllegalArgumentException("BookingKoiDetail with id " + id + " not found");
        }
        Bookings bookings = bookingKoiDetail.getBooking();
        float totalBookingAmount = bookings.getTotalAmount();
        totalBookingAmount -= bookingKoiDetail.getTotalAmount();

        bookingKoiDetail.setQuantity(bookingKoiDetailRequest.getQuantity());
        bookingKoiDetail.setUnitPrice(bookingKoiDetailRequest.getUnitPrice());
        bookingKoiDetail.setTotalAmount(bookingKoiDetailRequest.getUnitPrice() * bookingKoiDetailRequest.getQuantity());
        bookingKoiDetailRepo.update(bookingKoiDetail);

        totalBookingAmount += bookingKoiDetail.getTotalAmount();
        bookings.setTotalAmount(totalBookingAmount);
        bookings.setVatAmount(bookings.getVat()*(bookings.getTotalAmount()- bookings.getDiscountAmount()));
        bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
        bookings.setUpdatedBy(staff);
        bookingRepository.update(bookings);


        return bookingKoiDetail;
    }
}
