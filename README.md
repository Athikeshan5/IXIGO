# 🚆 IXIGO Automation Testing Framework

A comprehensive **Selenium + Java + Cucumber (BDD)** test automation framework for the [IXIGO](https://www.ixigo.com) travel platform, covering Train, Flight, Hotel, Bus, and ancillary modules.

---

## 📌 Project Overview

This project automates end-to-end user journeys on the IXIGO web application using the **Page Object Model (POM)** design pattern, **Cucumber BDD** for human-readable test scenarios, and **Extent Reports** for rich HTML test reporting.

| Item | Detail |
|------|--------|
| **Application Under Test** | https://www.ixigo.com |
| **Language** | Java 11 |
| **Build Tool** | Maven |
| **Test Framework** | Cucumber 7.20.1 + TestNG |
| **Browser Automation** | Selenium WebDriver 4.21.0 |
| **Reporting** | ExtentReports 5.0.9 |
| **Data-Driven** | Apache POI 5.4.1 (Excel) |
| **Design Pattern** | Page Object Model + ThreadLocal |

---

## 🗂️ Project Structure

```
IXIGO-Train-module-first/
├── pom.xml                                      # Maven dependencies & build config
├── testng.xml                                   # TestNG suite configuration
├── ixigo_session/                               # Persisted browser session (cookies + localStorage)
│   ├── localStorage.json
│   └── sessionStorage.json
├── Reports/
│   └── Ixigo_extent.html                        # Generated HTML test report
└── src/
    ├── main/java/com/ixigo/testing/
    │   ├── pages/                               # Page Object classes
    │   │   ├── HomePage.java
    │   │   ├── FlightHomePage.java
    │   │   ├── FlightResultsPage.java
    │   │   ├── FlightFilterPage.java
    │   │   ├── FlightTrackerPage.java
    │   │   ├── HotelBookingPage.java
    │   │   ├── HotelSortingPage.java
    │   │   ├── HotelWithFilters.java
    │   │   ├── BusSearchPage.java
    │   │   ├── IxigoTrainNamePage.java
    │   │   ├── IxigoVandeBharatPage.java
    │   │   ├── IxigoStationPage.java
    │   │   ├── IxigoFoodPage.java
    │   │   ├── PassangerDetailsPage.java
    │   │   ├── PaymentPage.java
    │   │   └── ... (30+ page classes)
    │   └── utilities/                           # Framework utilities
    │       ├── BaseClass.java                   # ThreadLocal WebDriver holder
    │       ├── Pages.java                       # ThreadLocal page object registry
    │       ├── SessionManager.java              # Cookie-based session persistence
    │       ├── AllUtilityFunctions.java         # Reusable helper methods
    │       ├── BookingFlowHandler.java          # Train booking orchestrator
    │       ├── SeatAvailabilityHandler.java     # Seat selection logic
    │       ├── ExcelDataProvider.java           # Apache POI data reader
    │       ├── LoginPopupHandler.java           # Popup/overlay handler
    │       ├── CucumberExtentListener.java      # Extent report integration
    │       └── ReportManager.java              # Report lifecycle manager
    └── test/java/com/ixigo/testing/
        ├── featurefile/                         # Cucumber .feature files (BDD scenarios)
        │   ├── OneWayFlightTrip.feature
        │   ├── RoundTrip.feature
        │   ├── FlightTracker.feature
        │   ├── HotelBooking.feature
        │   ├── HotelFilter.feature
        │   ├── HotelSorting.feature
        │   ├── HotelCompositeFilter.feature
        │   ├── AddHotelToWishlist.feature
        │   ├── BusModule.feature
        │   ├── seatavailability.feature
        │   ├── tatkalReservation.feature
        │   ├── trainName.feature
        │   ├── vandeBharat.feature
        │   ├── station.feature
        │   ├── platform.feature
        │   ├── foodDelivery.feature
        │   ├── LiveRuningStatus.feature
        │   ├── InvalidPassenger.feature
        │   ├── NoTrainAvailable.feature
        │   └── pnrnegative.feature
        └── stepdefinition/                      # Cucumber step definitions
            ├── Hook.java                        # @Before / @After lifecycle
            ├── CommonSteps.java
            ├── FlightBookingSteps.java
            ├── FlightTrackerSteps.java
            ├── HotelBooking.java
            ├── HotelFilter.java
            ├── HotelSortingByPrice.java
            ├── BusModule.java
            └── ... (15+ step definition classes)
```

---

## 🧩 Modules Covered

### ✈️ Flight Module
- **One-Way Flight Booking** — Search → Filter (Airline) → Select Flight → Add Traveller → Seat & Meal Preference → Payment
- **Round Trip Flight Booking** — Search with return date → Select onward + return flights → Booking flow
- **Flight Tracker** — Search flight status by airline + flight number (Excel data-driven)

### 🏨 Hotel Module
- **Hotel Booking** — Search by destination + dates → Book → Guest Details → Payment
- **Hotel Filter** — Price range, star rating, user rating filters
- **Hotel Sorting** — Sort by price (low to high), user rating
- **Hotel Composite Filter** — Multiple simultaneous filter combinations
- **Hotel Wishlist** — Add hotel to wishlist and verify

### 🚌 Bus Module
- **Bus Search** — Search by source/destination/date
- **Bus Filters** — Mandatory and optional (price) filters
- **Seat Selection** — Select bus, choose seat, boarding/dropping point
- **Passenger Details** — Fill details and proceed to payment

### 🚆 Train Module
- **Seat Availability** — Check, filter, select seats, passenger details, payment
- **Tatkal Booking** — Tatkal ticket reservation flow
- **Train Name Search** — Excel-driven search by name/number
- **Vande Bharat** — Vande Bharat specific booking flow
- **Live Running Status** — Real-time train location/platform/delay tracking
- **PNR Status** — PNR validation (positive + negative scenarios)
- **Station Module** — Book train from station code
- **Platform Locator** — Find platform by train number
- **Food Delivery** — Order food by PNR number

---

## ⚙️ Prerequisites

| Requirement | Version |
|-------------|---------|
| Java JDK | 21+ |
| Maven | 3.6+ |
| Google Chrome | Latest |
| ChromeDriver | Matching Chrome version |
| Git | Any |

---

## 🚀 Setup & Execution

### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/IXIGO.git
cd IXIGO
```

### 2. Install dependencies
```bash
mvn clean install -DskipTests
```

### 3. Run all tests
```bash
mvn test
```

### 4. Run specific tags
```bash
mvn test -Dcucumber.filter.tags="@OneWay"
mvn test -Dcucumber.filter.tags="@RoundTrip"
```

### 5. View Report
Open `Reports/Ixigo_extent.html` in any browser.

---

## 🔑 Session Management

This framework uses a **smart session persistence** strategy to avoid repeated manual login:

1. On first run, it opens the browser and waits 90 seconds for manual OTP login.
2. After successful login, cookies + localStorage are serialized and saved to `ixigo_session/`.
3. On subsequent runs, saved session is restored automatically — no manual login needed.
4. Sessions auto-expire after **7 days** and trigger a fresh login.

---

## 🏗️ Key Design Decisions

### ThreadLocal Page Objects
All page object instances are stored in `ThreadLocal` via the `Pages` class, enabling **safe parallel test execution** — each thread operates on its own browser instance without interference.

### Page Object Model (POM)
Every page of the application has a dedicated Java class under `pages/`. UI locators use `@FindBy` annotations and are initialized via `PageFactory.initElements()`.

### Popup Handling Strategy
The framework handles IXIGO's CleverTap popups using a **3-strategy approach**:
1. JavaScript DOM removal of overlay elements
2. iframe switch + close button click
3. JavaScript fallback click on the target element

### Data-Driven Testing
- **Excel (Apache POI)** — Flight tracker and train name tests read test data from `.xlsx` files at runtime.
- **Cucumber Examples Tables** — Parameterized scenarios use `Scenario Outline` + `Examples` tables for multiple data sets.

---

## 📊 Test Report

Extent Reports generate a detailed HTML report at `Reports/Ixigo_extent.html` with:
- Pass/Fail status per scenario
- Step-level logs
- Screenshots on failure (captured via `CucumberExtentListener`)
- Execution timestamps

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Selenium WebDriver 4.21 | Browser automation |
| Cucumber 7.20.1 | BDD framework (Gherkin scenarios) |
| TestNG | Test runner and suite management |
| Maven | Build & dependency management |
| Apache POI 5.4.1 | Excel data reading |
| ExtentReports 5.0.9 | HTML test reporting |
| ChromeDriver | Chrome browser driver |
| Java 11 | Programming language |
| ThreadLocal | Parallel-safe page object storage |

---

## 👥 Team

Developed as part of the **V&V Automation Testing (Selenium + Java)** training program.
**Athikeshan V**
**Devottham D K**
**Harini Angel A**
**Kanimozhi T**
**Kaarthiga G**
---

## 📄 License

This project is intended for educational and training purposes only.
