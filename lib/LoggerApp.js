module.exports = (function (app_param, driver_param, fs_param, folder_param) {
    var app = app_param,
        driver = driver_param,
        fs = fs_param,
        folder = (folder_param ? (folder_param+"/") : ""),
        image_counter = 1,
        app_find_widget = app.find_widget;
    return {
        find_widget: function () {
            var result, promise_before, promise_after;
            app._hover(driver.findElement({css: "body"}));
            promise_before = driver.takeScreenshot();
            result = app_find_widget.apply(app, arguments);
            promise_after = driver.takeScreenshot();

            return result.then(function (widgets) {
                if (widgets.length > 0) {
                    promise_before.then(function (image) {
                        fs.writeFile(folder + "widget_" + image_counter + "_before.png", image, "base64");
                    });
                    promise_after.then(function (image) {
                        fs.writeFile(folder + "widget_" + image_counter++ + "_after.png", image, "base64");
                    });
                }
                return widgets;
            });
        },
        find_all_widgets: function () {
            app.find_widget = this.find_widget;
            return app.find_all_widgets();
        }
    };
});