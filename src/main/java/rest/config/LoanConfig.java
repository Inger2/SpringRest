package rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@ConfigurationProperties(prefix = "loan")
@Component
public class LoanConfig {
    private BigDecimal minimalIncome;
    private BigDecimal minimalCarPrice;
    private BigDecimal maxLoanByIncomePercentage;
    private BigDecimal maxLoanByCarPricePercentage;
}
