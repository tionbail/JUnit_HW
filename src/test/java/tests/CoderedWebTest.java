package tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import tests.data.Language;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CoderedWebTest {

    @BeforeEach
    void setUp() {
        open("https://codered.su/");
    }

    @ValueSource(strings = {
            "Куртка", "Футболка", "Лонгслив"
    })
    @ParameterizedTest(name = "Найти одежду")
    @Tag("SMOKE")
    void searchResultsShouldNotBeEmpty(String cloth) {
        $("input[placeholder='поиск товара']").shouldBe(visible).setValue(cloth).pressEnter();
        $$(".col-lg-3.col-md-4.item.col-sm-6.col-xs-12").shouldBe(
                CollectionCondition.sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "Футболка, Футболка Regular",
            "Куртка, Куртка Puffed"
    })
    @ParameterizedTest(name = "Ищем одежду {0} нужная {1}")
    @Tag("REGRESS")
    void searchResultsShouldContainExpected(String wear, String expectedArrive) {
        $("input[placeholder='поиск товара']").shouldBe(visible).setValue(wear).pressEnter();
        $(".name_wrap").shouldHave(text(expectedArrive));
    }

    @EnumSource(Language.class)
    @ParameterizedTest(name = "При выборе языка {0} отображается корректный плэйсхолдер")
    @Tag("BLOCKER")
    void changeTheLanguageOnTheGear(Language language) {
        $$(".lang_icon").find(text(language.name())).click();
        $$(".head").findBy(visible).shouldHave(text(language.description));
    }


}

