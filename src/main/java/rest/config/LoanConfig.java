package rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "loan")
@Component
public class LoanConfig {
    private BigDecimal minimalIncome;
    private BigDecimal minimalCarPrice;
    private BigDecimal maxLoanByIncomePercentage;
    private BigDecimal maxLoanByCarPricePercentage;

    public BigDecimal getMinimalIncome() {
        return minimalIncome;
    }


    public BigDecimal getMinimalCarPrice() {
        return minimalCarPrice;
    }

    public BigDecimal getMaxLoanByIncomePercentage() {
        return maxLoanByIncomePercentage;
    }

    public BigDecimal getMaxLoanByCarPricePercentage() {
        return maxLoanByCarPricePercentage;
    }

    public void setMinimalIncome(BigDecimal minimalIncome) {
        this.minimalIncome = minimalIncome;
    }

    public void setMaxLoanByIncomePercentage(BigDecimal maxLoanByIncomePercentage) {
        this.maxLoanByIncomePercentage = maxLoanByIncomePercentage;
    }

    public void setMinimalCarPrice(BigDecimal minimalCarPrice) {
        this.minimalCarPrice = minimalCarPrice;
    }

    public void setMaxLoanByCarPricePercentage(BigDecimal maxLoanByCarPricePercentage) {
        this.maxLoanByCarPricePercentage = maxLoanByCarPricePercentage;
    }
}
