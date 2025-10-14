package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Modals {
    private SelenideElement
            containerWithModal = $(".modal-content"),
            headerModal = $("#example-modal-sizes-title-lg"),
            tableWithData = $(".table-responsive");

    public void checkModalVisible(String header) {
        $(headerModal).shouldHave(text(header));
    }

    public Modals checkSavedData(String key, String value) {
        $(tableWithData).$(byText(key)).parent().shouldHave(text(value));

        return this;
    }

    public void modalNotVisible() {
        $(containerWithModal).shouldNotBe(visible);
    }

}
