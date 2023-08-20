package ru.netology;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    private String getFutureDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = futureDate.format(formatter);
        return formattedDate;
    }
    @Test
    public void shouldSuccessDeliveryCardRegistration() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Омск");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(getFutureDate(3));

        $("[data-test-id='name'] input").setValue("Васильев Василий");
        $("[data-test-id='phone'] input").setValue("+79168268999");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")). click();
        $("[data-test-id='notification'] .notification__content")
         .shouldBe(Condition.visible, Duration.ofSeconds(14))
         .shouldHave(Condition.exactText("Встреча успешно забронирована на " + getFutureDate(3)));

    }
    @Test    
    public void shouldSubmitRequest(){
     open("http://localhost:9999");

    $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    $("[placeholder='Город']").setValue(city);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(getFutureDate(3));
        $("[name=name]").setValue(name);
        $("[name=phone]").setValue(phone);
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(visible);
        $("input[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(getFutureDate(5));
        $(".button__text").click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).waitUntil(visible, 15000);
        $("[data-test-id=replan-notification] button.button").click();
        $(withText("Успешно")).waitUntil(visible, 15000);    
    }

    }
