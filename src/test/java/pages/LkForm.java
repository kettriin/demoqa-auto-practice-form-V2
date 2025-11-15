package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class LkForm {

    private final SelenideElement
            firstName = $("#firstName"),
            lastName = $("#lastName"),
            userEmail = $("#userEmail"),
            genderWrapper = $("#genterWrapper"),
            userPhoneNumber = $("#userNumber"),
            calendarInput = $("#dateOfBirthInput"),
            subjectsInput = $("#subjectsInput"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            pictureLoader = $("#uploadPicture"),
            stateInput = $("#react-select-3-input"),
            cityInput = $("#react-select-4-input"),
            stateDropDown = $("#state"),
            cityDropDown = $("#city"),
            currentAddress = $("#currentAddress");

    //методы заполнения формы

    CalendarComponent caalendarComponent = new CalendarComponent();

    public LkForm goToPage() {
        open("/automation-practice-form");

        return this;
    }

    public LkForm removeAddBannersFromFooter() {
        sleep(1500);
        executeJavaScript("document.querySelectorAll('iframe, #fixedban, footer').forEach(el => el.remove());");
        return this;
    }

    public LkForm nameSurnameFilling(String firstNameValue, String secondNameValue) {
        firstName.setValue(firstNameValue);
        lastName.setValue(secondNameValue);

        return this;
    }

    public LkForm emailFilling(String emailAddress) {
        userEmail.setValue(emailAddress);

        return this;
    }

    public LkForm genderSetting(String genderChoice) {
        genderWrapper.$(byText(genderChoice)).click();

        return this;
    }

    public LkForm phoneNumberFilling(String phoneNumber) {
        userPhoneNumber.setValue(phoneNumber);

        return this;
    }

    public LkForm birthDateSetting(String day, String month, String year) {
        calendarInput.click();
        caalendarComponent.dateSetting(day, month,year);

        return this;
    }

    public LkForm subjectInput(String subjectName) {
        subjectsInput.setValue(subjectName).pressEnter();

        return this;
    }

    public LkForm hobbiesInpit(String hobby) {
        hobbiesWrapper.$(byText(hobby)).click();

        return this;
    }

    public LkForm uploadPicture(String pictureName) {
        pictureLoader.uploadFromClasspath(pictureName);

        return this;
    }

    public LkForm locationDropdowns(String stateName, String cityName) {
        removeAddBannersFromFooter();

        stateDropDown.scrollIntoView(true).click();
        stateInput.setValue(stateName).pressEnter();

        cityDropDown.scrollIntoView(true).click();
        cityInput.setValue(cityName).pressEnter();

        return this;
    }

    public LkForm currentAddressInput(String addressValue) {
        currentAddress.setValue(addressValue);

        return this;
    }

    //метод для проверки ошибки ввода
    public LkForm invalidEmailAddress() {
        userEmail.shouldHave(Condition.cssValue("border-color", "rgb(220, 53, 69)"));

        return this;
    }
}