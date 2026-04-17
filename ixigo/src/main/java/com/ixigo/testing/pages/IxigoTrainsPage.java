//package com.ixigo.testing.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//
//import com.ixigo.testing.utilities.AllUtilityFunctions;
//
//public class IxigoTrainsPage {
//
//    WebDriver driver;
//    AllUtilityFunctions util;
//
//    // Constructor
//    public IxigoTrainsPage(WebDriver driver) {
//        this.driver = driver;
//        util = new AllUtilityFunctions(driver);
//    }
//
//    // ===================== LOCATORS =====================
//
//    // --- Train Search (From / To) ---
//    // The From field on ixigo trains homepage
//    By fromField = By.xpath("(//input[@placeholder='From' or @placeholder='Enter city or station'])[1]");
//    By toField   = By.xpath("(//input[@placeholder='To'   or @placeholder='Enter city or station'])[2]");
//
//    // Search button on the main search widget
//    By searchButton = By.xpath(
//        "//button[normalize-space()='Search Trains'] | //button[contains(@class,'search') and contains(text(),'Search')]"
//    );
//
//    // --- Search By Name / Number module ---
//    // Ixigo renders these as anchor/div links in the "Quick Links" or tab section
//    By searchByNameModule = By.xpath(
//        "//a[contains(@href,'train-search')] | " +
//        "//div[contains(@class,'sc-') and .//*[contains(text(),'Name') or contains(text(),'Number')]] | " +
//        "//a[contains(text(),'Train Name')] | " +
//        "//span[contains(text(),'Name/Number')] | " +
//        "//li[contains(text(),'Search by Name')]"
//    );
//
//    By trainSearchBox = By.xpath(
//        "//input[@placeholder='Search by train name or number'] | " +
//        "//input[@placeholder='Enter train name or number'] | " +
//        "//input[contains(@placeholder,'train name') or contains(@placeholder,'Train Name')]"
//    );
//
//    // First train result card in the list
//    By firstTrainOption = By.xpath(
//        "(//div[contains(@class,'train-name')] | " +
//        "//p[contains(@class,'train-name')] | " +
//        "//div[contains(@class,'TrainName')] | " +
//        "//span[contains(@class,'train')])[1]"
//    );
//
//    // --- Availability tab inside a train detail page ---
//    By availabilityTab = By.xpath(
//        "//div[normalize-space()='AVAILABILITY'] | " +
//        "//button[normalize-space()='Availability'] | " +
//        "//span[normalize-space()='Availability']"
//    );
//
//    // Book / Proceed to Book button
//    By bookButton = By.xpath(
//        "//button[normalize-space()='Book Now'] | " +
//        "//button[normalize-space()='Book'] | " +
//        "//a[contains(text(),'Book')]"
//    );
//
//    // Something on the IRCTC / payment page to assert we got there
//    By paymentPageText = By.xpath(
//        "//*[contains(text(),'IRCTC')] | " +
//        "//*[contains(text(),'Payment')] | " +
//        "//*[contains(@class,'irctc')]"
//    );
//
//    // --- Station Search module ---
//    // Ixigo has a "Trains between Stations" or "Search by Station" quick link
//    By stationModule = By.xpath(
//        "//a[contains(@href,'trains-between-stations')] | " +
//        "//span[contains(text(),'Station')] | " +
//        "//div[contains(text(),'Search by Station')]"
//    );
//
//    By stationInput = By.xpath(
//        "//input[@placeholder='Enter the station name or code'] | " +
//        "//input[contains(@placeholder,'station')]"
//    );
//
//    By stationSearchBtn = By.xpath(
//        "//button[normalize-space()='SEARCH'] | " +
//        "//button[normalize-space()='Search']"
//    );
//
//    // --- Platform Locator module ---
//    By platformLocatorModule = By.xpath(
//        "//a[contains(@href,'platform-locator')] | " +
//        "//span[contains(text(),'Platform Locator')] | " +
//        "//div[contains(text(),'Platform Locator')]"
//    );
//
//    By trainNumberInput = By.xpath(
//        "//input[contains(@placeholder,'train name or number')] | " +
//        "//input[contains(@placeholder,'Train Number')]"
//    );
//
//    By searchPlatformBtn = By.xpath(
//        "//button[contains(normalize-space(),'SEARCH PLATFORM')] | " +
//        "//button[contains(normalize-space(),'Search Platform')]"
//    );
//
//    // --- Vande Bharat module ---
//    By vandeBharatModule = By.xpath(
//        "//a[contains(@href,'vande-bharat')] | " +
//        "//span[contains(text(),'Vande Bharat')] | " +
//        "//div[contains(text(),'Vande Bharat')]"
//    );
//
//    // After clicking Vande Bharat, the From field may use a different placeholder
//    By vandeBharatFromField = By.xpath(
//        "(//input[@placeholder='From' or contains(@placeholder,'From')])[1]"
//    );
//
//    By vandeBharatToField = By.xpath(
//        "(//input[@placeholder='To' or contains(@placeholder,'To')])[1]"
//    );
//
//    // --- Food Delivery / PNR ---
//    // Ixigo food delivery is usually at ixigo.com/trains/food-order
//    By pnrInput = By.xpath(
//        "//input[contains(@placeholder,'PNR')] | " +
//        "//input[contains(@placeholder,'pnr')]"
//    );
//
//    By pnrSearchBtn = By.xpath(
//        "//button[normalize-space()='Search'] | " +
//        "//button[contains(text(),'Track')]"
//    );
//
//    // ===================== PAGE ACTIONS =====================
//
//    /**
//     * Enter From city in the main train search widget.
//     * Ixigo clears & re-renders the input on click, so we click first.
//     */
//    public void enterFromCity(String from) {
//        util.click(fromField);
//        util.sendKeys(fromField, from);
//        util.waitForSuggestionAndSelect(from);
//    }
//
//    /**
//     * Enter To city.
//     */
//    public void enterToCity(String to) {
//        util.click(toField);
//        util.sendKeys(toField, to);
//        util.waitForSuggestionAndSelect(to);
//    }
//
//    /** Click the main Search Trains button. */
//    public void clickSearch() {
//        util.click(searchButton);
//    }
//
//    /**
//     * Navigate to the "Search By Name/Number" feature.
//     * Ixigo exposes this as a link/tab on the trains homepage.
//     */
//    public void searchTrainByName(String train) {
//        util.click(searchByNameModule);
//        util.sendKeys(trainSearchBox, train);
//        util.pressEnter(trainSearchBox);
//    }
//
//    /** Click the first train card in the results list. */
//    public void selectFirstTrain() {
//        util.click(firstTrainOption);
//    }
//
//    /** Switch to the Availability tab inside a train detail page. */
//    public void clickAvailability() {
//        util.click(availabilityTab);
//    }
//
//    /** Click the Book / Book Now button. */
//    public void clickBook() {
//        util.click(bookButton);
//    }
//
//    /** Returns true if the IRCTC / payment page element is visible. */
//    public boolean isPaymentPageDisplayed() {
//        return util.isDisplayed(paymentPageText);
//    }
//
//    /** Navigate to Station Search and submit. */
//    public void searchStation(String station) {
//        util.click(stationModule);
//        util.sendKeys(stationInput, station);
//        util.click(stationSearchBtn);
//    }
//
//    /** Navigate to Platform Locator and submit a train number. */
//    public void searchPlatform(String trainNo) {
//        util.click(platformLocatorModule);
//        util.sendKeys(trainNumberInput, trainNo);
//        util.click(searchPlatformBtn);
//    }
//
//    /** Open the Vande Bharat section. */
//    public void openVandeBharat() {
//        util.click(vandeBharatModule);
//    }
//
//    /**
//     * Enter cities specifically inside the Vande Bharat widget.
//     * After openVandeBharat() the page context changes, so we use
//     * dedicated locators for the VB from/to fields.
//     */
//    public void enterVandeBharatCities(String from, String to) {
//        util.click(vandeBharatFromField);
//        util.sendKeys(vandeBharatFromField, from);
//        util.waitForSuggestionAndSelect(from);
//
//        util.click(vandeBharatToField);
//        util.sendKeys(vandeBharatToField, to);
//        util.waitForSuggestionAndSelect(to);
//    }
//
//    /** Submit a PNR on the food delivery / order food page. */
//    public void searchFoodByPNR(String pnr) {
//        util.sendKeys(pnrInput, pnr);
//        util.click(pnrSearchBtn);
//    }
//}