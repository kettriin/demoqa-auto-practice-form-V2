package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.LkForm;
import pages.components.Buttons;
import pages.components.Modals;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class FillFormTestsV2 {

    LkForm lkForm = new LkForm();
    Buttons submitButton = new Buttons();
    Modals dataModal = new Modals();

    @BeforeAll
    static void beforeMyTest() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
    }

    @Test
    void positiveFormFilling() {
        lkForm.goToPage()
                .nameSurnameFilling("Marry", "By")
                .emailFilling("mbemail@mail.ru")
                .genderSetting("Female")
                .phoneNumberFilling("9384109813")
                .birthDateSetting("01","October","1991")
                .subjectInput("b")
                .hobbiesInpit("Sports")
                .hobbiesInpit("Reading")
                .hobbiesInpit("Music")
                .uploadPicture("png.png")
                .locationDropdowns("NCR", "Noida")
                .currentAddressInput("Stretty Street 128");

        submitButton.buttonClick();

        // проверяем появилась ли модалка с введенными данными
        dataModal.checkModalVisible("Thanks for submitting the form");
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        // через проверку содержимого нужных нам ячеек в таблице
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
    }

    @Test
    void minimalDataFormFilling() {
        lkForm.goToPage()
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
                .nameSurnameFilling("Sponge", "Bob")
                .emailFilling("not a sponge mail-ru") // невалидный формат ввода почты
                .genderSetting("Male")
                .phoneNumberFilling("1104100000");

        submitButton.buttonClick();

        // проверяем что модалка с сохраненными данными не появилась
        dataModal.modalNotVisible();
    }

}
