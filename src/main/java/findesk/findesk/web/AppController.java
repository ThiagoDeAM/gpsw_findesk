package findesk.findesk.web;

import findesk.findesk.infra.ExpenseRepository;
import findesk.findesk.infra.RevenueRepository;
import findesk.findesk.model.Expense;
import findesk.findesk.model.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @GetMapping("/expenses/{id}")
    public Optional<Expense> getExpenseById(@PathVariable UUID id) {
        return expenseRepository.findById(id);
    }

    @DeleteMapping("/expenses/{id}")
    public String deleteExpense(@PathVariable UUID id) {
        expenseRepository.deleteById(id);
        return "Expense deleted successfully!";
    }

    @GetMapping("/revenues")
    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }

    @PostMapping("/revenues")
    public Revenue addRevenue(@RequestBody Revenue revenue) {
        return revenueRepository.save(revenue);
    }

    @GetMapping("/revenues/{id}")
    public Optional<Revenue> getRevenueById(@PathVariable UUID id) {
        return revenueRepository.findById(id);
    }

    @DeleteMapping("/revenues/{id}")
    public String deleteRevenue(@PathVariable UUID id) {
        revenueRepository.deleteById(id);
        return "Revenue deleted successfully!";
    }

    @GetMapping("/balance")
    public double getBalance() {
        double totalRevenue = revenueRepository.findAll()
                .stream()
                .mapToDouble(Revenue::getAmount)
                .sum();

        double totalExpense = expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return totalRevenue - totalExpense;
    }

    @GetMapping("/balance/month/{month}")
    public double getBalanceByMonth(@PathVariable int month) {
        int year = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        double totalRevenue = revenueRepository.findAllByDateAfterAndDateBefore(startDate, endDate)
                .stream()
                .mapToDouble(Revenue::getAmount)
                .sum();

        double totalExpense = expenseRepository.findAllByDateAfterAndDateBefore(startDate, endDate)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return totalRevenue - totalExpense;
    }
}
