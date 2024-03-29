#### Diary entries (Dmitri Bontoft + Tom Spencer)

### 29/3/23
- started amendBooking
- decided to do TDD to start out on amendBooking
- realised that service was difficult to test because it was attracting logic
- separated a Transaction class
- created AmendBookingTransaction and BookingTransaction
    - these return the payment for the booking
- Now the bookingService has:

```java
public class BookingService {
    public Payment calculatePayment(Booking booking) {
        MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
                .orElseThrow(IllegalArgumentException::new);

        BookingTransaction bookingTransaction = new BookingTransaction(booking, movieGoer, LocalDateTime.now());
        return bookingTransaction.payment();
    }
}

```
- this moves the logic out of the service into the Transaction
- the service is delegating as opposed to doing logic
- the Transaction now contains the Booking and the Payment

Questions:
- Can we reuse the BookingForm?
- Can we reuse the same Booking screen?

### 30/3/23
- we implemented amend booking
- realised booking form didn't work perfectly
- we had to pass in the bookingId and additionalSeats as separate parameters
```java

@Controller
public class MovieController {
  @PostMapping("/amendBooking")
  public String makeAmendBooking(@ModelAttribute("amendBookingForm") AmendBookingForm amendBookingForm,
                                 @RequestParam("bookingId") Long bookingId,
                                 @RequestParam("additionalSeats") int additionalSeats) {
    Payment payment = AmendBookingForm.toPayment(amendBookingForm);

    if (bookingService.canAmendBooking(bookingId, additionalSeats)) {
      bookingService.amendBooking(bookingId, additionalSeats, payment);

      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable?bookingId=" + bookingId;
    }
  }
}
```
- added seatsNotAvailable page for situation where there is concurrent users
- realised we needed to cover the same situation for a normal booking
- refactored booking form factory to remove duplication between amendments and booking

```java
public class BookingForm {
  public static BookingForm from(Booking booking, Payment payment) {
    BookingForm bookingForm = from(booking);
    bookingForm.setAmountToPay(payment.amountPaid());
    bookingForm.setUpdatedLoyaltyPoints(payment.updatedLoyaltyPoints());
    return bookingForm;
  }

  public static BookingForm from(Booking booking) {
    BookingForm bookingForm = new BookingForm();
    bookingForm.setMovieGoerId(booking.movieGoerId());
    bookingForm.setMovieName(booking.filmName());
    bookingForm.setScheduleDate(booking.bookingTime());
    bookingForm.setScheduleId(booking.scheduleId());
    bookingForm.setNumberOfSeats(booking.numberOfSeatsBooked());
    bookingForm.setSeatPrice(booking.seatPrice());

    return bookingForm;
  }
}

```
- created issue for handling concurrency for normal booking

### 31/3/23
- resolved issues for handling concurrency
- noticed logic in the Controller and discussed possibility of making the service adaptable for API as well as MVC
- Looked ways to move more logic into the domain

```java
@Controller
public class MovieController {
  @PostMapping("/book")
  public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
    MovieProgram movieProgram = movieService.findMovieProgramBy(bookingForm.getScheduleId());
    Booking booking = BookingForm.to(bookingForm, movieProgram);
    Payment payment = BookingForm.toPayment(bookingForm);
    BookingOutcome notification = bookingService.save(booking, payment);

    if (notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable";
    }
  }
}
```
- Can we remove references to the repositories in the controller
- Is there any opportunity for refactoring?
- Are things in the right place?
- Can we add an API instead of using MVC?

### 1/4/23
- Interesting discussion about Command Query Separation (CQS)
- We wanted to move decision logic into the service layer so that it could be shared between MVC and SPA type approaches
  - this required returning a result or notification from operations
  - we spoke about whether this violated the CQS rule
  - we discussed the approach of using exceptions as an alternative
  - Martin Fowler article was helpful to show errors in a notification result
    - https://martinfowler.com/articles/replaceThrowWithNotification.html
  - we used this approach for our problem

```java
@Controller
public class MovieController {
  @PostMapping("/book")
  public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
    MovieProgram movieProgram = movieService.findMovieProgramBy(bookingForm.getScheduleId());
    Booking booking = BookingForm.to(bookingForm, movieProgram);
    Payment payment = BookingForm.toPayment(bookingForm);
    Notification notification = bookingService.payForBooking(booking, payment);

    if (notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable";
    }
  }

  @PostMapping("/amendBooking")
  public String makeAmendBooking(@ModelAttribute("amendBookingForm") AmendBookingForm amendBookingForm,
                                 @RequestParam("bookingId") Long bookingId,
                                 @RequestParam("additionalSeats") int additionalSeats) {
    Payment payment = AmendBookingForm.toPayment(amendBookingForm);
    Notification notification = bookingService.amendBooking(bookingId, additionalSeats, payment);

    if(notification.isSuccess()) {
      return "redirect:/bookings";
    } else {
      return "redirect:/seatsNotAvailable?bookingId=" + bookingId;
    }
  }
}
```
The BookingService functions:
```java
public class BookingService {
  public Notification payForBooking(Booking booking, Payment payment) {
    MovieProgram movieProgram = booking.movieProgram();
    Notification notification = new Notification();
    if (!movieProgram.seatsAvailableFor(booking.numberOfSeatsBooked())) {
      notification.addError("No seats available");
      return notification;
    }

    MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
            .orElseThrow(IllegalArgumentException::new);
    movieGoer.confirmPayment(payment);
    movieGoerRepository.save(movieGoer);
    Booking savedBooking = bookingRepository.save(booking);
    payment.associateBooking(savedBooking);
    paymentRepository.save(payment);

    return notification;
  }
  
  public Notification amendBooking(Long bookingId, int additionalSeats, Payment payment) {
    Notification notification = new Notification();

    Booking booking = bookingRepository.findById(bookingId).orElseThrow(IllegalArgumentException::new);
    boolean seatsAvailable = booking.movieProgram().seatsAvailableFor(additionalSeats);

    if (!seatsAvailable) {
      notification.addError("Seats not available");
      return notification;
    }

    booking.addSeats(additionalSeats);
    MovieGoer movieGoer = movieGoerRepository.findById(booking.movieGoerId())
            .orElseThrow(IllegalArgumentException::new);
    movieGoer.confirmPayment(payment);
    movieGoerRepository.save(movieGoer);
    Booking savedBooking = bookingRepository.save(booking);
    payment.associateBooking(savedBooking);
    paymentRepository.save(payment);

    return notification;
  }
  
}

```

In the next session we want to add custom Id types with validation:
```java
@Controller
public class MovieController {
  @GetMapping("/seatsNotAvailable")
  public String seatsNotAvailable(Model model, @RequestParam(value = "bookingId", defaultValue = "-1") Long bookingId) {
    // TODO: We could add a BookingId class which would contain validation
    if (bookingId != -1) {
      Booking booking = bookingService.findBooking(bookingId);
      BookingForm bookingForm = BookingForm.from(booking);
      model.addAttribute("bookingForm", bookingForm);
    }

    model.addAttribute("movieGoer", movieGoerView());
    return "/bookings/seatsNotAvailable";
  }
}
```

### 13/4/23 
- Discussed bounded contexts and how to split the admin and moviegoer bounded contexts
- Spring is outside in moviebooker
- There are now separate folders for moviegoer and admin bounded context
- We have decided not to use microservices or separate projects for the moment.
- There may be some duplication of classes and we haven't decided how to deal with this
- Choices include:
  - expose functionality from one bounded context to the other
  - two separate ways of reading from the database for each context

### 13/4/23 - Tom
Aim: Create page for listing movie programs for Admin
Completed admin page for listing programs

### 14/4/23
- Show movie name on list of programs
- button for add new program
  - validation: for movie, chosen time etc.
- later: list of movies
  - form for a new movie
- Ideas:
  - 15 minutes between movies
  - what is already scheduled?

### 17/4/23
- Added movie name and price to list of programs
- button for add new program (style)
- button for add new program - added get mapping

### 18/4/23 Dmitri - Tom
- Added validation
- Now add list of movies
  - form for a new movie

### 20/6/23 Dmitri - Tom
Current issue with login
- redirect goes to original inputted url or root
- We need a default redirect from root to the correct url for accessing the application
- We also need a 404 page for unsupported pages
/home/tom/.jdks/temurin-19.0.2/bin/java 
-XX:TieredStopAtLevel=1 
-Dspring.output.ansi.enabled=always 
-Dcom.sun.management.jmxremote 
-Dspring.jmx.enabled=true 
-Dspring.liveBeansView.mbeanDomain 
-Dspring.application.admin.enabled=true 
-Dmanagement.endpoints.jmx.exposure.include=* 
-javaagent:/snap/intellij-idea-ultimate/437/lib/idea_rt.jar=36273:/snap/intellij-idea-ultimate/437/bin 
-Dfile.encoding=UTF-8 
-Dsun.stdout.encoding=UTF-8 
-Dsun.stderr.encoding=UTF-8 
-classpath /home/tom/Projects/MovieBooker/target/classes:
/home/tom/.m2/repository/org/thymeleaf/extras/thymeleaf-extras-java8time/3.0.4.RELEASE/thymeleaf-extras-java8time-3.0.4.RELEASE.jar:/home/tom/.m2/repository/org/thymeleaf/thymeleaf/3.1.1.RELEASE/thymeleaf-3.1.1.RELEASE.jar:/home/tom/.m2/repository/ognl/ognl/3.3.4/ognl-3.3.4.jar:/home/tom/.m2/repository/org/javassist/javassist/3.29.0-GA/javassist-3.29.0-GA.jar:/home/tom/.m2/repository/org/attoparser/attoparser/2.0.6.RELEASE/attoparser-2.0.6.RELEASE.jar:/home/tom/.m2/repository/org/unbescape/unbescape/1.1.6.RELEASE/unbescape-1.1.6.RELEASE.jar:/home/tom/.m2/repository/org/slf4j/slf4j-api/2.0.6/slf4j-api-2.0.6.jar:/home/tom/.m2/repository/mysql/mysql-connector-java/8.0.11/mysql-connector-java-8.0.11.jar:/home/tom/.m2/repository/org/flywaydb/flyway-core/9.5.1/flyway-core-9.5.1.jar:/home/tom/.m2/repository/com/electronwill/night-config/toml/3.6.6/toml-3.6.6.jar:/home/tom/.m2/repository/com/electronwill/night-config/core/3.6.6/core-3.6.6.jar:/home/tom/.m2/repository/org/flywaydb/flyway-mysql/9.5.1/flyway-mysql-9.5.1.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-data-jpa/3.0.2/spring-boot-starter-data-jpa-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-aop/3.0.2/spring-boot-starter-aop-3.0.2.jar:/home/tom/.m2/repository/org/aspectj/aspectjweaver/1.9.19/aspectjweaver-1.9.19.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-jdbc/3.0.2/spring-boot-starter-jdbc-3.0.2.jar:/home/tom/.m2/repository/com/zaxxer/HikariCP/5.0.1/HikariCP-5.0.1.jar:/home/tom/.m2/repository/org/springframework/spring-jdbc/6.0.4/spring-jdbc-6.0.4.jar:/home/tom/.m2/repository/org/hibernate/orm/hibernate-core/6.1.6.Final/hibernate-core-6.1.6.Final.jar:/home/tom/.m2/repository/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.jar:/home/tom/.m2/repository/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.jar:/home/tom/.m2/repository/org/jboss/logging/jboss-logging/3.5.0.Final/jboss-logging-3.5.0.Final.jar:/home/tom/.m2/repository/org/hibernate/common/hibernate-commons-annotations/6.0.2.Final/hibernate-commons-annotations-6.0.2.Final.jar:/home/tom/.m2/repository/org/jboss/jandex/2.4.2.Final/jandex-2.4.2.Final.jar:/home/tom/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/home/tom/.m2/repository/org/glassfish/jaxb/jaxb-runtime/4.0.1/jaxb-runtime-4.0.1.jar:/home/tom/.m2/repository/org/glassfish/jaxb/jaxb-core/4.0.1/jaxb-core-4.0.1.jar:/home/tom/.m2/repository/org/eclipse/angus/angus-activation/1.0.0/angus-activation-1.0.0.jar:/home/tom/.m2/repository/org/glassfish/jaxb/txw2/4.0.1/txw2-4.0.1.jar:/home/tom/.m2/repository/com/sun/istack/istack-commons-runtime/4.1.1/istack-commons-runtime-4.1.1.jar:/home/tom/.m2/repository/jakarta/inject/jakarta.inject-api/2.0.0/jakarta.inject-api-2.0.0.jar:/home/tom/.m2/repository/org/antlr/antlr4-runtime/4.10.1/antlr4-runtime-4.10.1.jar:/home/tom/.m2/repository/org/springframework/data/spring-data-jpa/3.0.1/spring-data-jpa-3.0.1.jar:/home/tom/.m2/repository/org/springframework/data/spring-data-commons/3.0.1/spring-data-commons-3.0.1.jar:/home/tom/.m2/repository/org/springframework/spring-orm/6.0.4/spring-orm-6.0.4.jar:/home/tom/.m2/repository/org/springframework/spring-context/6.0.4/spring-context-6.0.4.jar:/home/tom/.m2/repository/org/springframework/spring-tx/6.0.4/spring-tx-6.0.4.jar:/home/tom/.m2/repository/org/springframework/spring-beans/6.0.4/spring-beans-6.0.4.jar:/home/tom/.m2/repository/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar:/home/tom/.m2/repository/org/springframework/spring-aspects/6.0.4/spring-aspects-6.0.4.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-thymeleaf/3.0.2/spring-boot-starter-thymeleaf-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter/3.0.2/spring-boot-starter-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-logging/3.0.2/spring-boot-starter-logging-3.0.2.jar:/home/tom/.m2/repository/ch/qos/logback/logback-classic/1.4.5/logback-classic-1.4.5.jar:/home/tom/.m2/repository/ch/qos/logback/logback-core/1.4.5/logback-core-1.4.5.jar:/home/tom/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.19.0/log4j-to-slf4j-2.19.0.jar:/home/tom/.m2/repository/org/apache/logging/log4j/log4j-api/2.19.0/log4j-api-2.19.0.jar:/home/tom/.m2/repository/org/slf4j/jul-to-slf4j/2.0.6/jul-to-slf4j-2.0.6.jar:/home/tom/.m2/repository/org/yaml/snakeyaml/1.33/snakeyaml-1.33.jar:/home/tom/.m2/repository/org/thymeleaf/thymeleaf-spring6/3.1.1.RELEASE/thymeleaf-spring6-3.1.1.RELEASE.jar:/home/tom/.m2/repository/nz/net/ultraq/thymeleaf/thymeleaf-layout-dialect/3.2.0/thymeleaf-layout-dialect-3.2.0.jar:/home/tom/.m2/repository/org/apache/groovy/groovy/4.0.7/groovy-4.0.7.jar:/home/tom/.m2/repository/nz/net/ultraq/groovy/groovy-extensions/2.1.0/groovy-extensions-2.1.0.jar:/home/tom/.m2/repository/nz/net/ultraq/thymeleaf/thymeleaf-expression-processor/3.1.0/thymeleaf-expression-processor-3.1.0.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-validation/3.0.2/spring-boot-starter-validation-3.0.2.jar:/home/tom/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/10.1.5/tomcat-embed-el-10.1.5.jar:/home/tom/.m2/repository/org/hibernate/validator/hibernate-validator/8.0.0.Final/hibernate-validator-8.0.0.Final.jar:/home/tom/.m2/repository/jakarta/validation/jakarta.validation-api/3.0.2/jakarta.validation-api-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-web/3.0.2/spring-boot-starter-web-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-json/3.0.2/spring-boot-starter-json-3.0.2.jar:/home/tom/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.14.1/jackson-databind-2.14.1.jar:/home/tom/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.14.1/jackson-core-2.14.1.jar:/home/tom/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.14.1/jackson-datatype-jdk8-2.14.1.jar:/home/tom/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.14.1/jackson-datatype-jsr310-2.14.1.jar:/home/tom/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.14.1/jackson-module-parameter-names-2.14.1.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/3.0.2/spring-boot-starter-tomcat-3.0.2.jar:/home/tom/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/10.1.5/tomcat-embed-core-10.1.5.jar:/home/tom/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/10.1.5/tomcat-embed-websocket-10.1.5.jar:/home/tom/.m2/repository/org/springframework/spring-web/6.0.4/spring-web-6.0.4.jar:/home/tom/.m2/repository/io/micrometer/micrometer-observation/1.10.3/micrometer-observation-1.10.3.jar:/home/tom/.m2/repository/io/micrometer/micrometer-commons/1.10.3/micrometer-commons-1.10.3.jar:/home/tom/.m2/repository/org/springframework/spring-webmvc/6.0.4/spring-webmvc-6.0.4.jar:/home/tom/.m2/repository/org/springframework/spring-expression/6.0.4/spring-expression-6.0.4.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-devtools/3.0.2/spring-boot-devtools-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot/3.0.2/spring-boot-3.0.2.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/3.0.2/spring-boot-autoconfigure-3.0.2.jar:/home/tom/.m2/repository/com/h2database/h2/2.1.214/h2-2.1.214.jar:/home/tom/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.0/jakarta.xml.bind-api-4.0.0.jar:/home/tom/.m2/repository/jakarta/activation/jakarta.activation-api/2.1.1/jakarta.activation-api-2.1.1.jar:/home/tom/.m2/repository/org/springframework/spring-core/6.0.4/spring-core-6.0.4.jar:/home/tom/.m2/repository/org/springframework/spring-jcl/6.0.4/spring-jcl-6.0.4.jar:/home/tom/.m2/repository/org/springframework/boot/spring-boot-starter-security/3.0.2/spring-boot-starter-security-3.0.2.jar:/home/tom/.m2/repository/org/springframework/spring-aop/6.0.4/spring-aop-6.0.4.jar:/home/tom/.m2/repository/org/springframework/security/spring-security-config/6.0.1/spring-security-config-6.0.1.jar:/home/tom/.m2/repository/org/springframework/security/spring-security-core/6.0.1/spring-security-core-6.0.1.jar:/home/tom/.m2/repository/org/springframework/security/spring-security-crypto/6.0.1/spring-security-crypto-6.0.1.jar:/home/tom/.m2/repository/org/springframework/security/spring-security-web/6.0.1/spring-security-web-6.0.1.jar:/home/tom/.m2/repository/net/bytebuddy/byte-buddy/1.12.22/byte-buddy-1.12.22.jar:/home/tom/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.14.1/jackson-annotations-2.14.1.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-sdk-java/2.20.74/aws-sdk-java-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/acm/2.20.74/acm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-json-protocol/2.20.74/aws-json-protocol-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/protocol-core/2.20.74/protocol-core-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sdk-core/2.20.74/sdk-core-2.20.74.jar:/home/tom/.m2/repository/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar:/home/tom/.m2/repository/software/amazon/awssdk/auth/2.20.74/auth-2.20.74.jar:/home/tom/.m2/repository/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar:/home/tom/.m2/repository/software/amazon/awssdk/http-client-spi/2.20.74/http-client-spi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/regions/2.20.74/regions-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/annotations/2.20.74/annotations-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/utils/2.20.74/utils-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-core/2.20.74/aws-core-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/metrics-spi/2.20.74/metrics-spi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/json-utils/2.20.74/json-utils-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/endpoints-spi/2.20.74/endpoints-spi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/apache-client/2.20.74/apache-client-2.20.74.jar:/home/tom/.m2/repository/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar:/home/tom/.m2/repository/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar:/home/tom/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar:/home/tom/.m2/repository/software/amazon/awssdk/netty-nio-client/2.20.74/netty-nio-client-2.20.74.jar:/home/tom/.m2/repository/io/netty/netty-codec-http/4.1.87.Final/netty-codec-http-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-codec-http2/4.1.87.Final/netty-codec-http2-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-codec/4.1.87.Final/netty-codec-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-transport/4.1.87.Final/netty-transport-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-resolver/4.1.87.Final/netty-resolver-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-common/4.1.87.Final/netty-common-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-buffer/4.1.87.Final/netty-buffer-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-handler/4.1.87.Final/netty-handler-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-transport-native-unix-common/4.1.87.Final/netty-transport-native-unix-common-4.1.87.Final.jar:/home/tom/.m2/repository/io/netty/netty-transport-classes-epoll/4.1.87.Final/netty-transport-classes-epoll-4.1.87.Final.jar:/home/tom/.m2/repository/software/amazon/awssdk/acmpca/2.20.74/acmpca-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/alexaforbusiness/2.20.74/alexaforbusiness-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/amplify/2.20.74/amplify-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/apigateway/2.20.74/apigateway-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/apigatewaymanagementapi/2.20.74/apigatewaymanagementapi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/apigatewayv2/2.20.74/apigatewayv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/applicationautoscaling/2.20.74/applicationautoscaling-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/applicationdiscovery/2.20.74/applicationdiscovery-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appmesh/2.20.74/appmesh-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appstream/2.20.74/appstream-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appsync/2.20.74/appsync-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/athena/2.20.74/athena-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/autoscaling/2.20.74/autoscaling-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-query-protocol/2.20.74/aws-query-protocol-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/autoscalingplans/2.20.74/autoscalingplans-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/backup/2.20.74/backup-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/batch/2.20.74/batch-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/budgets/2.20.74/budgets-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chime/2.20.74/chime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloud9/2.20.74/cloud9-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/clouddirectory/2.20.74/clouddirectory-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudformation/2.20.74/cloudformation-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudfront/2.20.74/cloudfront-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-xml-protocol/2.20.74/aws-xml-protocol-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudhsm/2.20.74/cloudhsm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudhsmv2/2.20.74/cloudhsmv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudsearch/2.20.74/cloudsearch-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudsearchdomain/2.20.74/cloudsearchdomain-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudtrail/2.20.74/cloudtrail-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudwatch/2.20.74/cloudwatch-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudwatchevents/2.20.74/cloudwatchevents-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudwatchlogs/2.20.74/cloudwatchlogs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codebuild/2.20.74/codebuild-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codecommit/2.20.74/codecommit-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codedeploy/2.20.74/codedeploy-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codepipeline/2.20.74/codepipeline-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codestar/2.20.74/codestar-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cognitoidentity/2.20.74/cognitoidentity-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cognitoidentityprovider/2.20.74/cognitoidentityprovider-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cognitosync/2.20.74/cognitosync-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/comprehend/2.20.74/comprehend-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/comprehendmedical/2.20.74/comprehendmedical-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/config/2.20.74/config-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/connect/2.20.74/connect-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/costandusagereport/2.20.74/costandusagereport-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/costexplorer/2.20.74/costexplorer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/databasemigration/2.20.74/databasemigration-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/datapipeline/2.20.74/datapipeline-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/datasync/2.20.74/datasync-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/dax/2.20.74/dax-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/devicefarm/2.20.74/devicefarm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/directconnect/2.20.74/directconnect-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/directory/2.20.74/directory-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/dlm/2.20.74/dlm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/docdb/2.20.74/docdb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/profiles/2.20.74/profiles-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/dynamodb/2.20.74/dynamodb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ec2/2.20.74/ec2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ecr/2.20.74/ecr-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ecs/2.20.74/ecs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/efs/2.20.74/efs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/eks/2.20.74/eks-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticache/2.20.74/elasticache-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticbeanstalk/2.20.74/elasticbeanstalk-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticloadbalancing/2.20.74/elasticloadbalancing-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticloadbalancingv2/2.20.74/elasticloadbalancingv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticsearch/2.20.74/elasticsearch-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elastictranscoder/2.20.74/elastictranscoder-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/emr/2.20.74/emr-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/firehose/2.20.74/firehose-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/fms/2.20.74/fms-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/fsx/2.20.74/fsx-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/gamelift/2.20.74/gamelift-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/glacier/2.20.74/glacier-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/globalaccelerator/2.20.74/globalaccelerator-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/glue/2.20.74/glue-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/greengrass/2.20.74/greengrass-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/guardduty/2.20.74/guardduty-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/health/2.20.74/health-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iam/2.20.74/iam-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/inspector/2.20.74/inspector-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iot/2.20.74/iot-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iot1clickdevices/2.20.74/iot1clickdevices-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iot1clickprojects/2.20.74/iot1clickprojects-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotanalytics/2.20.74/iotanalytics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotdataplane/2.20.74/iotdataplane-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotjobsdataplane/2.20.74/iotjobsdataplane-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kafka/2.20.74/kafka-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesis/2.20.74/kinesis-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/aws-cbor-protocol/2.20.74/aws-cbor-protocol-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/third-party-jackson-dataformat-cbor/2.20.74/third-party-jackson-dataformat-cbor-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisanalytics/2.20.74/kinesisanalytics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisanalyticsv2/2.20.74/kinesisanalyticsv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisvideo/2.20.74/kinesisvideo-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisvideoarchivedmedia/2.20.74/kinesisvideoarchivedmedia-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisvideomedia/2.20.74/kinesisvideomedia-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kms/2.20.74/kms-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lambda/2.20.74/lambda-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lexmodelbuilding/2.20.74/lexmodelbuilding-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lexruntime/2.20.74/lexruntime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/licensemanager/2.20.74/licensemanager-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lightsail/2.20.74/lightsail-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/machinelearning/2.20.74/machinelearning-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/macie/2.20.74/macie-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/managedblockchain/2.20.74/managedblockchain-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/marketplacecommerceanalytics/2.20.74/marketplacecommerceanalytics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/marketplaceentitlement/2.20.74/marketplaceentitlement-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/marketplacemetering/2.20.74/marketplacemetering-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediaconnect/2.20.74/mediaconnect-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediaconvert/2.20.74/mediaconvert-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/medialive/2.20.74/medialive-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediapackage/2.20.74/mediapackage-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediapackagevod/2.20.74/mediapackagevod-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediastore/2.20.74/mediastore-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediastoredata/2.20.74/mediastoredata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediatailor/2.20.74/mediatailor-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/migrationhub/2.20.74/migrationhub-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mobile/2.20.74/mobile-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mq/2.20.74/mq-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mturk/2.20.74/mturk-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/neptune/2.20.74/neptune-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/opsworks/2.20.74/opsworks-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/opsworkscm/2.20.74/opsworkscm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/organizations/2.20.74/organizations-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pi/2.20.74/pi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pinpoint/2.20.74/pinpoint-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pinpointemail/2.20.74/pinpointemail-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pinpointsmsvoice/2.20.74/pinpointsmsvoice-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/polly/2.20.74/polly-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pricing/2.20.74/pricing-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/quicksight/2.20.74/quicksight-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ram/2.20.74/ram-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rds/2.20.74/rds-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rdsdata/2.20.74/rdsdata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/redshift/2.20.74/redshift-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rekognition/2.20.74/rekognition-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/resourcegroups/2.20.74/resourcegroups-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/resourcegroupstaggingapi/2.20.74/resourcegroupstaggingapi-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/robomaker/2.20.74/robomaker-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53/2.20.74/route53-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53domains/2.20.74/route53domains-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53resolver/2.20.74/route53resolver-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/s3/2.20.74/s3-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/arns/2.20.74/arns-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/crt-core/2.20.74/crt-core-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/s3control/2.20.74/s3control-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/s3-transfer-manager/2.20.74/s3-transfer-manager-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemaker/2.20.74/sagemaker-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakerruntime/2.20.74/sagemakerruntime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/secretsmanager/2.20.74/secretsmanager-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/securityhub/2.20.74/securityhub-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/serverlessapplicationrepository/2.20.74/serverlessapplicationrepository-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/servicecatalog/2.20.74/servicecatalog-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/servicediscovery/2.20.74/servicediscovery-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ses/2.20.74/ses-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sfn/2.20.74/sfn-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/shield/2.20.74/shield-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/signer/2.20.74/signer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sms/2.20.74/sms-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/snowball/2.20.74/snowball-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sns/2.20.74/sns-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sqs/2.20.74/sqs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssm/2.20.74/ssm-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/storagegateway/2.20.74/storagegateway-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sts/2.20.74/sts-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/support/2.20.74/support-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/swf/2.20.74/swf-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/textract/2.20.74/textract-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/transcribe/2.20.74/transcribe-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/transcribestreaming/2.20.74/transcribestreaming-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/transfer/2.20.74/transfer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/translate/2.20.74/translate-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/waf/2.20.74/waf-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/workdocs/2.20.74/workdocs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/worklink/2.20.74/worklink-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/workmail/2.20.74/workmail-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/workspaces/2.20.74/workspaces-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/xray/2.20.74/xray-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/groundstation/2.20.74/groundstation-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotthingsgraph/2.20.74/iotthingsgraph-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotevents/2.20.74/iotevents-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ioteventsdata/2.20.74/ioteventsdata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/personalizeruntime/2.20.74/personalizeruntime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/personalize/2.20.74/personalize-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/personalizeevents/2.20.74/personalizeevents-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/servicequotas/2.20.74/servicequotas-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/applicationinsights/2.20.74/applicationinsights-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ec2instanceconnect/2.20.74/ec2instanceconnect-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/eventbridge/2.20.74/eventbridge-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lakeformation/2.20.74/lakeformation-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/forecast/2.20.74/forecast-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/forecastquery/2.20.74/forecastquery-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/qldb/2.20.74/qldb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/qldbsession/2.20.74/qldbsession-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/workmailmessageflow/2.20.74/workmailmessageflow-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codestarnotifications/2.20.74/codestarnotifications-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/savingsplans/2.20.74/savingsplans-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sso/2.20.74/sso-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssooidc/2.20.74/ssooidc-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/third-party-jackson-core/2.20.74/third-party-jackson-core-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/marketplacecatalog/2.20.74/marketplacecatalog-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sesv2/2.20.74/sesv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/dataexchange/2.20.74/dataexchange-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/migrationhubconfig/2.20.74/migrationhubconfig-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/connectparticipant/2.20.74/connectparticipant-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/wafv2/2.20.74/wafv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appconfig/2.20.74/appconfig-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotsecuretunneling/2.20.74/iotsecuretunneling-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/elasticinference/2.20.74/elasticinference-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/imagebuilder/2.20.74/imagebuilder-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/schemas/2.20.74/schemas-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/accessanalyzer/2.20.74/accessanalyzer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/computeoptimizer/2.20.74/computeoptimizer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/networkmanager/2.20.74/networkmanager-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kendra/2.20.74/kendra-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/frauddetector/2.20.74/frauddetector-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codegurureviewer/2.20.74/codegurureviewer-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codeguruprofiler/2.20.74/codeguruprofiler-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/outposts/2.20.74/outposts-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakera2iruntime/2.20.74/sagemakera2iruntime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ebs/2.20.74/ebs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisvideosignaling/2.20.74/kinesisvideosignaling-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/detective/2.20.74/detective-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codestarconnections/2.20.74/codestarconnections-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/synthetics/2.20.74/synthetics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotsitewise/2.20.74/iotsitewise-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/macie2/2.20.74/macie2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codeartifact/2.20.74/codeartifact-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/honeycode/2.20.74/honeycode-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ivs/2.20.74/ivs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/braket/2.20.74/braket-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/identitystore/2.20.74/identitystore-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appflow/2.20.74/appflow-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/redshiftdata/2.20.74/redshiftdata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssoadmin/2.20.74/ssoadmin-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/timestreamwrite/2.20.74/timestreamwrite-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/timestreamquery/2.20.74/timestreamquery-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/s3outposts/2.20.74/s3outposts-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/databrew/2.20.74/databrew-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/servicecatalogappregistry/2.20.74/servicecatalogappregistry-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/networkfirewall/2.20.74/networkfirewall-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mwaa/2.20.74/mwaa-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/devopsguru/2.20.74/devopsguru-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakerfeaturestoreruntime/2.20.74/sagemakerfeaturestoreruntime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appintegrations/2.20.74/appintegrations-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ecrpublic/2.20.74/ecrpublic-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/amplifybackend/2.20.74/amplifybackend-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/connectcontactlens/2.20.74/connectcontactlens-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lookoutvision/2.20.74/lookoutvision-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/customerprofiles/2.20.74/customerprofiles-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/emrcontainers/2.20.74/emrcontainers-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakeredge/2.20.74/sagemakeredge-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/healthlake/2.20.74/healthlake-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/auditmanager/2.20.74/auditmanager-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/amp/2.20.74/amp-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/greengrassv2/2.20.74/greengrassv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotwireless/2.20.74/iotwireless-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotfleethub/2.20.74/iotfleethub-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotdeviceadvisor/2.20.74/iotdeviceadvisor-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/location/2.20.74/location-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/wellarchitected/2.20.74/wellarchitected-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lexruntimev2/2.20.74/lexruntimev2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lexmodelsv2/2.20.74/lexmodelsv2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/fis/2.20.74/fis-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lookoutmetrics/2.20.74/lookoutmetrics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mgn/2.20.74/mgn-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/lookoutequipment/2.20.74/lookoutequipment-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/nimble/2.20.74/nimble-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/finspacedata/2.20.74/finspacedata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/finspace/2.20.74/finspace-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssmincidents/2.20.74/ssmincidents-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssmcontacts/2.20.74/ssmcontacts-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/applicationcostprofiler/2.20.74/applicationcostprofiler-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/apprunner/2.20.74/apprunner-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/proton/2.20.74/proton-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53recoveryreadiness/2.20.74/route53recoveryreadiness-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53recoverycontrolconfig/2.20.74/route53recoverycontrolconfig-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/route53recoverycluster/2.20.74/route53recoverycluster-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chimesdkmessaging/2.20.74/chimesdkmessaging-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chimesdkidentity/2.20.74/chimesdkidentity-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/snowdevicemanagement/2.20.74/snowdevicemanagement-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/memorydb/2.20.74/memorydb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/opensearch/2.20.74/opensearch-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kafkaconnect/2.20.74/kafkaconnect-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/wisdom/2.20.74/wisdom-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/voiceid/2.20.74/voiceid-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/account/2.20.74/account-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudcontrol/2.20.74/cloudcontrol-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/grafana/2.20.74/grafana-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/panorama/2.20.74/panorama-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chimesdkmeetings/2.20.74/chimesdkmeetings-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/resiliencehub/2.20.74/resiliencehub-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/migrationhubstrategy/2.20.74/migrationhubstrategy-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/drs/2.20.74/drs-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/appconfigdata/2.20.74/appconfigdata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/migrationhubrefactorspaces/2.20.74/migrationhubrefactorspaces-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/inspector2/2.20.74/inspector2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/evidently/2.20.74/evidently-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rum/2.20.74/rum-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rbin/2.20.74/rbin-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iottwinmaker/2.20.74/iottwinmaker-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/workspacesweb/2.20.74/workspacesweb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/backupgateway/2.20.74/backupgateway-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/amplifyuibuilder/2.20.74/amplifyuibuilder-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/keyspaces/2.20.74/keyspaces-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/billingconductor/2.20.74/billingconductor-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/gamesparks/2.20.74/gamesparks-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pinpointsmsvoicev2/2.20.74/pinpointsmsvoicev2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ivschat/2.20.74/ivschat-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chimesdkmediapipelines/2.20.74/chimesdkmediapipelines-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/emrserverless/2.20.74/emrserverless-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/m2/2.20.74/m2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/connectcampaigns/2.20.74/connectcampaigns-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/redshiftserverless/2.20.74/redshiftserverless-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/rolesanywhere/2.20.74/rolesanywhere-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/licensemanagerusersubscriptions/2.20.74/licensemanagerusersubscriptions-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/backupstorage/2.20.74/backupstorage-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/privatenetworks/2.20.74/privatenetworks-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/supportapp/2.20.74/supportapp-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/controltower/2.20.74/controltower-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotfleetwise/2.20.74/iotfleetwise-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/migrationhuborchestrator/2.20.74/migrationhuborchestrator-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/connectcases/2.20.74/connectcases-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/resourceexplorer2/2.20.74/resourceexplorer2-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/scheduler/2.20.74/scheduler-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ssmsap/2.20.74/ssmsap-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/chimesdkvoice/2.20.74/chimesdkvoice-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/iotroborunner/2.20.74/iotroborunner-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/oam/2.20.74/oam-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/arczonalshift/2.20.74/arczonalshift-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/simspaceweaver/2.20.74/simspaceweaver-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/securitylake/2.20.74/securitylake-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/opensearchserverless/2.20.74/opensearchserverless-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/omics/2.20.74/omics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/docdbelastic/2.20.74/docdbelastic-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakergeospatial/2.20.74/sagemakergeospatial-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/pipes/2.20.74/pipes-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/codecatalyst/2.20.74/codecatalyst-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/sagemakermetrics/2.20.74/sagemakermetrics-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kinesisvideowebrtcstorage/2.20.74/kinesisvideowebrtcstorage-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/licensemanagerlinuxsubscriptions/2.20.74/licensemanagerlinuxsubscriptions-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/kendraranking/2.20.74/kendraranking-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cleanrooms/2.20.74/cleanrooms-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/cloudtraildata/2.20.74/cloudtraildata-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/tnb/2.20.74/tnb-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/internetmonitor/2.20.74/internetmonitor-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/ivsrealtime/2.20.74/ivsrealtime-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/vpclattice/2.20.74/vpclattice-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/osis/2.20.74/osis-2.20.74.jar:/home/tom/.m2/repository/software/amazon/awssdk/mediapackagev2/2.20.74/mediapackagev2-2.20.74.jar:/home/tom/.m2/repository/org/projectlombok/lombok/1.18.28/lombok-1.18.28.jar com.tomspencerlondon.moviebooker.MovieBookerApplication

com.tomspencerlondon.moviebooker.MovieBookerApplication


### Commands for bucket with localstack

```bash
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api create-bucket --bucket movie-images-s3-bucket
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls                                           
2023-08-18 08:43:11 movie-images-s3-bucket
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api put-object --bucket movie-images-s3-bucket --key /img/godfather.jpg --body ./src/main/resources/static/img/godfather.jpg
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls
2023-08-18 08:43:11 movie-images-s3-bucket
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api list-objects --bucket movie-images-s3-bucket
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api get-object --bucket movie-images-s3-bucket --key /img/godfather.jpg godfather.jpg
➜  MovieBooker git:(main) ✗ aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api put-object --bucket movie-images-s3-bucket --key /img/star-wars.jpg --body ./src/main/resources/static/img/star-wars.jpg
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api get-object --bucket movie-images-s3-bucket --key /img/star-wars.jpg star-wars.jpg
➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api list-objects --bucket movie-images-s3-bucket          
```

### Delete object from bucket

