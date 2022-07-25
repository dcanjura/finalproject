package com.canjura.finalproject.entity.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static User testUser = new User();
    private static final List<UserAddress> addresses = new ArrayList<>();
    private static final List<UserPayment> payments = new ArrayList<>();

    @BeforeAll
    public static void createValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void createAddressPayment(){
        UserAddress address = new UserAddress("Montrial 10 pje 5, Mejicanos", "Home");
        address.setId(1);
        addresses.add(address);
        UserPayment payment = new UserPayment("4123654769531456", "Credit Card");
        payment.setId(1);
        payments.add(payment);
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @Test
    @DisplayName("User Entity No Violations")
    public void userEntityNoViolations(){
        //Arr
        testUser = new User("Juan Perez", "+50378797879", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Entity Email Violation Test")
    public void userEntityEmailViolation(){
        //Arr
        testUser = new User("Juan Perez", "+50378797879", "juan", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = violations.iterator().next();
        Assertions.assertThat("Email is not a valid formatted email address").isEqualTo(violation.getMessage());
        Assertions.assertThat("email").isEqualTo(violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("User Entity Null Values Test")
    public void userEntityNullValuesTest(){
        //Arr
        testUser = new User(null, null, null, null, null);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(13);
    }

    @Test
    @DisplayName("User Entity Null Name Test")
    public void userEntityNullNameTest(){
        //Arr
        testUser = new User("", "+50378787878", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("User Entity Phone Violations Test")
    public void userEntityPhoneViolationsTest(){
        //Arr
        testUser = new User("Juan Perez", "", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("User Entity Email and Name Test")
    public void userEntityEmailAndNameViolationsTest(){
        //Arr
        testUser = new User("", "+50378787878", "j", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("User Entity Email and Phone Test")
    public void userEntityEmailAndPhoneViolationsTest(){
        //Arr
        testUser = new User("Juan Perez", "", "j", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(4).isEqualTo(violations.size());
    }

    @Test
    @DisplayName("User Entity Email, Name and Phone Test")
    public void userEntityEmailNameAndPhoneViolationsTest(){
        //Arr
        testUser = new User("", "", "", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("User Entity Null Addresses Test")
    public void userEntityNullAddressesViolationTest(){
        //Arr
        testUser = new User("Juan Perez", "+50378797879", "juan@gmail.com", null, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("User Entity Null Payments Test")
    public void userEntityNullPaymentsViolationTest(){
        //Arr
        testUser = new User("Juan Perez", "+50378797879", "juan@gmail.com", addresses, null);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }
}
