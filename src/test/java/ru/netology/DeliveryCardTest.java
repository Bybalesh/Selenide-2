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
    public void ShouldBeSuccessCompleted(){
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Во");
        $(byText("Волгоград")).click();
        $("[data-test-id=date] [value]").click();
        LocalDate dateDefault = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusDays(30);
        String stringToSearch = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        if (dateOfMeeting.getMonthValue()>dateDefault.getMonthValue()|dateOfMeeting.getYear()>dateDefault.getYear()){
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(dayToSearch)).click();
        $("[data-test-id='name'] input").setValue("Петров Петр Петрович");
        $("[data-test-id='phone'] input").setValue("+78567324855");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
            .shouldBe(Condition.visible, Duration.ofSeconds(15));
            .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

}
