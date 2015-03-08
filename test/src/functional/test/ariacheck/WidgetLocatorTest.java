package functional.test.ariacheck;

import ariacheck.WidgetLocator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.io.File;

@RunWith(JUnit4.class)
public class WidgetLocatorTest {

    @Test
    public void test_widget_locator_should_find_css_only_tooltip () throws IOException {
        WebDriver driver = new FirefoxDriver();
        Actions actions = new Actions(driver);
        WebElement target, result_widget;

        driver.get("file://" + (new File(".").getCanonicalPath()) + "/test/fixture/sanity_check01.html");
        target = driver.findElement(By.cssSelector("#link1"));

        WidgetLocator locator = new WidgetLocator(driver, actions);
        result_widget = locator.find_widget(target);

        assertEquals("Useful message 1", result_widget.getText());

        target = driver.findElement(By.cssSelector("#link2"));
        result_widget = locator.find_widget(target);
        assertEquals("Useful message 2", result_widget.getText());

        driver.quit();
    }

}
