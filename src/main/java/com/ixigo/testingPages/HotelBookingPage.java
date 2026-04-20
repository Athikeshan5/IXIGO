
package com.ixigo.testingPages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.testing.utilities.AllUtilityFunctions;
import com.ixigo.testing.utilities.BaseClass;

public class HotelBookingPage extends BaseClass {

	// ─── Locators ──────────────────────────────────────────────────────────────

	@FindBy(xpath = "//input[@placeholder='Enter city, area or property name' and @data-testid='location-input-web']")
	private WebElement destinationField;
	
	@FindBy(xpath = "//div[@data-testid='Chennai']")
	private WebElement selectSuggestion;
	
	@FindBy(xpath = "//button[@data-testid='search-hotels']")
    private WebElement searchBtn;


	private By checkInField=By.xpath ( "//input[@data-testid='checkin-input']");
	
	@FindBy(xpath="//input[@data-testid='checkout-input']")
	private WebElement checkout;

	@FindBy(xpath = "//button[@aria-label and contains(@class,'react-calendar__navigation__next-button')]")
	WebElement nextMonthButton;

	@FindBy(xpath = "//span[contains(@class,'react-calendar__navigation__label__labelText--from')]")
	WebElement monthFrom;

	@FindBy(xpath = "//span[contains(@class,'react-calendar__navigation__label__labelText--to')]")
	WebElement monthTo;

	

	

	// ─── Action Methods ────────────────────────────────────────────────────────

	public void enterDestination(String city) {
		AllUtilityFunctions allUtilityFunctions = new AllUtilityFunctions();
		allUtilityFunctions.waitForVisibility(getDriver(), destinationField, 20);
		destinationField.sendKeys(city);
		destinationField.click();
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='Chennai']"))).click();
		//selectSuggestion.click();
		
	}

	public void openCalendar() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

	    WebElement checkIn = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//input[contains(@placeholder,'Check')]")
	            )
	    );

	    checkIn.click();
	
	}

//	public void selectDate(String monthYear, String day) {
//
//	    boolean found = false;
//
//	    while (true) {
//
//	        List<WebElement> dates = driver.findElements(
//	                By.xpath("//div[contains(@class,'day')]")
//	        );
//
//	        for (WebElement date : dates) {
//
//	            String text = date.getText();
//
//	            if (text.contains(day)) {
//	                date.click();
//	                found = true;
//	                return;
//	            }
//	        }
//
//	        if (found) break;
//
//	        driver.findElement(
//	                By.xpath("//button[contains(@aria-label,'Next')]")
//	        ).click();
//	    }
//
//	    if (!found) {
//	        throw new RuntimeException("Date not found: " + monthYear + " " + day);
//	    }
//	}
	
	

//	public void selectDatesFromExcel(String checkIn, String checkOut) {
//
//		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
//				.appendPattern("[dd/MMM/yy][dd-MMM-yyyy][dd/MMM/yyyy]").toFormatter();
//
//		LocalDate inDate = LocalDate.parse(checkIn.trim(), formatter);
//		LocalDate outDate = LocalDate.parse(checkOut.trim(), formatter);
//
//		String inDay = String.valueOf(inDate.getDayOfMonth());
//		String inMonthYear = formatMonthYear(inDate);
//
//		String outDay = String.valueOf(outDate.getDayOfMonth());
//		String outMonthYear = formatMonthYear(outDate);
//
//		openCalendar();
//		selectDate(inMonthYear, inDay);
//		selectDate(outMonthYear, outDay);
//	}

	// helper method
//	private String formatMonthYear(LocalDate date) {
//		String month = date.getMonth().name().substring(0, 1) + date.getMonth().name().substring(1).toLowerCase();
//
//		return month + " " + date.getYear();
//	}

	// ─── Rooms & Guests Business Logic ────────────────────────────────────────

	/**
	 * Main method called from step definition. Opens the panel, sets Rooms / Adults
	 * / Children, then closes.
	 *
	 * @param targetRooms    number from Excel col 3 e.g. 1
	 * @param targetAdults   number from Excel col 4 e.g. 2
	 * @param targetChildren number from Excel col 5 e.g. 0
	 */
//	public void selectRoomsAndGuests(String targetRooms, String targetAdults, String targetChildren) {
//		// Step 1 — open the Rooms & Guests panel
//		AllUtilityFunctions.waitForClickable(driver, roomsGuestsField, 10);
//		AllUtilityFunctions.click(roomsGuestsField);
//
//		// Step 2 — wait for panel to load
//		AllUtilityFunctions.waitForVisibility(driver, roomsIncBtn, 10);
//
//		// Step 3 — set each counter to exact Excel value
//		setCounter(roomsIncBtn, roomsDecBtn, roomsCountInput, roomsCountSpan, targetRooms);
//		setCounter(adultsIncBtn, adultsDecBtn, adultsCountInput, adultsCountSpan, targetAdults);
//		setCounter(childrenIncBtn, childrenDecBtn, childrenCountInput, childrenCountSpan, targetChildren);
//
//		// Step 4 — close panel by clicking outside (or Done button if present)
//		closePanelIfDoneExists();
//	}

	/**
	 * Reads the current counter value (works for both <input readonly> and <span>),
	 * then clicks + or - until it matches the target.
	 */
//	private void setCounter(WebElement incBtn, WebElement decBtn, WebElement countInput, WebElement countSpan,
//			int target) {
//
//		for (int attempt = 0; attempt < 20; attempt++) {
//			int current = readCount(countInput, countSpan);
//
//			if (current == target) {
//				AllUtilityFunctions.log("Counter already at target: " + target);
//				break;
//			}
//
//			if (current < target) {
//				AllUtilityFunctions.click(incBtn);
//				AllUtilityFunctions.log("Clicked + | was: " + current + " | target: " + target);
//			} else {
//				AllUtilityFunctions.click(decBtn);
//				AllUtilityFunctions.log("Clicked - | was: " + current + " | target: " + target);
//			}
//		}
//	}
//
//	/**
//	 * Reads the count from <input readonly value="1"> or <span>1</span>. Tries
//	 * input first, falls back to span.
//	 */
//	private int readCount(WebElement countInput, WebElement countSpan) {
//		try {
//			String val = countInput.getAttribute("value");
//			if (val != null && !val.trim().isEmpty()) {
//				return Integer.parseInt(val.trim());
//			}
//		} catch (Exception ignored) {
//		}
//
//		try {
//			String text = countSpan.getText().trim();
//			if (!text.isEmpty()) {
//				return Integer.parseInt(text);
//			}
//		} catch (Exception ignored) {
//		}
//
//		throw new RuntimeException("Could not read counter value from input or span");
//	}
//
//	/**
//	 * Clicks the Done / Apply button if it exists. If not, clicks outside to close
//	 * the panel.
//	 */
//	private void closePanelIfDoneExists() {
//		try {
//			WebElement doneBtn = driver.findElement(By.xpath(
//					"//button[contains(text(),'Done') or contains(text(),'Apply') or contains(text(),'Update')]"));
//			AllUtilityFunctions.waitForClickable(driver, doneBtn, 5);
//			AllUtilityFunctions.click(doneBtn);
//		} catch (Exception e) {
//			// No Done button — click the destination field to close the panel
//			AllUtilityFunctions.log("No Done button found, closing panel by clicking destination field");
//			destinationField.click();
//		}
//	}

	public void clickSearch() {
		searchButton.click();
	}


//	
////	@FindBy(xpath = "//img[@title='ixigo Hotels' and @src='https://images.ixigo.com/image/upload/Header/2fa9ebf005adc1ed6473d6229aedfaa5-dwtqq.webp']")
////	private WebElement hotelPage;
//	//Destination field
//    @FindBy(xpath = "//input[@placeholder='Enter city, area or property name']")
//    private WebElement destinationField;
//    
//    //check-in
//    @FindBy(xpath = "//p[text()='Check-in']")
//    private WebElement checkInField;
//    
//    //check-out
//    @FindBy(xpath = "//p[text()='Check-out']")
//    private WebElement checkOutField;
//
////    @FindBy(xpath = "//button[.='›']")
////    private WebElement nextMonthArrow;
//
//    @FindBy(xpath = "//div[contains(@class,'DayPicker-Caption')]//div")
//    private List<WebElement> calendarHeaders;
//
//    @FindBy(xpath = "//p[text()='Rooms & Guests']")
//    private WebElement roomAndGuest;
//
//    @FindBy(xpath = "//div[text()='Search']")
//    private WebElement searchBtn;
//
//    @FindBy(xpath = "//p[@data-testid='room-increment']")
//    private WebElement roomIncrement;
//
//    @FindBy(xpath = "//p[@data-testid='room-decrement']")
//    private WebElement roomDecrement;
//
//    @FindBy(xpath = "//p[@data-testid='adult-increment']")
//    private WebElement adultIncrement;
//
//    @FindBy(xpath = "//p[@data-testid='adult-decrement']")
//    private WebElement adultDecrement;
//
//    @FindBy(xpath = "//p[@data-testid='counter-increment-children']")
//    private WebElement childrenIncrement;
//
//    @FindBy(xpath = "//p[@data-testid='counter-decrement-children']")
//    private WebElement childrenDecrement;
//
//    // ── Date Formatters ─────────────────────
//
//    private static final DateTimeFormatter DATE_FORMAT =
//            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
//
//    private static final DateTimeFormatter MONTH_FORMAT =
//            DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
//
//    // ── Business Methods ─────────────────────────────
//
//    
////    //click hotel
////    public void clickHotel() {
////        hotelPage.click();
////    }
//    
//    // Enter destination
//    public void enterDestination(String destination) {
//        destinationField.click();
//        destinationField.sendKeys(destination);
//    }
//
//    // Select dates
//    public void selectDates(int checkInOffset, int checkOutOffset) throws InterruptedException {
//
//        LocalDate checkInDate = LocalDate.now().plusDays(checkInOffset);
//        LocalDate checkOutDate = LocalDate.now().plusDays(checkOutOffset);
//
//        checkInField.click();
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
//        String targetMonth = targetDate.format(MONTH_FORMAT);
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
//    // Click specific date
//    private void clickDate(LocalDate date) throws InterruptedException {
//        String formattedDate = date.format(DATE_FORMAT);
//
//        By dateLocator = By.xpath("//*[@aria-label='" + formattedDate + "']");
//        Thread.sleep(1000); // wait for calendar
//        driver.findElement(dateLocator).click();
//    }
//
//    // Open guest section
//    public void openGuestSection() {
//        roomAndGuest.click();
//    }
//
//    // Room methods
//    public void increaseRoom(int count) {
//        for (int i = 1; i < count; i++) {
//            roomIncrement.click();
//        }
//    }
//
//    public void decreaseRoom(int count) {
//        for (int i = 0; i < count; i++) {
//            roomDecrement.click();
//        }
//    }
//
//    // Adult methods
//    public void increaseAdult(int count) {
//        for (int i = 1; i < count; i++) {
//            adultIncrement.click();
//        }
//    }
//
//    public void decreaseAdult(int count) {
//        for (int i = 0; i < count; i++) {
//            adultDecrement.click();
//        }
//    }
//
//    // Children methods
//    public void increaseChildren(int count) {
//        for (int i = 0; i < count; i++) {
//            childrenIncrement.click();
//        }
//    }
//
//    public void decreaseChildren(int count) {
//        for (int i = 0; i < count; i++) {
//            childrenDecrement.click();
//        }
//    }

// Click search

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

	private By arrow = By.xpath("//button[text()='›']");

	public void clickCheckInDate(String month, String dates) {

	    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

	    // Open calendar
	    wait.until(ExpectedConditions.elementToBeClickable(checkInField)).click();

	    for (int i = 0; i < 12; i++) {

	        try {
	            // Try to find date in current view
	        	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//abbr[contains(@aria-label,'"+month+" "+dates+", 2026')]"))).click();
	        	return;
	        	
	        } catch (Exception e) {
	            // Date not found → move to next month
	        }

	        // Click next arrow only if not found
	        wait.until(ExpectedConditions.elementToBeClickable(arrow)).click();
	    }
	}
	
	// Rooms & Guests trigger input
		@FindBy(xpath = "//input[@placeholder='Rooms & Guests']")
		WebElement roomsGuestsField;
	
	// ── Rooms row ──
		
		private By roomsDecBtn=By.xpath("//p[@data-testid='room-decrement']");
		private By roomsIncBtn=By.xpath("//p[@data-testid='room-increment']");
		private By adultsDecBtn=By.xpath("//p[@data-testid='adult-decrement']");
		private By adultsIncBtn=By.xpath("//p[@data-testid='adult-increment']");
		private By childrenDecBtn=By.xpath("//p[@data-testid='counter-decrement-children']");
		private By childrenIncBtn=By.xpath("//p[@data-testid='counter-increment-children']");
		
		@FindBy(xpath = "//button[@data-testid='search-hotels']")
		WebElement searchButton;

	public void booking(String roomcount,String adultcount,String childcount) {
		 WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		 wait.until(ExpectedConditions.elementToBeClickable(roomsGuestsField)).click();

		int room=Integer.parseInt(roomcount);
		int adult=Integer.parseInt(adultcount);
		int child=Integer.parseInt(childcount);
		int count=0;
		if(room>1 && adult>1) {
			for(int i=0;i<room-1;i++) {
				wait.until(ExpectedConditions.presenceOfElementLocated(roomsIncBtn)).click();
			}
			for(int i=0;i<adult-2;i++) {
//				if(count==0) {
//				wait.until(ExpectedConditions.presenceOfElementLocated(adultsDecBtn)).click();
//				}
				wait.until(ExpectedConditions.presenceOfElementLocated(adultsIncBtn)).click();
			 //count++;
			}
			if(child>0) {
			for(int i=0;i<child;i++) {
				wait.until(ExpectedConditions.presenceOfElementLocated(childrenIncBtn)).click();
			}
			}
		}
		
		
	}
	}