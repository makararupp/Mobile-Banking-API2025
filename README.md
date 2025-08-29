# Mobile Banking API 
# OverView

A secure and modular RESTful API for mobile banking, built with Spring Boot and MyBatis.
It supports customer registration, account management, and transaction processing — tailored
for integration with mobile apps and financial systems in Cambodia.

# Tech Stack 
  . Java JDK 17 <br>
  . Spring boot 3x <br>
  . Spring Web <br>
  . MyBatis <br>
  . PostgreSQL <br>
  . Lombok <br>
  . Validation <br>
  . MapStruct (optional for DTO mapping) <br>
  
# Project Structure

src/ <br>
├── main/<br>
│   ├── java/com/bank/mobileapi/ <br>
│   │   ├── controller/         # REST endpoints <br>
│   │   ├── service/            # Business logic <br>
│   │   ├── mapper/             # MyBatis mappers <br>
│   │   ├── model/              # Entities & DTOs <br>
│   │   ├── config/             # Security, Swagger, MyBatis <br>
│   │   └── MobileBankingApplication.java <br>
│   └── resources/  <br>
│       ├── mapper/             # XML SQL mappings  <br>
│       ├── application.yml     # Configurations    <br>
│       └── static/  <br>
└── test/ <br>
└── java/com/bank/mobileapi/ <br>

 # Feature Requirement
1. Account Type
   -Saving, Payroll, Credit, Debit
2. User
3. User Account
4. Transaction
   -Transfer
   - Payment(Mobile Topup, School Payment)
5. Notification(One Signal)
   application.dev(login group role)

# Setup instructions
1. Configure Database 

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/spring-mbanking-api
spring.datasource.username=user-name
spring.datasource.password=password

2. Database Design 

![](src/main/resources/static/img/Database.png)

3. Build and Run
  - ./gradlew.bat clean
  - ./gradlew.bat bootRun

# API Validation 
   1. Set up Constraint in DTO
   2. Validation DTO in Controller
   3. Handle Exception