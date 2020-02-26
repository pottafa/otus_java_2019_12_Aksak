import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.ATM;
import ru.otus.homework.atm.Department;
import ru.otus.homework.atm.exceptions.AtmException;
import ru.otus.homework.atm.exceptions.DepartamentException;
import ru.otus.homework.atm.operations.AtmOperation;
import ru.otus.homework.atm.operations.GetBalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllAtmBalance {

    Department department;
    ATM atm1;
    ATM atm2;
    ATM atm3;
    ATM atm4;
    int atm1Balance;
    int atm2Balance;
    int atm3Balance;
    int atm4Balance;

    @BeforeEach
    void setUp() throws DepartamentException, AtmException {
        department = new Department();
        atm1 = department.getAtm();
        atm2 = department.getAtm();
        atm3 = department.getAtm();
        atm4 = department.getAtm();

        AtmOperation getBalance = new GetBalance();
        atm1.addOperation(getBalance);
        atm1Balance = atm1.execute();

        atm2.addOperation(getBalance);
        atm2Balance = atm2.execute();

        atm3.addOperation(getBalance);
        atm3Balance = atm3.execute();

        atm4.addOperation(getBalance);
        atm4Balance = atm4.execute();
    }

    @DisplayName("Getting balance from 4 ATM")
    @Test
    void balanceFrom4ATM() {
        int overallBalanceExpected = atm1Balance + atm2Balance + atm3Balance + atm4Balance;
        int overallBalanceResult = department.getAllAtmBalance();

        assertEquals(overallBalanceExpected, overallBalanceResult);


    }


}
