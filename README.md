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

# How to upload images to resource 
1. Step number 1
 - Configure application.properties or appliction.yml

   file.server-path = D:\\Self Study 2025\\Rest Api Mybatis\\Thumbnail\\ <br>
   file.client-path = /files/**  <br>
   file.base-url = http://localhost:8080/file/  <br>
   file.download-url=http://localhost:8080/api/v1/files/download/ <br>

2. Step number 2
   Create file Utils Using FileDto
- Design FileDto
  recode field (name, size , url, extension,downloadUrl)
- File Service
   get from MultipleFile 

  - create file util    <br>
    @Component  <br>
    public class FileUtil {  <br>
    @Value("${file.base-url}")  <br>
    private String fileBaseUrl;  <br>

    @Value("${file.server-path}")  <br>
    private String fileServerPath;  <br>

    @Value("${file.download-url}")  <br>
    private String fileDownload;  <br>

    public FileDto upload(MultipartFile file){  <br>
    String extension = getExtension(file.getOriginalFilename()); <br>
    String name = String.format("%s.%s", UUID.randomUUID(),extension); <br>
    Long size = file.getSize(); <br>
    String url = getUrl(name); <br>

         Path path = Paths.get(fileServerPath+name);
         try {
             Files.copy(file.getInputStream(),path);
         } catch (IOException e) {
           throw  new ResponseStatusException(
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   e.getMessage());
         }
         return FileDto.builder()
                 .name(name)
                 .size(size)
                 .extension(extension)
                 .url(url)
                 .downloadUrl(fileDownload+ name)
                 .build();
    }
  
    public String getExtension(String name){ <br>
           int dotLastIndex = name.lastIndexOf("."); <br> 
    return name.substring(dotLastIndex+1); <br>
    }
    <br><br>
    public String getUrl(String name){<br>
    return fileBaseUrl + name;<br>
    }
    <br><br>
    public String getDownloadUrl(String name){<br>
    return fileDownload + name;<br>
    }
    <br><br>
    public Resource load(String name) {<br>
    Path path = Paths.get(fileServerPath + name);<br>
    return UrlResource.from(path.toUri());<br>
    }
    <br><br>
    public void delete(String name) throws IOException {  <br>
              Path path = Paths.get(fileServerPath + name); <br>
    Files.deleteIfExists(path); <br>
    }
    <br><br>
  }
# Read able Size file 

![](src/main/resources/static/img/readableSize.png)

# Find Name file Like the image <br>
![](src/main/resources/static/img/findName.png)

# step download file <br>
 - upload image after successfully copy path downloadUrl by image below: <br>
![](src/main/resources/static/img/download.png) <br>
 - Pass url to browser it's show image like this : <br>

 ![](src/main/resources/static/img/1.png)
 
# Security In memory
1. Add dependency
   implementation 'org.springframework.boot:spring-boot-starter-security'
2. Define bean FilterChain <br>

public class SecurityConfig { <br>
        //injection bean <br>
    private final PasswordEncoder passwordEncoder;

    //1. Define bean: security FilterChain . // update spring version3. like arrow function.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Disable CSRF (for APIs)
        http.csrf(token -> token.disable());

        //Configure Http mapping URL.
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/account-types/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST,"/api/v1/files/**").hasRole("EDITOR")
                .anyRequest().authenticated()
        );

        //Configure security mechanism
        http.httpBasic(httpBasic->{});

        // Make session stateless
        http.sessionManagement(session
                ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

3. Define In Memory <br>
@Bean <br>
public UserDetailsService userDetailsService(){<br>

        UserDetails admin = User.builder()
                .username("makara")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails editor = User.builder()
                .username("dara")
                .password(passwordEncoder.encode("123"))
                .roles("EDITOR")
                .build();

        return new InMemoryUserDetailsManager(user, admin, editor);
    }

}
# fetch api client request basic auth javaScript

 <!-- Client response  -->
    <script>
        data = "http://localhost:8080/api/v1/users";
        fetch(data, {
            headers: {
                'Authorization': 'Basic ZGFyYTFAZ21haWwuY29tOjk5OTk=' // encode automatically
            }
        })
            .then(rep => rep.json())
            .then(json => {
                console.log(json);
            })
            .catch(err => console.error(err));

    </script>

# register 

![](src/main/resources/static/img/register.png)

- Noted : when you register new account we need to including roles 2 numbers because it is a list <br>

public record RegisterDto(

         @NotBlank(message = "Email is required")
         @Email(message = "Email should be valid")
         String email,

        @NotBlank(message = "Password is required")
        @Size(min = 4, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmedPassword,

        @NotNull
        List<Integer> roleIds
) {

}

# Annotation Transaction

Key Benefits of @Transactional in This Context: <br>
Atomicity: It wraps multiple database operations into a single "all-or-nothing" unit of work.

Consistency: It guarantees your application data remains consistent,
preventing orphaned records or half-baked user accounts.

Automatic Rollback: It automatically rolls back the transaction if a runtime exception is thrown.
You don't have to write manual rollback logic.

Simplified Code: You write your business logic as if everything will work,
and Spring handles the complex transaction management behind the scenes.

# Login data transfer object for access basic authorization like following base64 authentication

- Step 1 :
 create AuthDto

public record AuthDto(String basicHeader) {

}

step 2 : create AuthService

public interface AuthService {

  AuthDto login(LoginDto loginDto);

}

step 3 : implement Service

    @Override
    public AuthDto login(LoginDto loginDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
            loginDto.email(),
            loginDto.password()
            );
            auth = authProvider.authenticate(auth);

        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();

        log.info("Auth :{}", customUserDetails.getUser().getEmail());
        //create following algorithm and RSA keys.
        String basicAuthString = auth.getName() + ":"+ auth.getCredentials();
        String basicAuthHeader = Base64.getEncoder().encodeToString(basicAuthString.getBytes());

        return new AuthDto(String.format("Basic %s",basicAuthHeader));
    }


step 4 : Call service to AuthController 

    @PostMapping("/login")
    public BaseApi<?> login(@RequestBody LoginDto loginDto){

        AuthDto authDto = authService.login(loginDto);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been logged in successfully!")
                .timestamp(LocalDateTime.now())
                .data(authDto)
                .build();
    }


# I just drop the image for Login access basic authorization access resource

![](src/main/resources/static/img/authLogin.png)

# Copy basic auth to resource for access like below the image :

![](src/main/resources/static/img/authorization.png)
