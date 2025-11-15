package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.LkForm;
import pages.components.Buttons;
import pages.components.Modals;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.Map;

@Tag("REGISTRATION")
public class FillFormTestsV2 {

    LkForm lkForm = new LkForm();
    Buttons submitButton = new Buttons();
    Modals dataModal = new Modals();

    @BeforeAll
    static void beforeMyTest() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    void positiveFormFilling() {
        step("Open form", () -> {
            lkForm.goToPage()
                    .removeAddBannersFromFooter()
                    .nameSurnameFilling("Marry", "By");
            });
        step("Filling the form", () -> {
            lkForm.emailFilling("mbemail@mail.ru")
                            .genderSetting("Female")
                            .phoneNumberFilling("9384109813")
                            .birthDateSetting("01", "October", "1991")
                            .subjectInput("b")
                            .hobbiesInpit("Sports")
                            .hobbiesInpit("Reading")
                            .hobbiesInpit("Music")
                            .uploadPicture("png.png")
                            .locationDropdowns("NCR", "Noida")
                            .currentAddressInput("Stretty Street 128");
                });
        step("Submit the form", () -> submitButton.buttonClick());

        // проверяем появилась ли модалка с введенными данными
        step("Check the modal with saved data", () -> {
            dataModal.checkModalVisible("Thanks for submitting the form");
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
                });

        // через проверку содержимого нужных нам ячеек в таблице
        step("Check the saved data", () -> {
            dataModal.checkSavedData("Student Name", "Marry By")
                    .checkSavedData("Student Email", "mbemail@mail.ru")
                    .checkSavedData("Gender", "Female")
                    .checkSavedData("Mobile", "9384109813")
                    .checkSavedData("Date of Birth", "01 November,1991")
                    .checkSavedData("Subjects", "Biology")
                    .checkSavedData("Hobbies", "Sports, Reading, Music")
                    .checkSavedData("Picture", "png.png")
                    .checkSavedData("Address", "Stretty Street 128")
                    .checkSavedData("State and City", "NCR Noida");
        });
    }

    @Test
    void minimalDataFormFilling() {
        lkForm.goToPage()
                .removeAddBannersFromFooter()
                .nameSurnameFilling("Larry", "The Snail")
                .genderSetting("Male")
                .phoneNumberFilling("9004100000");

        submitButton.buttonClick();

        // проверяем появилась ли модалка с введенными данными
        dataModal.checkModalVisible("Thanks for submitting the form");
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        // через проверку содержимого нужных нам ячеек в таблице
        dataModal.checkSavedData("Student Name", "Larry The Snail")
                .checkSavedData("Gender", "Male")
                .checkSavedData("Mobile", "9004100000");
    }

    @Test
    void invalidEmailAddress() {
        lkForm.goToPage()
                .removeAddBannersFromFooter()
                .nameSurnameFilling("Sponge", "Bob")
                .emailFilling("not a sponge mail-ru") // невалидный формат ввода почты
                .genderSetting("Male")
                .phoneNumberFilling("1104100000");

        submitButton.buttonClick();

        // проверяем что модалка с сохраненными данными не появилась
        dataModal.modalNotVisible();
        // проверяем что поле покраснело из-за ошибки введенных данных
        lkForm.invalidEmailAddress();
    }

}
