package com.ixigo.testingPages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ixigo.testing.utilities.BaseClass;

public class HotelBookingPage extends BaseClass{

    // ── Locators ─────────────────────────────
	
	
//	@FindBy(xpath = "//img[@title='ixigo Hotels' and @src='https://images.ixigo.com/image/upload/Header/2fa9ebf005adc1ed6473d6229aedfaa5-dwtqq.webp']")
//	private WebElement hotelPage;

    @FindBy(xpath = "//input[@placeholder='Enter city, area or property name']")
    private WebElement destinationField;

    @FindBy(xpath = "//p[text()='Check-in']")
    private WebElement checkInField;

    @FindBy(xpath = "//p[text()='Check-out']")
    private WebElement checkOutField;

    @FindBy(xpath = "//button[.='›']")
    private WebElement nextMonthArrow;

    @FindBy(xpath = "//div[contains(@class,'DayPicker-Caption')]//div")
    private List<WebElement> calendarHeaders;

    @FindBy(xpath = "//p[text()='Rooms & Guests']")
    private WebElement roomAndGuest;

    @FindBy(xpath = "//div[text()='Search']")
    private WebElement searchBtn;

    @FindBy(xpath = "//p[@data-testid='room-increment']")
    private WebElement roomIncrement;

    @FindBy(xpath = "//p[@data-testid='room-decrement']")
    private WebElement roomDecrement;

    @FindBy(xpath = "//p[@data-testid='adult-increment']")
    private WebElement adultIncrement;

    @FindBy(xpath = "//p[@data-testid='adult-decrement']")
    private WebElement adultDecrement;

    @FindBy(xpath = "//p[@data-testid='counter-increment-children']")
    private WebElement childrenIncrement;

    @FindBy(xpath = "//p[@data-testid='counter-decrement-children']")
    private WebElement childrenDecrement;

    // ── Date Formatters ─────────────────────

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

    private static final DateTimeFormatter MONTH_FORMAT =
            DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

    // ── Business Methods ─────────────────────────────

    
//    //click hotel
//    public void clickHotel() {
//        hotelPage.click();
//    }
    
    // Enter destination
    public void enterDestination(String destination) {
        destinationField.click();
        destinationField.sendKeys(destination);
    }

    // Select dates
    public void selectDates(int checkInOffset, int checkOutOffset) throws InterruptedException {

        LocalDate checkInDate = LocalDate.now().plusDays(checkInOffset);
        LocalDate checkOutDate = LocalDate.now().plusDays(checkOutOffset);

        checkInField.click();
        Thread.sleep(2000);

        navigateToMonth(checkInDate);
        clickDate(checkInDate);

        Thread.sleep(1000);

        navigateToMonth(checkOutDate);
        clickDate(checkOutDate);
    }

    // Navigate calendar
    private void navigateToMonth(LocalDate targetDate) {
        String targetMonth = targetDate.format(MONTH_FORMAT);

        for (int i = 0; i < 12; i++) {
            for (WebElement header : calendarHeaders) {
                if (header.getText().equalsIgnoreCase(targetMonth)) {
                    return;
                }
            }
            nextMonthArrow.click();
        }
    }

    // Click specific date
    private void clickDate(LocalDate date) throws InterruptedException {
        String formattedDate = date.format(DATE_FORMAT);

        By dateLocator = By.xpath("//*[@aria-label='" + formattedDate + "']");
        Thread.sleep(1000); // wait for calendar
        driver.findElement(dateLocator).click();
    }

    // Open guest section
    public void openGuestSection() {
        roomAndGuest.click();
    }

    // Room methods
    public void increaseRoom(int count) {
        for (int i = 1; i < count; i++) {
            roomIncrement.click();
        }
    }

    public void decreaseRoom(int count) {
        for (int i = 0; i < count; i++) {
            roomDecrement.click();
        }
    }

    // Adult methods
    public void increaseAdult(int count) {
        for (int i = 1; i < count; i++) {
            adultIncrement.click();
        }
    }

    public void decreaseAdult(int count) {
        for (int i = 0; i < count; i++) {
            adultDecrement.click();
        }
    }

    // Children methods
    public void increaseChildren(int count) {
        for (int i = 0; i < count; i++) {
            childrenIncrement.click();
        }
    }

    public void decreaseChildren(int count) {
        for (int i = 0; i < count; i++) {
            childrenDecrement.click();
        }
    }

    // Click search
    public void clickSearch() {
        searchBtn.click();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//Destination field
//	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name']")
//	private WebElement destinationField;
//	
//	
//	//Check-in date
//	@FindBy(xpath = "//p[text()='Check-in']")
//	private WebElement checkIn;
//	
//	//Check-out date
//	@FindBy(xpath = "//p[text()='Check-out']")
//	private WebElement checkOut;
//	
////	 @FindBy(xpath = "//div[contains(@class,'react-calendar__viewContainer')]")
////	 private WebElement calendarContainer;
//
//	 @FindBy(xpath = "//button[.='›']")
//	 private WebElement nextMonthArrow;
//
//	 @FindBy(xpath = "//button[.='‹']")
//	 private WebElement prevMonthArrow;
//
//	 @FindBy(xpath = "//div[contains(@class,'DayPicker-Caption')]//div")
//	 private List<WebElement> calendarHeaders;
//
//	
//	//Rooms and Guests
//	@FindBy(xpath = "//p[text()='Rooms & Guests']")
//	private WebElement roomAndGuest;
//	
//	//Search button
//	@FindBy(xpath = "//div[text()='Search']")
//	private WebElement searchBtn;
//	
//	@FindBy(xpath = "//p[@data-testid='room-increment']")
//	private WebElement roomIncrement;
//	
//	@FindBy(xpath = "//p[@data-testid='room-decrement']")
//	private WebElement roomDecrement;
//	
//	@FindBy(xpath = "//p[@data-testid='adult-increment']")
//	private WebElement adultIncrement;
//	
//	@FindBy(xpath = "//p[@data-testid='adult-decrement']")
//	private WebElement adultDecrement;
//	
//	@FindBy(xpath = "//p[@data-testid='counter-increment-children']")
//	private WebElement childrenIncrement;
//	
//	@FindBy(xpath = "//p[@data-testid='counter-decrement-children']")
//	private WebElement childrenDecrement;
//	
//	
//
//	public WebElement getRoomIncrement() {
//		return roomIncrement;
//	}
//
//	public WebElement getRoomDecrement() {
//		return roomDecrement;
//	}
//
//	public WebElement getAdultIncrement() {
//		return adultIncrement;
//	}
//
//	public WebElement getAdultDecrement() {
//		return adultDecrement;
//	}
//
//	public WebElement getChildrenIncrement() {
//		return childrenIncrement;
//	}
//
//	public WebElement getChildrenDecrement() {
//		return childrenDecrement;
//	}
//
//	public WebElement getDestinationField() {
//		return destinationField;
//	}
//
//	public WebElement getCheckIn() {
//		return checkIn;
//	}
//	
//	public WebElement getCheckOut() {
//		return checkOut;
//	}
//
//	public WebElement getRoomAndGuest() {
//		return roomAndGuest;
//	}
//
//	public WebElement getSearchBtn() {
//		return searchBtn;
//	}
//	
//	//Business Logic
//	
//	
////	public void selectDates(int checkin, int checkout) {
////		checkIn.click();
////		
////	}
//	
//	public void increaseRoom(int count) {
//		for(int i=1; i<count; i++) {
//			getRoomIncrement().click();
//		}
//	}
//	
//	public void decreaseRoom(int count) {
//		for(int i=0; i<count; i++) {
//			getRoomIncrement().click();
//		}
//	}
//	
//	public void increaseAdult(int count) {
//		for(int i=1; i<count; i++) {
//			getAdultIncrement().click();
//		}
//	}
//	
//	public void decreaseAdult(int count) {
//		for(int i=0; i<count; i++) {
//			getRoomIncrement().click();
//		}
//	}
//	
//	public void incrementChildren(int count) {
//		for(int i=0; i<count; i++) {
//			getAdultIncrement().click();
//		}
//	}
//	
//	public void decreaseChildren(int count) {
//		for(int i=0; i<count; i++) {
//			getRoomIncrement().click();
//		}
//	}
//	public void clickSearch() {
//		searchBtn.click();
//	}
//	
//	
//	
//	
//	
//    // ── Date Formatters ─────────────────────
//
//    private static final DateTimeFormatter ARIA_LABEL_FORMAT =
//            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
//
//    private static final DateTimeFormatter MONTH_HEADER_FORMAT =
//            DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
//
//    // ── Business Logic Methods ─────────────────────────────
//
//    /** Enter destination */
//  
//    public void enterDestination(String destination) {
//    	destinationField.click();
//    	destinationField.sendKeys(destination);
//    }
//   
//    // Select dates (simple working version)
//    public void selectDates(int checkInOffset, int checkOutOffset) throws InterruptedException {
//
//        LocalDate checkInDate = LocalDate.now().plusDays(checkInOffset);
//        LocalDate checkOutDate = LocalDate.now().plusDays(checkOutOffset);
//
//        checkIn.click();
//        Thread.sleep(2000);
//
//        navigateToMonth(checkInDate);
//        clickDate(checkInDate);
//
//        Thread.sleep(1000);
//
//        navigateToMonth(checkOutDate);
//        clickDate(checkOutDate);
//    }
//
//    // Navigate calendar
//    private void navigateToMonth(LocalDate targetDate) {
//        String targetMonth = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
//
//        for (int i = 0; i < 12; i++) {
//            for (WebElement header : calendarHeaders) {
//                if (header.getText().equalsIgnoreCase(targetMonth)) {
//                    return;
//                }
//            }
//            nextMonthArrow.click();
//        }
//    }
//    
//    //click date
//    private void clickDate(LocalDate date) {
//        String formattedDate = date.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH));
//
//        By dateLocator = By.xpath("//*[@aria-label='" + formattedDate + "']");
//        driver.findElement(dateLocator).click();
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    /** Select Guests & Rooms */
//    public void selectGuestsAndRooms(int adults, int rooms) {
//        roomsGuestsField.click();
//
//        adjustCounter(adultIncrement, adultsCount, adults);
//        adjustCounter(roomsIncreaseBtn, roomsCount, rooms);
//    }
//
//    /** Adjust counter */
//    private void adjustCounter(WebElement plusBtn, WebElement countElement, int target) {
//        int current = Integer.parseInt(countElement.getText());
//
//        while (current < target) {
//            plusBtn.click();
//            current++;
//        }
//    }
//
//    /** Click Search */
//    public HotelListingPage clickSearch() {
//        searchButton.click();
//        return new HotelListingPage(driver);
//    }


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	//Select first hotel 
//	@FindBy(xpath = "(//a[.//div[@data-testid='hotel-card']])[1]")
//	private WebElement selectHotel;
//	
//	//Reserve rooms
//	@FindBy(xpath = "//button[@data-testid='reserve-recommended-room']")
//	private WebElement reserveRoom;
//	
//	//Enter Guest Details
//	@FindBy(id = "gender-0")
//	private WebElement mrRadio;
//
//	@FindBy(id = "gender-1")
//	private WebElement mrsRadio;
//
//	@FindBy(id = "gender-2")
//	private WebElement missRadio;
//	
//	@FindBy(id = "firstName-input")
//	private WebElement firstName;
//	
//	@FindBy(id = "lastName-input")
//	private WebElement lastName;
//	
//	@FindBy(id = "email-input")
//	private WebElement email;
//	
//	@FindBy(xpath = "//input[@placeholder='Enter Mobile Number']")
//	private WebElement mobileNumber;
//	
	
	
	
	
	
	

}
