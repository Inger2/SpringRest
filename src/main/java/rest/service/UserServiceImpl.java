package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.config.LoanConfig;
import rest.model.Car;
import rest.model.User;
import rest.repository.CarDao;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final CarDao carDao;
    private final LoanConfig loanConfig;
    private final UserClient userClient;


    @Autowired
    public UserServiceImpl(CarDao carDao, LoanConfig loanConfig, UserClient userClient) {
        this.carDao = carDao;
        this.loanConfig = loanConfig;
        this.userClient = userClient;
    }

    public BigDecimal approveLoanById(int userId) {
        User user = getCarByKey(userId);
        if (loanApprovalConditions(user)) {
            return loanSum(user);
        } else {
            return BigDecimal.ZERO;
        }

    }

    private boolean loanApprovalConditions(User user) {
        BigDecimal retrieveUserIncome = retrieveUserIncome(user);
        return retrieveUserIncome
                .compareTo(loanConfig.getMinimalIncome()) > 0 ||
                user.getCar().getPrice()
                        .compareTo(loanConfig.getMinimalCarPrice()) > 0;

    }

    private BigDecimal loanSum(User user) {
        BigDecimal retrieveUserIncome = retrieveUserIncome(user);
        BigDecimal yearlyIncome = retrieveUserIncome.multiply(BigDecimal.valueOf(12));
        BigDecimal yearlyIncomeLoanPercentage = yearlyIncome
                .multiply(loanConfig.getMaxLoanByIncomePercentage());
        BigDecimal carPriceLoanPercentage = user.getCar().getPrice()
                .multiply(loanConfig.getMaxLoanByCarPricePercentage());

        return yearlyIncomeLoanPercentage.min(carPriceLoanPercentage);
    }

    private BigDecimal retrieveUserIncome(User user) {
        if (user.getIncome() == null) {
            return BigDecimal.valueOf(0);
        } else {
            return user.getIncome();
        }
    }


    private User getCarByKey(int userId) {
        User user = getUser(userId);
        Car car = carDao.getCarByKey(userId);
        car.setUser(user);
        user.setCar(car);
        return user;
    }

    private User getUser(int userId) {
        List<User> user;
        user = userClient.getUserById(userId);
        if (user != null && !user.isEmpty()) {
            return user.get(0);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }


}
