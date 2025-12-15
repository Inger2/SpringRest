package rest.service;

import rest.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    BigDecimal validateUser(User user);

    boolean loanApprovalConditions(User user);

    BigDecimal loanSum(User user);

    BigDecimal approveLoanById(int id);
}
