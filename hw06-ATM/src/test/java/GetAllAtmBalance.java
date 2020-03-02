import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.operations.AtmOperation;
import ru.otus.homework.atm.operations.GetBalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllAtmBalance {

    Department department;
    AtmImpl atmImpl1;
    AtmImpl atmImpl2;
    AtmImpl atmImpl3;
    AtmImpl atmImpl4;
    int atm1Balance;
    int atm2Balance;
    int atm3Balance;
    int atm4Balance;

    @BeforeEach
    void setUp() throws AtmException {
        department = new Department();
        atmImpl1 = department.getAtmBuilder()
                .fifty(1000)
                .hundred(100)
                .fiveHundred(100)
                .thousand(100)
                .fiveTHousand(100)
                .build();
        atmImpl2 = department.getAtmBuilder()
                .fifty(2000)
                .hundred(2000)
                .fiveHundred(2000)
                .thousand(2000)
                .fiveTHousand(2000)
                .build();
        atmImpl3 = department.getAtmBuilder()
                .hundred(1000)
                .fiveHundred(1000)
                .fiveTHousand(1000)
                .build();
        atmImpl4 = department.getAtmBuilder().build();

        AtmOperation getBalance = new GetBalance();
        atmImpl1.addOperation(getBalance);
        atm1Balance = atmImpl1.execute();

        atmImpl2.addOperation(getBalance);
        atm2Balance = atmImpl2.execute();

        atmImpl3.addOperation(getBalance);
        atm3Balance = atmImpl3.execute();

        atmImpl4.addOperation(getBalance);
        atm4Balance = atmImpl4.execute();
    }

    @DisplayName("Getting balance from 4 ATM")
    @Test
    void balanceFrom4ATM() {
        int overallBalanceExpected = atm1Balance + atm2Balance + atm3Balance + atm4Balance;
        int overallBalanceResult = department.getAllAtmBalance();
        System.out.println(overallBalanceResult);

        assertEquals(overallBalanceExpected, overallBalanceResult);


    }


}
