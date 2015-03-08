package ariacheck;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.ArrayList;

public class WidgetLocator {

    private WebDriver driver;
    private JavascriptExecutor executor;
    private Actions actions;

    public WidgetLocator (WebDriver driver, JavascriptExecutor executor, Actions actions) {
        this.driver = driver;
        this.actions = actions;
        this.executor = executor;
    }

    public WebElement find_widget (WebElement target) {
        List <WebElement> child_elements = this.driver.findElements(By.cssSelector("body *"));
        List <WebElement> invisibles = new ArrayList <WebElement> ();
        List <WebElement> mutation_widgets = new ArrayList <WebElement> ();
        String javascript_code = "if ( ! window.observer) {" +
                                 "    window.setInterval = function () {};" +
                                 "    for (var i = 0; i < 10000; i++) { clearTimeout(i); clearInterval(i); };" +
                                 "    window.observer = new MutationObserver(function (mutations) {" +
                                 "        mutations.forEach(function (mutation) {" +
                                 "            if (mutation.addedNodes && mutation.addedNodes.length > 0 &&" +
                                 "                mutation.addedNodes[0].nodeType === 1 &&" +
                                 "                mutation.addedNodes[0].parentElement.getAttribute(\"role\") !== \"log\") {" +
                                 "                mutation.addedNodes[0].className += \" mutation_widget\";" +
                                 "            }" +
                                 "        });" +
                                 "    });" +
                                 "    window.observer.observe(document.body, {childList: true, subtree: true});" +
                                 "}";

        this.executor.executeScript(javascript_code);

        for (WebElement child : child_elements) {
            if ( ! child.isDisplayed())
                invisibles.add(child);
        }

        this.actions.moveByOffset(-1500, -1500)
                    .moveToElement(target)
                    .build()
                    .perform();

        for (WebElement inv : invisibles) {
            if (inv.isDisplayed())
                return inv;
        }

        mutation_widgets = this.driver.findElements(By.cssSelector(".mutation_widget"));
        if (mutation_widgets.size() == 0)
            return null;
        return mutation_widgets.get(0);
    }

}
