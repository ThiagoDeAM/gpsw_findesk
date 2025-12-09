package findesk.findesk.web;

import findesk.findesk.infra.ExpenseRepository;
import findesk.findesk.infra.RevenueRepository;
import findesk.findesk.model.Expense;
import findesk.findesk.model.Revenue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {
    @Mock
    ExpenseRepository expenseRepository;
    @Mock
    RevenueRepository revenueRepository;
    @InjectMocks
    AppController controller;

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = List.of(
                new Expense(100.0, "Compras", "Compra mercado")
        );

        when(expenseRepository.findAll()).thenReturn(expenses);

        var result = controller.getAllExpenses();

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals("Compras", result.get(0).getCategory());
    }

    @Test
    void testAddExpense() {
        Expense expense = new Expense(50.0, "Transporte", "Gasolina");

        when(expenseRepository.save(expense)).thenReturn(expense);

        var result = controller.addExpense(expense);

        assertEquals(50.0, result.getAmount());
        assertEquals("Transporte", result.getCategory());
        assertEquals("Gasolina", result.getDescription());
    }

    @Test
    void testGetExpenseById() {
        Expense expense = new Expense(80.0, "Moradia", "Conta de luz");
        UUID id = expense.getId();

        when(expenseRepository.findById(id)).thenReturn(Optional.of(expense));

        var result = controller.getExpenseById(id);

        assertTrue(result.isPresent());
        assertEquals(80.0, result.get().getAmount());
    }

    @Test
    void testDeleteExpense() {
        UUID id = UUID.randomUUID();

        String msg = controller.deleteExpense(id);

        verify(expenseRepository, times(1)).deleteById(id);
        assertEquals("Expense deleted successfully!", msg);
    }

    @Test
    void testGetAllRevenues() {
        List<Revenue> revenues = List.of(
                new Revenue(3000.0, "Salário", "Pagamento mensal")
        );

        when(revenueRepository.findAll()).thenReturn(revenues);

        var result = controller.getAllRevenues();

        assertEquals(1, result.size());
        assertEquals(3000.0, result.get(0).getAmount());
        assertEquals("Salário", result.get(0).getSource());
    }

    @Test
    void testAddRevenue() {
        Revenue revenue = new Revenue(500.0, "Venda", "Venda online");

        when(revenueRepository.save(revenue)).thenReturn(revenue);

        var result = controller.addRevenue(revenue);

        assertEquals(500.0, result.getAmount());
        assertEquals("Venda", result.getSource());
        assertEquals("Venda online", result.getDescription());
    }

    @Test
    void testGetRevenueById() {
        Revenue revenue = new Revenue(200.0, "Bonus", "Bonus trimestral");
        UUID id = revenue.getId();

        when(revenueRepository.findById(id)).thenReturn(Optional.of(revenue));

        var result = controller.getRevenueById(id);

        assertTrue(result.isPresent());
        assertEquals(200.0, result.get().getAmount());
        assertEquals("Bonus", result.get().getSource());
    }

    @Test
    void testDeleteRevenue() {
        UUID id = UUID.randomUUID();

        String msg = controller.deleteRevenue(id);

        verify(revenueRepository, times(1)).deleteById(id);
        assertEquals("Revenue deleted successfully!", msg);
    }

    @Test
    void testGetBalance() {
        when(revenueRepository.findAll()).thenReturn(
                List.of(new Revenue(3000.0, "Salário", "Salário do mês"))
        );

        when(expenseRepository.findAll()).thenReturn(
                List.of(new Expense(1500.0, "Aluguel", "Pagamento do aluguel"))
        );

        double result = controller.getBalance();

        assertEquals(1500.0, result);
    }

    @Test
    void testGetBalanceByMonth() {
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        Revenue revenue = new Revenue(3000.0, "Salário", "Mensal");
        Expense expense = new Expense(500.0, "Mercado", "Gastos do mês");

        when(revenueRepository.findAllByDateAfterAndDateBefore(start, end))
                .thenReturn(List.of(revenue));

        when(expenseRepository.findAllByDateAfterAndDateBefore(start, end))
                .thenReturn(List.of(expense));

        double result = controller.getBalanceByMonth(1);

        assertEquals(2500.0, result);
    }
}
