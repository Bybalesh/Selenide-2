package ru.netology

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDelivery {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }
    private String generateDate(int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimerFormatter.ofPattern(pattern));
    }

    @Test
    public void ShouldBeSuccessCompleted(){
        $("[data-test-id=city] input").setValue("Во");
        $(byText("Волгоград")).click();
        
        LocalDate defaultDay = LocalDate.now().plusDays(3);
        LocalDate planDay = LocalDate.now().plusDays(30);
        String str = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
            .shouldHave(Condition.exactText("Встреча успешно забронирована на " + str));

    }

}
