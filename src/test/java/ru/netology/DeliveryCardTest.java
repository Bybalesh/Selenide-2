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
    void shouldRegisterIfDateIsMoreThanOneYear() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Во");
        $(byText("Волгоград")).click();
        $("[data-test-id=date] [value]").click();
        LocalDate dateDefault = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusYears(1);
        String stringToSearch = dateOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dayToSearch = String.valueOf(dateOfMeeting.getDayOfMonth());
        if (dateOfMeeting.getMonthValue()>dateDefault.getMonthValue()|dateOfMeeting.getYear()>dateDefault.getYear()){
            $(".calendar__arrow_direction_right[data-step='12']").click();
        }
        $$("td.calendar__day").find(exactText(dayToSearch)).click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=notification]").waitUntil(Condition.visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + stringToSearch));


    }

}
