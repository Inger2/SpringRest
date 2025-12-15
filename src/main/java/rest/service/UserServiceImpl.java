package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rest.model.Car;
import rest.model.User;
import rest.repository.CarDao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    @Value("${url}")
    private String url;
    @Value("${loan.minimalIncome}")
    private BigDecimal minIncome;
    @Value("${loan.minimalCarPrice}")
    private BigDecimal minCarPrice;
    @Value("${loan.maxLoanByIncomePercentage}")
    private BigDecimal maxIncomePercentage;
    @Value("${loan.maxLoanByCarPricePercentage}")
    private BigDecimal maxCarPricePercentage;
    private CarDao carDao;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, CarDao carDao) {
        this.restTemplate = restTemplate;
        this.carDao = carDao;
    }

    public List<User> getUsers() {
        User[] user = restTemplate.getForObject(url, User[].class);
        List<Car> cars = carDao.getCars();
        assert user != null;
        for (int i = 0; i < user.length; i++) {
            user[i].setCar(cars.get(i));
            cars.get(i).setUser(user[i]);
        }
        return Arrays.asList(user);
    }

    public BigDecimal validateUser(User user) {
        if (user.getIncome() == null) {
            return BigDecimal.valueOf(0);
        } else {
            return user.getIncome();
        }
    }

    public boolean loanApprovalConditions(User user) {
        BigDecimal validatedIncome = validateUser(user);
        return validatedIncome.compareTo(minIncome) > 0 || user.getCar().getPrice().compareTo(minCarPrice) > 0;
    }

    public BigDecimal loanSum(User user) {
        BigDecimal validatedIncome = validateUser(user);
        BigDecimal yearlyIncome = validatedIncome.multiply(BigDecimal.valueOf(12));
        BigDecimal loanSum;
        if (yearlyIncome.multiply(maxIncomePercentage)
                .compareTo(user.getCar().getPrice().multiply(maxCarPricePercentage)) < 0) {
            loanSum = yearlyIncome.multiply(maxIncomePercentage);
            return loanSum;
        } else {
            loanSum = user.getCar().getPrice().multiply(maxCarPricePercentage);
            return loanSum;
        }

    }

    User getUserById(int id) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public BigDecimal approveLoan(User user) {
        if (loanApprovalConditions(user)) {
            return loanSum(user);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal approveLoanById(int id) {
        User user = getUserById(id);
        return approveLoan(user);


    }
}
