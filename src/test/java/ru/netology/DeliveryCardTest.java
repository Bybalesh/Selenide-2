package ru.netology

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDelivery {
   
    private String generateDate(int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimerFormatter.ofPattern(pattern));
    }

    @Test
    public void ShouldBeSuccessCityCompleted(){
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Во");
        $(byText("Волгоград")).click();
        String currentDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Петров Петр Петрович");
        $("[data-test-id='phone'] input").setValue("+78567324855");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
            .shouldBe(Condition.visible, Duration.ofSeconds(15));
            .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }
    @Test
    public void ShouldBeSuccessTimeCompleted(){
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Волгоград");
        
        LocalDate defaultDay = LocalDate.now().plusDays(AddDays);
        LocalDate planDay = LocalDate.now().plusDays(AddDays);
        String currentDate = LocalDate.now().plusDays(AddDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] [value]").click();
        if ((planDay.getYear() > defaultDay.getYear() | planDay.getMonthValue() > defaultDay.getMonthValue())) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        String seekingDay = String.valueOf(planDay.getDayOfMonth());
        $$("td.calendar__day").find(text(seekingDay)).click();
        
        $("[data-test-id='name'] input").setValue("Петров Петр Петрович");
        $("[data-test-id='phone'] input").setValue("+78567324855");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
            .shouldBe(Condition.visible, Duration.ofSeconds(15));
            .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

}
