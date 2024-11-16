package org.example.hsf301.navigate;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.controllers.BookingController;
import org.example.hsf301.controllers.BookingKoiController;
import org.example.hsf301.controllers.FarmController;
import org.example.hsf301.controllers.KoiController;
import org.example.hsf301.controllers.MyProfileController;
import org.example.hsf301.controllers.TourController;
import org.example.hsf301.controllers.managements.BookingManagementController;
import org.example.hsf301.controllers.managements.FarmManagementController;
import org.example.hsf301.controllers.managements.KoiManagementController;
import org.example.hsf301.controllers.managements.TourManagementController;
import org.example.hsf301.services.AccountService;
import org.example.hsf301.services.BookingKoiService;
import org.example.hsf301.services.BookingService;
import org.example.hsf301.services.BookingTourService;
import org.example.hsf301.services.IBookingTourService;
import org.example.hsf301.services.KoiFarmService;
import org.example.hsf301.services.KoiService;
import org.example.hsf301.services.TourService;
import org.example.hsf301.utils.NavigateUtil;

public interface Navigable {

    // Default method to navigate to the Home page
    default void navigateHome(StackPane contentArea) throws IOException {
        setContent("home", contentArea);
    }

    // Default method to navigate to the Tours page
    default void navigateTour(StackPane contentArea) throws IOException {
        setContent("tour", contentArea);
    }

    default void navigateBookingManagement(StackPane contentArea) throws IOException {
        setContent("bookings_management", contentArea);
    }
    default void navigateQuotationSaleStaff(StackPane contentArea) throws IOException {
        setContent("Quotation", contentArea);
    }

    // Default method to navigate to the Settings page
    default void navigateSetting(StackPane contentArea) throws IOException {
        setContent("quotationManagement", contentArea);
    }

    default void navigateKoi(StackPane contentArea) throws IOException {
        setContent("koi", contentArea);
    }

    default void navigateStaff(StackPane contentArea) throws IOException {
        setContent("staff", contentArea);
    }

    //navigateMyProfile
    default void navigateMyProfile(StackPane contentArea) throws IOException {
        setContent("my_profile", contentArea);
    }

    //navigateFarm
    default void navigateFarm(StackPane contentArea) throws IOException {
        setContent("farm", contentArea);
    }

    default void navigateLogout(StackPane contentArea) throws IOException {
        NavigateUtil.navigateTo(ResourcePaths.LOGIN_VIEW, contentArea, 830, 650, "Hello!");
    }

    default void navigateTourHomePage(StackPane contentArea) throws IOException {
        setContent("tours_home_page", contentArea);
    }

    default void navigatePurchasedTour(StackPane contentArea) throws IOException {
        setContent("purchased_tour", contentArea);
    }

    default void navigateBookingKoi(StackPane contentArea) throws IOException {
        setContent("booking_koi", contentArea);
    }

    default void navigateKois(StackPane contentArea) throws IOException {
        setContent("kois", contentArea);
    }

    default void navigateFarms(StackPane contentArea) throws IOException {
        setContent("farms", contentArea);
    }

    //Management
    default void navigateTourManagement(StackPane contentArea) throws IOException {
        setContent("tours_management", contentArea);
    }

    default void navigateFarmManagement(StackPane contentArea) throws IOException {
        setContent("farms_management", contentArea);
    }

    default void navigateKoiManagement(StackPane contentArea) throws IOException {
        setContent("kois_management", contentArea);
    }

    //Staff
    default void navigateBookingKoiList(StackPane contentArea) throws IOException {
        setContent("BookingKoiListStaff", contentArea);
    }
    default void navigateBookingTourList(StackPane contentArea) throws IOException {
        setContent("BookingTourListStaff", contentArea);
    }
    default void navigateBookingTourListSaleStaff(StackPane contentArea) throws IOException {
        setContent("BookingTourListSaleStaff", contentArea);
    }

    default void navigateDelivery(StackPane contentArea) throws IOException {
        setContent("Delivery", contentArea);
    }
    default void navigateDeposit(StackPane contentArea) throws IOException {
        setContent("Deposit", contentArea);
    }

    // Method to load and set the content in the provided contentArea
    default void setContent(String page, StackPane contentArea) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(
                getClass().getResource("/org/example/hsf301/fxml/" + page + ".fxml")));

        // preload on startup with initialize method
        //preload data when customer login
        if (page.equals("tours_home_page")) {
            TourService tourService = new TourService(ResourcePaths.HIBERNATE_CONFIG);
            IBookingTourService bookingTourService = new BookingTourService(ResourcePaths.HIBERNATE_CONFIG);
            TourController tourController = new TourController(tourService, bookingTourService);
            loader.setController(tourController);
        }

        if (page.equals("tours_management")) {
            TourService tourService = new TourService(ResourcePaths.HIBERNATE_CONFIG);
            TourManagementController tourManagementController = new TourManagementController(
                tourService);
            loader.setController(tourManagementController);
        }

        if (page.equals("bookings_management")) {
            BookingManagementController bookingManagementController = new BookingManagementController(new BookingService(ResourcePaths.HIBERNATE_CONFIG));
            loader.setController(bookingManagementController);
        }

        if (page.equals("farms_management")) {
            FarmManagementController farmManagementController = new FarmManagementController(new KoiFarmService(ResourcePaths.HIBERNATE_CONFIG));
            loader.setController(farmManagementController);
        }

        //kois_management
        if(page.equals("kois_management")){
            KoiManagementController koiManagementController = new KoiManagementController(new KoiService(ResourcePaths.HIBERNATE_CONFIG));
            loader.setController(koiManagementController);
        }

        if (page.equals("my_profile")) {
            AccountService accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
            MyProfileController myProfileController = new MyProfileController(accountService);
            loader.setController(myProfileController);
        }

        if(page.equals("booking_koi")){
            BookingKoiController bookingKoiController =
                new BookingKoiController(new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG));
            loader.setController(bookingKoiController);
        }

        if(page.equals("kois")){
             KoiController koiController = new KoiController(new KoiService(ResourcePaths.HIBERNATE_CONFIG));
             loader.setController(koiController);
        }

        if(page.equals("farms")){
             FarmController farmController = new FarmController(new KoiFarmService(ResourcePaths.HIBERNATE_CONFIG));
             loader.setController(farmController);
        }

        if(page.equals("purchased_tour")){
             BookingController bookingController = new BookingController(new BookingService(ResourcePaths.HIBERNATE_CONFIG));
             loader.setController(bookingController);
        }

        //preload data when admin login

        //preload data when staff login

        Node pageContent = loader.load();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(pageContent);
    }
}
