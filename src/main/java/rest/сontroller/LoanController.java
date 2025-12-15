package rest.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.service.UserService;

import java.math.BigDecimal;

@RestController
public class LoanController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loan")
    public BigDecimal loan(@RequestParam(value = "userId", required = false) Integer id) {
        return userService.approveLoanById(id);
    }
}
