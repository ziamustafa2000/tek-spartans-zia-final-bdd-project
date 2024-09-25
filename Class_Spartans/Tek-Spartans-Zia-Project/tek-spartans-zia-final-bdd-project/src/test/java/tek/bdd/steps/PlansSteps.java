package tek.bdd.steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import tek.bdd.pages.PlansPageObject;
import tek.bdd.utilities.SeleniumUtility;
import java.util.List;

public class PlansSteps extends SeleniumUtility {

    @Then("click on plans button")
    public void clickOnPlansButton() {
        clickOnElement(PlansPageObject.PLANS_ICON);
    }

    @Then("validate {int} row of the data is present")
    public void validate4RowOfTheDataIsPresent(Integer row) {

        String planType = getElementText(PlansPageObject.PLAN_TYPE);
        Assert.assertEquals("PLAN TYPE", planType);

        String planBasePrice = getElementText(PlansPageObject.PLAN_BASE_PRICE);
        Assert.assertEquals("PLAN BASE PRICE", planBasePrice);

        String dateCreated = getElementText(PlansPageObject.DATE_CREATED);
        Assert.assertEquals("DATE CREATED", dateCreated);

        String dateExpire = getElementText(PlansPageObject.DATE_EXPIRE);
        Assert.assertEquals("DATE EXPIRE", dateExpire);
    }

    @Then("validate Create Date is today's date in EST Time zone")
    public void validateCreateDate() {
        List<WebElement> elements = getElements(PlansPageObject.CREATE_DATE_IS_TODAY_DATE);
        String[] actualDate = new String[elements.size()];
        int i = 0;
        for(WebElement element:elements){
            actualDate[i] = element.getText();
            String expectedDate = SeleniumUtility.getCurrentDate();
            Assert.assertEquals(expectedDate,actualDate[i]);
        }
    }

    @Then("validate Date Expire is a day after EST Time Zone")
    public void validateDateExpire() {
        List<WebElement> elements = getElements(PlansPageObject.EXPIRE_DATE_IS_AFTER_TODAY_DATE);
        String[] actualDate = new String[elements.size()];
        int i = 0;
        for(WebElement element:elements) {
            actualDate[i] = element.getText();
            String expectedDate = SeleniumUtility.getExpireDate();
            Assert.assertEquals(expectedDate, actualDate[i]);
        }
    }
}