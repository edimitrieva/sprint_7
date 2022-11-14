package org.example.generator;

import org.example.model.Orders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderGenerate {

    public static Orders getDefaultWithColorGrey() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().plusDays(2).format(formatter);
        return new Orders(
                "Мария",
                "Иванова",
                "Спортивная, 9",
                "Выхино",
                "89438975645",
                1,
                date,
                "test",
                List.of("GREY"));
    }

    public static Orders getDefaultWithColorBlack() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().plusDays(2).format(formatter);
        return new Orders(
                "Мария",
                "Иванова",
                "Спортивная, 9",
                "Выхино",
                "89438975645",
                1,
                date,
                "test",
                List.of("BLACK"));
    }

    public static Orders getDefaultWithColorGreyAndBlack() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().plusDays(2).format(formatter);
        return new Orders(
                "Мария",
                "Иванова",
                "Спортивная, 9",
                "Выхино",
                "89438975645",
                1,
                date,
                "test",
                List.of("BLACK", "GREY"));
    }

    public static Orders getDefaultWithoutColor() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().plusDays(2).format(formatter);
        return new Orders(
                "Мария",
                "Иванова",
                "Спортивная, 9",
                "Выхино",
                "89438975645",
                1,
                date,
                "test",
                null);
    }
}
