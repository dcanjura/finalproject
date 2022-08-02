package com.canjura.finalproject.entity;

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
     void userEntityNoViolations(){
        //Arr
        testUser = new User(1, "Juan Perez", "+50378797879", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Entity Email Violation Test")
     void userEntityEmailViolation(){
        //Arr
        testUser = new User(1, "Juan Perez", "+50378797879", "juan", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(1);
        ConstraintViolation<User> violation = violations.iterator().next();
        Assertions.assertThat(violation.getMessage()).isEqualTo("Email is not a valid formatted email address");
        Assertions.assertThat(violation.getPropertyPath().toString()).hasToString("email");
    }

    @Test
    @DisplayName("User Entity Null Values Test")
    void userEntityNullValuesTest(){
        //Arr
        testUser = new User(1, null, null, null, null, null);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(13);
    }

    @Test
    @DisplayName("User Entity Null Name Test")
     void userEntityNullNameTest(){
        //Arr
        testUser = new User(1, "", "+50378787878", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("User Entity Phone Violations Test")
     void userEntityPhoneViolationsTest(){
        //Arr
        testUser = new User(1, "Juan Perez", "", "juan@gmail.com", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("User Entity Email and Name Test")
     void userEntityEmailAndNameViolationsTest(){
        //Arr
        testUser = new User(1, "", "+50378787878", "j", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    @DisplayName("User Entity Email and Phone Test")
     void userEntityEmailAndPhoneViolationsTest(){
        //Arr
        testUser = new User(1, "Juan Perez", "", "j", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(4);
    }

    @Test
    @DisplayName("User Entity Email, Name and Phone Test")
     void userEntityEmailNameAndPhoneViolationsTest(){
        //Arr
        testUser = new User(1, "", "", "", addresses, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(6);
    }

    @Test
    @DisplayName("User Entity Null Addresses Test")
     void userEntityNullAddressesViolationTest(){
        //Arr
        testUser = new User(1, "Juan Perez", "+50378797879", "juan@gmail.com", null, payments);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }

    @Test
    @DisplayName("User Entity Null Payments Test")
     void userEntityNullPaymentsViolationTest(){
        //Arr
        testUser = new User(1, "Juan Perez", "+50378797879", "juan@gmail.com", addresses, null);

        //Act
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        //Assert
        Assertions.assertThat(violations).hasSize(2);
    }
}
