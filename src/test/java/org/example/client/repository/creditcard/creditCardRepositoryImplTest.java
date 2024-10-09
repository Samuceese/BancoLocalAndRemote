package org.example.client.repository.creditcard;

import org.example.client.database.LocalDataBaseManager;
import org.example.client.repository.user.UserRepositoryImpl;
import org.example.client.repository.user.UsersRepository;
import org.example.models.TarjetaCredito;
import org.example.models.Usuario;
import org.junit.jupiter.api.*;
import org.junit.rules.Timeout;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class creditCardRepositoryImplTest {

    private static LocalDataBaseManager dataBaseManager;
    private static UsersRepository usersRepository;
    private static CreditCardRepository creditCardRepository;

    @BeforeAll
     static void setUpAll() throws SQLException {
        dataBaseManager = LocalDataBaseManager.getInstance();
        usersRepository = new UserRepositoryImpl(dataBaseManager);
        creditCardRepository = new CreditCardRepositoryImpl(dataBaseManager, usersRepository);
        dataBaseManager.connect();
        dataBaseManager.initializeDatabase();
    }

    @BeforeEach
    void setUp() {
        creditCardRepository.saveCreditCard(TarjetaCredito.builder()
                .id(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"))
                .numero("1234567890123456")
                .nombreTitular("Test")
                .clientID(1L)
                .fechaCaducidad("12/24")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        usersRepository.saveUser(Usuario.builder()
                .id(1l)
                .name("Test")
                .username("TestUsername")
                .email("test@example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @AfterEach
    void tearDown() {
        creditCardRepository.deleteCreditCard(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"));
        creditCardRepository.saveCreditCard(TarjetaCredito.builder()
                .id(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"))
                .numero("1234567890123456")
                .nombreTitular("Test")
                .clientID(1L)
                .fechaCaducidad("12/24")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        usersRepository.saveUser(Usuario.builder()
                .id(1l)
                .name("Test")
                .username("TestUsername")
                .email("test@example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

    }


    @Test
    void findAllCreditCards() {

        //act
        var result = creditCardRepository.findAllCreditCards();
        //assert
        assertEquals(1, result.size());
    }

    @Test
    void findCreditCardById() {

        //act
        var result = creditCardRepository.findCreditCardById(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"));
        //assert
        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"), result.getId()),
                ()-> assertEquals("1234567890123456", result.getNumero()),
                ()-> assertEquals("Test", result.getNombreTitular()),
                ()-> assertEquals(1L, result.getClientID()),
                ()-> assertEquals("12/24", result.getFechaCaducidad()),
                ()-> assertFalse(result.getIsDeleted())
        );
    }

    @Test
    void findCreditCardByIdNotFound() {
        //act
        var result = creditCardRepository.findCreditCardById(UUID.fromString("3a62d823-e068-4560-a464-9daa364e03d9"));
        //assert
        assertNull(result);
    }

    @Test
    void findCreditCardByNumber() {

        //act
        var result = creditCardRepository.findCreditCardByNumber("1234567890123456");
        //assert
        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"), result.getId()),
                ()-> assertEquals("1234567890123456", result.getNumero()),
                ()-> assertEquals("Test", result.getNombreTitular()),
                ()-> assertEquals(1L, result.getClientID()),
                ()-> assertEquals("12/24", result.getFechaCaducidad()),
                ()-> assertFalse(result.getIsDeleted())
        );
    }

    @Test
    void findCreditCardByNumberNotFound() {
        //act
        var result = creditCardRepository.findCreditCardByNumber("1234567234890123457");
        //assert
        assertNull(result);
    }

    @Test
    void saveCreditCard() {

        //act
        var tarjetaCredito = TarjetaCredito.builder()
                .id(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d9"))
                        .numero("1234567890123456")
                        .nombreTitular("TestSave")
                        .clientID(1L)
                        .fechaCaducidad("11/24")
                        .isDeleted(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build();
        var result = creditCardRepository.saveCreditCard(tarjetaCredito);
        //assert
        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d9"), result.getId()),
                ()-> assertEquals("1234567890123456", result.getNumero()),
                ()-> assertEquals("TestSave", result.getNombreTitular()),
                ()-> assertEquals(1L, result.getClientID()),
                ()-> assertEquals("11/24", result.getFechaCaducidad()),
                ()-> assertFalse(result.getIsDeleted())
        );
    }

    @Test
    void updateCreditCard() {

        //arrange
        TarjetaCredito tarjetaCreditoUpdated = creditCardRepository.findCreditCardById(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"));
        tarjetaCreditoUpdated.setFechaCaducidad("11/24");
        var localDateTime = LocalDateTime.now();
        tarjetaCreditoUpdated.setUpdatedAt(localDateTime);
        //act
        var result = creditCardRepository.updateCreditCard(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"),tarjetaCreditoUpdated);
        //assert
        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"), result.getId()),
                ()-> assertEquals("1234567890123456", result.getNumero()),
                ()-> assertEquals("Test", result.getNombreTitular()),
                ()-> assertEquals(1L, result.getClientID()),
                ()-> assertEquals("11/24", result.getFechaCaducidad()),
                ()-> assertEquals(localDateTime, result.getUpdatedAt()),
                ()-> assertFalse(result.getIsDeleted())
        );
    }

    @Test
    void updateCreditCardNotFound() {
        var localDateTime = LocalDateTime.now();
        TarjetaCredito tarjetaCreditoNotFound = TarjetaCredito.builder()
                .id(UUID.fromString("3a41d823-e068-4560-a464-9daa369e03d6"))
                .numero("1234567890123456")
                .nombreTitular("Test")
                .clientID(1L)
                .fechaCaducidad("12/24")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        //arrange
        tarjetaCreditoNotFound.setFechaCaducidad("11/24");
        tarjetaCreditoNotFound.setUpdatedAt(localDateTime);
        //act
        var result = creditCardRepository.updateCreditCard(UUID.fromString("3a41d823-e068-4560-a464-9daa369e03d6"),tarjetaCreditoNotFound);
        //assert
        assertNull(result);
    }

    @Test
    void deleteCreditCard() {

        //act
        var result = creditCardRepository.deleteCreditCard(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"));
        //assert
        assertTrue(result);
    }

    @Test
    void deleteCreditCardNotFound() {
        //act
        var result = creditCardRepository.deleteCreditCard(UUID.fromString("3a41d823-e068-4560-a464-9daa369e03d6"));
        //assert
        assertFalse(result);
    }

    @Test
    void deleteAllCreditCards() {

        //act
        var result = creditCardRepository.deleteAllCreditCards();
        //assert
        assertTrue(result);
    }

    @Test
    void findAllCreditCardsByUserId() {
        creditCardRepository.saveCreditCard(TarjetaCredito.builder()
                .id(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"))
                .numero("1234567890123456")
                .nombreTitular("Test")
                .clientID(1L)
                .fechaCaducidad("12/24")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        usersRepository.saveUser(Usuario.builder()
                .id(1l)
                .name("Test")
                .username("TestUsername")
                .email("test@example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        //act
        var result = creditCardRepository.findAllCreditCardsByUserId(1L);
        //assert
        assertAll(
                ()-> assertNotNull(result),
                ()-> assertEquals(1, result.size()),
                ()-> assertEquals(UUID.fromString("3a62d823-e068-4560-a464-9daa369e03d6"), result.get(0).getId()),
                ()-> assertEquals("1234567890123456", result.get(0).getNumero()),
                ()-> assertEquals("Test", result.get(0).getNombreTitular()),
                ()-> assertEquals(1L, result.get(0).getClientID()),
                ()-> assertEquals("12/24", result.get(0).getFechaCaducidad()),
                ()-> assertFalse(result.get(0).getIsDeleted())
        );;
    }
    @Test
    void findAllCreditCardsByUserIdNotFound() {
        //act
        var result = creditCardRepository.findAllCreditCardsByUserId(4L);
        //assert
        assertEquals(0, result.size());
    }
}