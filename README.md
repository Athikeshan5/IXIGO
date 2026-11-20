# ✈️ ixigo Website Selenium Automation Testing

## 📌 Project Overview

This project is a **Selenium WebDriver automation testing project for the ixigo travel booking platform**.

The automation testing scope covers multiple major ixigo modules:

- 🚆 Train Booking
- ✈️ Flight Booking
- 🚌 Bus Booking
- 🏨 Hotel Booking
- 🔎 Train Information & Search
- 📍 Platform Information
- 🚄 Vande Bharat Search
- 🍱 Train Food Ordering
- 📋 Booking and passenger details
- 🐞 Defect tracking
- 🔗 Requirements Traceability Matrix (RTM)

The project follows a structured software testing approach using **requirements → user stories → test scenarios → test cases → execution → defect reporting → traceability**.

---

## 🎯 Project Objectives

The main objectives of this automation project are:

1. Automate critical ixigo web application workflows.
2. Validate positive and negative test scenarios.
3. Verify search and booking-related functionality.
4. Validate user input and error handling.
5. Identify and document application defects.
6. Maintain traceability between requirements and test cases.
7. Generate meaningful test execution results.
8. Build a reusable Selenium automation framework suitable for regression testing.

---

# 🧩 Modules Covered

## 🚆 1. Train Module

The Train module covers:

- Train search
- Source and destination selection
- Travel date selection
- Train availability
- Class/filter verification
- General ticket booking
- Tatkal booking
- Passenger details
- PNR generation
- PNR status
- Invalid PNR validation
- Live train running status
- Train number validation

### Example Test Cases

| Test Case ID | Scenario | Type |
|---|---|---|
| TC_Train_01 | Train search & availability | Positive |
| TC_Train_02 | Past date restriction | Negative |
| TC_Train_03 | General booking | Positive |
| TC_Train_04 | Invalid passenger details | Negative |
| TC_Train_05 | Tatkal booking | Positive |
| TC_Train_08 | Invalid PNR | Negative |
| TC_Train_09 | Live train status | Positive |
| TC_Train_10 | Invalid train number | Negative |

---

# 🔎 2. Train Information Module

This module validates train information and supporting features.

### Covered Functionality

- Search train by train name
- Search train by train number
- Invalid train search
- Train overview
- Train availability
- Train schedule
- Coach position
- Station-based search
- Platform information
- Vande Bharat search
- Train food ordering using PNR

### Test Cases

| Test Case ID | Functionality |
|---|---|
| TC_Ixigo_01 | Search using train name |
| TC_Ixigo_02 | Search using train number |
| TC_Ixigo_03 | Invalid train search |
| TC_Ixigo_04 | Availability verification |
| TC_Ixigo_05 | Booking navigation |
| TC_Ixigo_06 | Station search |
| TC_Ixigo_07 | Platform information |
| TC_Ixigo_08 | Vande Bharat search |
| TC_Ixigo_09 | Invalid PNR for food ordering |

---

# ✈️ 3. Flight Module

The Flight module covers flight search and booking workflows.

### Covered Functionality

- One-way flight search
- Round-trip flight search
- Departure and return date selection
- Traveller selection
- Cabin class selection
- Special fares
- Flight filtering
- Airline selection
- Price range
- Time-slot filtering
- Flight details
- Fare breakup
- Baggage information
- Cancellation charges
- Passenger details
- Seat selection
- Booking management
- Cancellation/rescheduling

The Excel test plan contains **24 flight test cases**.

---

# 🚌 4. Bus Module

The Bus module validates bus search and booking functionality.

### Covered Functionality

- Source selection
- Destination selection
- Travel date
- Bus search
- Invalid source/destination validation
- Seat selection
- Already-booked seat validation
- Passenger details
- Booking flow

Example:

| Test Case | Scenario |
|---|---|
| TC_01 | Valid bus search |
| TC_02 | Same source and destination validation |
| TC_03 | Bus selection/booking flow |
| TC_04 | Passenger validation |
| TC_05 | Seat selection |
| TC_06 | Invalid seat selection |
| TC_07 | Booking validation |
| TC_08 | Negative scenario validation |

---

# 🏨 5. Hotel Module

The Hotel module covers hotel search, filtering and booking workflows.

### Covered Functionality

- Hotel destination search
- Check-in date
- Check-out date
- Guest selection
- Search results
- Price filtering
- Star-rating filtering
- Hotel selection
- Hotel details
- Room selection
- Reserve Rooms
- Guest details
- Email validation
- Phone validation
- Booking summary
- Payment page
- Total amount verification

### Example Test Cases

| Test Case ID | Scenario |
|---|---|
| TC_Hotel_001 | Hotel search validation |
| TC_Hotel_002 | Date validation |
| TC_Hotel_004 | Hotel search results |
| TC_Hotel_005 | Price filter |
| TC_Hotel_006 | Star rating filter |
| TC_Hotel_016 | Reserve Rooms navigation |
| TC_Hotel_017 | Guest field validation |
| TC_Hotel_018 | Guest details submission |
| TC_Hotel_019 | Booking summary |
| TC_Hotel_020 | Total amount validation |

---

# 🧪 Testing Approach

The project contains both **functional and negative testing**.

### Functional Testing

Functional scenarios validate whether the application performs the expected business operation.

Examples:

- Search for a valid train.
- Search for a valid flight.
- Search for a valid bus.
- Search for a hotel.
- Select a train.
- Select a seat.
- Enter passenger information.
- Navigate to booking/payment.

### Negative Testing

Negative scenarios validate how the application behaves with invalid input.

Examples:

- Invalid train number
- Invalid PNR
- Past travel date
- Invalid passenger age
- Invalid email
- Invalid phone number
- Same source and destination
- Invalid station
- Invalid hotel search input

---

# 🧰 Technology Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Selenium WebDriver | Web UI automation |
| TestNG | Test execution and assertions |
| Maven | Dependency/build management |
| Page Object Model | Framework design |
| Excel | Test data / test management |
| Git | Version control |
| GitHub | Source code repository |

---

# 🏗️ Suggested Automation Framework Structure

```text
ixigo-selenium-automation/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   └── BaseTest.java
│   │       │
│   │       ├── pages/
│   │       │   ├── LoginPage.java
│   │       │   ├── TrainPage.java
│   │       │   ├── FlightPage.java
│   │       │   ├── BusPage.java
│   │       │   └── HotelPage.java
│   │       │
│   │       └── utilities/
│   │           ├── ExcelUtility.java
│   │           ├── WaitUtility.java
│   │           └── ScreenshotUtility.java
│   │
│   └── test/
│       └── java/
│           ├── train/
│           │   └── TrainTest.java
│           ├── flight/
│           │   └── FlightTest.java
│           ├── bus/
│           │   └── BusTest.java
│           └── hotel/
│               └── HotelTest.java
│
├── testdata/
│   └── TestData.xlsx
│
├── screenshots/
│
├── reports/
│
├── pom.xml
│
├── testng.xml
│
└── README.md
```

---

# 📊 Test Execution Summary

Based on the provided test execution summary:

| Module | Total Cases | Passed | Failed | Pass % | Completion |
|---|---:|---:|---:|---:|---:|
| Train | 17 | 15 | 2 | 88% | 100% |
| Flight | 24 | 22 | 2 | 91% | 100% |
| Bus | 8 | 8 | 0 | 100% | 100% |
| Hotel | 20 | 19 | 1 | 95% | 100% |
| **Total** | **69** | **64** | **5** | **~93%** | **100%** |

> **Note:** The percentages above are based on the execution summary provided in the Excel workbook.

---

# 🐞 Defect Management

Defects identified during testing were documented with:

- Defect ID
- Module
- Defect summary
- Priority
- Assigned person
- Status

### Sample Defects

| Defect ID | Module | Priority | Status |
|---|---|---|---|
| DF_Train_001 | Live Train Status | High | In Progress |
| DF_Ixigo_06 | Train Search | High | Open |
| DF_Ixigo_07 | Train Search | High | Open |
| DF_Ixigo_10 | Train Search | High | Open |
| DF_Bus_001 | Seat Selection | High | In Progress |
| DF_Hotel_01 | Hotel Search | High | Open |
| DF_Hotel_02 | Guest Details | High | Open |

### Example Defect

**DF_Train_001 — Live Train Status**

The train selection dropdown in the Live Train Tracking page does not consistently allow users to select a matching train, even though the dropdown items are displayed.

**Priority:** High  
**Status:** In Progress

---

# 🔗 Requirements Traceability Matrix

The project maintains an RTM to establish traceability between requirements, user stories, test scenarios, test cases and defects.

Example:

```text
Requirement
    ↓
User Story
    ↓
Test Scenario
    ↓
Test Case
    ↓
Test Execution
    ↓
Defect
```

### Example

```text
REQ_Train_S1
     ↓
story1
     ↓
TS_Train_01
     ↓
TC_Train_01
TC_Train_02
     ↓
Execution Result
     ↓
Defect / Pass
```

This helps ensure that the required functionality is covered by corresponding test cases.

---

# 📋 Test Scenario Coverage

The Excel test plan contains scenarios covering:

### Train

- Train search
- Availability
- Booking
- PNR
- Live train status
- Invalid inputs

### Flight

- One-way search
- Round-trip search
- Travellers
- Cabin class
- Special fares
- Filters
- Flight details
- Passenger details
- Seat selection
- Booking management

### Bus

- Search
- Validation
- Seat selection
- Passenger details
- Booking

### Hotel

- Search
- Date validation
- Filters
- Hotel details
- Room selection
- Guest details
- Payment/summary validation

---

# 🔄 Automation Workflow

```text
Launch Browser
      ↓
Open ixigo
      ↓
Select Module
      ↓
Enter Test Data
      ↓
Perform User Action
      ↓
Validate Expected Result
      ↓
Capture Result
      ↓
Generate Test Report
      ↓
Log Defects if Test Fails
```

---

# 🧱 Framework Design

The automation framework is designed using the **Page Object Model (POM)** approach.

### Base Layer

Responsible for:

- WebDriver initialization
- Browser configuration
- Browser closing
- Common setup/teardown

### Page Layer

Contains page-specific:

- Locators
- Web elements
- Page actions
- Business operations

### Test Layer

Contains:

- Test scenarios
- Test data
- Assertions
- TestNG annotations
- Test execution

### Utility Layer

Contains reusable utilities such as:

- Excel data reading
- Explicit waits
- Screenshots
- Common WebDriver operations

---

# 🧪 TestNG

TestNG is used for:

- Test annotations
- Assertions
- Test grouping
- Test execution
- Configuration methods
- Test reports

Example execution flow:

```text
@BeforeSuite
     ↓
@BeforeTest
     ↓
@BeforeMethod
     ↓
@Test
     ↓
@AfterMethod
     ↓
@AfterTest
     ↓
@AfterSuite
```

---

# 📊 Data-Driven Testing

Test data can be maintained separately from automation code using Excel.

Example:

```text
Source       Destination       Date
Chennai      Madurai           Future Date
Chennai      Bangalore         Future Date
Delhi        Mumbai             Future Date
```

This allows the same automation script to execute with multiple sets of test data.

---

# ⏱️ Wait Strategy

The framework should primarily use explicit waits for dynamic elements.

Example:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("elementId"))
);
```

This improves synchronization when working with dynamically loaded travel-search results.

---

# 📸 Screenshot & Reporting

For failed test cases, screenshots can be captured automatically.

Example:

```text
reports/
│
├── screenshots/
│   ├── TrainSearch_Failed.png
│   ├── FlightSearch_Failed.png
│   └── HotelBooking_Failed.png
│
└── test-report.html
```

Reports can contain:

- Test case name
- Execution status
- Failure reason
- Screenshot
- Execution time
- Browser information

---

# ▶️ How to Run the Project

## 1. Clone the Repository

```bash
git clone <your-github-repository-url>
```

## 2. Navigate to the Project

```bash
cd ixigo-selenium-automation
```

## 3. Install Dependencies

```bash
mvn clean install
```

## 4. Execute Tests

```bash
mvn test
```

Or execute through TestNG:

```text
testng.xml
```

---

# 🔐 Test Data & Credentials

Do not commit real usernames, passwords, payment details or other sensitive information to GitHub.

Use:

```text
config.properties
```

or environment variables:

```text
USERNAME
PASSWORD
```

Example:

```properties
browser=chrome
url=https://www.ixigo.com/
```

---

# 🛠️ Key Automation Challenges

This project provides opportunities to handle real-world Selenium challenges such as:

- Dynamic elements
- Auto-suggestion dropdowns
- Date pickers
- Dynamic search results
- Multiple modules
- Conditional elements
- Explicit waits
- Browser synchronization
- Form validation
- Negative test scenarios
- Reusable page components

These are important skills for real-world UI automation projects.

---

# 🚀 Future Enhancements

The framework can be further enhanced with:

- [ ] Page Object Model
- [ ] Page Factory
- [ ] Data-driven testing
- [ ] Cross-browser testing
- [ ] Parallel execution
- [ ] Screenshot on failure
- [ ] Extent Reports
- [ ] Log4j logging
- [ ] Jenkins CI/CD
- [ ] GitHub Actions
- [ ] Docker execution
- [ ] Selenium Grid
- [ ] API + UI hybrid testing
- [ ] Retry failed test cases

---

# 📈 Project Metrics

| Metric | Result |
|---|---:|
| Modules Covered | 4 |
| Total Test Cases | 69 |
| Passed | 64 |
| Failed | 5 |
| Overall Pass Rate | ~93% |
| Execution Completion | 100% |
| Test Types | Functional + Negative |
| RTM | Available |
| Defect Report | Available |

---

# 👨‍💻 Skills Demonstrated

This project demonstrates practical knowledge of:

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model
- Data-driven testing
- Functional testing
- Negative testing
- Test case design
- Test scenario design
- Defect reporting
- Requirement traceability
- Regression testing
- Web UI automation
- Test execution reporting
- Git/GitHub

---

# 📂 Project Documentation

The Excel test management workbook contains:

```text
Stories_ixigo
TestScenario
Train_TestCase
Train_Info_TestCase
Bus_TestCase
Flight_TestCases
Hotel_TestCase
DefectReport
RTM
TestSummary
```

These documents provide the testing foundation for the Selenium automation project.

---

# ⚠️ Disclaimer

This project is created for **learning, portfolio and software testing practice purposes**.

It is not affiliated with or endorsed by ixigo.

---

# ⭐ Portfolio Highlights

This project demonstrates an end-to-end QA automation workflow:

```text
Requirement Analysis
        ↓
User Stories
        ↓
Test Scenario Design
        ↓
Test Case Design
        ↓
Selenium Automation
        ↓
Test Execution
        ↓
Defect Identification
        ↓
RTM
        ↓
Test Summary
```

The project is designed to demonstrate how a QA Automation Engineer can approach testing a real-world travel booking application using Selenium WebDriver.
