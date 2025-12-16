package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rest.model.User;
import rest.repository.CarDao;

import java.math.BigDecimal;

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

    public BigDecimal approveLoanById(int id) {
        User user = linkUserWithCar(id);
        if (loanApprovalConditions(user)) {
            return loanSum(user);
        } else {
            return BigDecimal.ZERO;
        }

    }

    private boolean loanApprovalConditions(User user) {
        BigDecimal validatedIncome = retrieveUser(user);
        return validatedIncome.compareTo(minIncome) > 0 || user.getCar().getPrice().compareTo(minCarPrice) > 0;
    }

    private BigDecimal loanSum(User user) {
        BigDecimal validatedIncome = retrieveUser(user);
        BigDecimal yearlyIncome = validatedIncome.multiply(BigDecimal.valueOf(12));
        BigDecimal loanSum;
        if (yearlyIncome.multiply(maxIncomePercentage)
                .compareTo(user.getCar().getPrice().multiply(maxCarPricePercentage)) < 0) {
            loanSum = yearlyIncome.multiply(maxIncomePercentage);
        } else {
            loanSum = user.getCar().getPrice().multiply(maxCarPricePercentage);
        }
        return loanSum;

    }

    private BigDecimal retrieveUser(User user) {
        if (user.getIncome() == null) {
            return BigDecimal.valueOf(0);
        } else {
            return user.getIncome();
        }
    }

    private User linkUserWithCar(int id) {
        User user = getUser(id);
        user.setCar(carDao.getCar(id));
        return user;
    }

    private User getUser(int id) {
        String userUrl = url + "?id=" + id;
        User[] user = restTemplate.getForObject(userUrl, User[].class);
        assert user != null;
        return user[0];
    }


}
