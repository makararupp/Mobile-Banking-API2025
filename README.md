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

# create save users
  # Api end point
  create : localhost:8080/api/v1/users 
  findId : localhost:8080/api/v1/users/1
  
# Api Exception

@RestControllerAdvice <br>
public class ApiException {

    //Exception catch to field errors in field detail.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorApi<?> handleValidationException(MethodArgumentNotValidException e){
        List<Map<String, String>> errors = new ArrayList<>();
        //using foreach
        for (FieldError fieldError : e.getFieldErrors()){
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("detail", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return ErrorApi.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
    //exception update information
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorApi<?> handleServiceException(ResponseStatusException e){
        return ErrorApi.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .errors(e.getReason())
                .build();
    }
}

# Mapstruct 
 * Definition<br>
   MapStruct is a Java annotation processor that generates code for mapping between different object models (like Entity ↔ DTO) at compile time.
   It’s widely used in Spring Boot projects to reduce boilerplate code when converting between entities, DTOs, and request/response objects.

  * Add dependency <br>
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
  * 
# create Mapstruct <br>
@Mapper(componentModel = "spring") <br>
public interface UserMapstruct {<br>
User saveUserDtoToUser(SaveUserDto dto);<br>
UserDto userToUserDto(User user);<br>
}