import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class InvoicesPage {
    private final SelenideElement dateGte = $(byXpath("//input[@name='date_gte']"));
    private final SelenideElement dateLte = $(byXpath("//input[@name='date_lte']"));
    private final SelenideElement expandButton = $(byXpath("//div[@role='button']"));
    private final SelenideElement customer = $(byXpath("//div[contains(@class, 'MuiTypography-root MuiTypography-body')]"));//MuiTypography-root MuiTypography-body2 css-4prify
    private final SelenideElement addFilter = $(byXpath("//button[@aria-label='Add filter']"));
    private final SelenideElement chooseFilterType = $(byXpath("//li[@data-key='customer_id']"));
    private final SelenideElement sendCustomer = $(byXpath("//input[@role='combobox']"));
    private final SelenideElement changeAddressCheck = $(byXpath("//p[text()='Groove street']"));

    public InvoicesPage inputDateGte() {
        String formattedDate = "01012024".replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$3-$2-$1");
        dateGte.shouldBe(visible, enabled);
        dateGte.setValue("");
        executeJavaScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                dateGte,
                formattedDate
        );
        return this;
    }

    public InvoicesPage inputDateLte() {
        String formattedDate = "01082025".replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$3-$2-$1");
        dateLte.shouldBe(visible, enabled);
        dateLte.setValue("");
        executeJavaScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                dateLte,
                formattedDate
        );
        return this;
    }

    public InvoicesPage clickExpandButton() {
        expandButton.click();
        return this;
    }

    public String[] customer() {
        String actualText = customer.getText();
        boolean containsText = actualText.contains("Korey Mohr");
        assertFalse("Клиент в первых заказах не Korey Mohr", containsText);
        String[] parts = actualText.split("\\s+", 3);
        return parts;
    }

    public InvoicesPage clickAddFilter() {
        addFilter.click();
        return this;
    }

    public InvoicesPage clickChooseFilterType() {
        chooseFilterType.click();
        return this;
    }

    public InvoicesPage sendCustomer(String customerName) {
        sendCustomer.sendKeys(customerName);
        sendCustomer.sendKeys(Keys.ENTER);
        return this;
    }

    public InvoicesPage changeAddressCheck() {
        assertTrue(changeAddressCheck.isDisplayed());
        return this;
    }

    public InvoicesPage checkOldAddressRevert(String oldAddress) {
        SelenideElement check = $(byXpath("//p[text()='" + oldAddress + "']"));
        check.shouldHave(text(oldAddress));
        assertTrue(check.isDisplayed());
        return this;
    }

}
