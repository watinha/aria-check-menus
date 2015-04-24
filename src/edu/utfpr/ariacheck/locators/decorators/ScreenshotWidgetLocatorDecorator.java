package edu.utfpr.ariacheck.locators.decorators;

import edu.utfpr.ariacheck.locators.Locator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotWidgetLocatorDecorator implements Locator {

    private Locator decorable;
    private TakesScreenshot takes;
    private String folder;
    private int counter = 1;
    private String image_filetype = ".jpg";

    public ScreenshotWidgetLocatorDecorator (Locator locator, TakesScreenshot takes, String folder) {
        this.decorable = locator;
        this.takes = takes;
        this.folder = folder;
    }

    public WebElement find_widget (WebElement target) {
        File screenshot = this.takes.getScreenshotAs(OutputType.FILE),
             new_file;
        WebElement widget = null;
        widget = this.decorable.find_widget(target);
        if (widget == null)
            return widget;
        new_file = this.create_file_wrapper(this.folder + (String.format("%03d", this.counter)) +
                                            "_before_widget" + this.image_filetype);
        try {
            this.copy_file_wrapper(screenshot, new_file);
        } catch (IOException io) {
            System.out.println("Error copying screenshot file to path: before widget");
        }
        screenshot = this.takes.getScreenshotAs(OutputType.FILE);
        new_file = this.create_file_wrapper(this.folder + (String.format("%03d", this.counter++)) +
                                            "_later_widget" + this.image_filetype);
        try {
            this.copy_file_wrapper(screenshot, new_file);
        } catch (IOException io) {
            System.out.println("Error copying screenshot file to path: later widget");
        }
        return widget;
    }

    public File create_file_wrapper (String name) {
        return new File(name);
    }

    public void copy_file_wrapper (File screenshot, File new_file) throws IOException {
        FileUtils.copyFile(screenshot, new_file);
    }

    public void set_image_filetype (String filetype) {
        this.image_filetype = filetype;
    }

}
