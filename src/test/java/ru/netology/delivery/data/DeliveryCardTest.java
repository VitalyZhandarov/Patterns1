package ru.netology.delivery.data;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;

public class DeliveryCardTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    String date1 = generateDate(7, "dd.MM.yyyy");
    String date2 = generateDate(12, "dd.MM.yyyy");

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {

        $("[data-test-id=city] [placeholder='Город']").val(DataGenerator.generateCity("ru"));
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").val(date1);
        $("[data-test-id=name] [name='name']").val(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] [type='tel']").val(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__text").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date1));
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").val(date2);
        $("[role=button] .button__text").click();
        $("[data-test-id=replan-notification] .notification__content").shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date2));


    }
}
