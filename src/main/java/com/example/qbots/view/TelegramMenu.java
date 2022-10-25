package com.example.qbots.view;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class TelegramMenu {
    private HashMap<String, String> text_kz = new HashMap<>();
    private HashMap<String, String> text_ru = new HashMap<>();

    private String language = "";

    public void add_messages(){
        text_kz.put("enter_name", "Атыңызды енгізіңіз");
        text_kz.put("enter_phone", "Телефон нөмірін енгізіңіз");

        text_kz.put("language", "Қазақ тілінде");

        text_kz.put("menu_1_company", "Компания туралы");
        text_kz.put("menu_1_bots", "Чат-боттар туралы");
        text_kz.put("menu_1_callback", "Кері қоңырауға тапсырыс беру");

        text_kz.put("menu_2_about_company", "Біз туралы");
        text_kz.put("menu_2_news", "Компания жаңалықтары");

        text_ru.put("enter_name", "Введите ваше имя");
        text_ru.put("enter_phone", "Введите номер вашего телефона");
        text_ru.put("language", "На русском языке");

        text_ru.put("menu_1_company", "О компании");
        text_ru.put("menu_1_bots", "О чат-ботах");
        text_ru.put("menu_1_callback", "Заказать обратный звонок");

        text_ru.put("menu_2_about_company", "О нас");
        text_ru.put("menu_2_news", "Новости компании");
    }

    public SendMessage buttons_select_language(Long chatId){
        add_messages();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();

        keyboardButtons.add(new InlineKeyboardButton().setText(text_kz.get("language")).setCallbackData("kz"));
        keyboardButtons.add(new InlineKeyboardButton().setText(text_ru.get("language")).setCallbackData("ru"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);
    }

    public SendMessage buttons_menu_1(Long chatId, String lang){
        language = lang;

        String menu_1_company = "", menu_1_bots = "", menu_1_callback = "";

        if(lang.equals("kz")){
            menu_1_company = text_kz.get("menu_1_company");
            menu_1_bots = text_kz.get("menu_1_bots");
            menu_1_callback = text_kz.get("menu_1_callback");
        } else{
            menu_1_company = text_ru.get("menu_1_company");
            menu_1_bots = text_ru.get("menu_1_bots");
            menu_1_callback = text_ru.get("menu_1_callback");
        }

        System.out.println("line 83" + menu_1_company);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsCompany = new ArrayList<>();
        keyboardButtonsCompany.add(new InlineKeyboardButton().setText(menu_1_company).setCallbackData("menu_1_company"));

        List<InlineKeyboardButton> keyboardButtonsBots = new ArrayList<>();
        keyboardButtonsBots.add(new InlineKeyboardButton().setText(menu_1_bots).setCallbackData("menu_1_bots"));

        List<InlineKeyboardButton> keyboardButtonsCallback = new ArrayList<>();
        keyboardButtonsCallback.add(new InlineKeyboardButton().setText(menu_1_callback).setCallbackData("menu_1_callback"));


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsCompany);
        rowList.add(keyboardButtonsBots);
        rowList.add(keyboardButtonsCallback);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);
    }

    public SendMessage buttons_menu_company(Long chatId){
        String menu_2_company = "", menu_2_news = "";

        if(language.equals("kz")){
            menu_2_company = text_kz.get("menu_2_about_company");
            menu_2_news = text_kz.get("menu_2_news");
        } else{
            menu_2_company = text_ru.get("menu_2_about_company");
            menu_2_news = text_ru.get("menu_2_news");
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsCompany = new ArrayList<>();
        keyboardButtonsCompany.add(new InlineKeyboardButton().setText(menu_2_company).setCallbackData("menu_2_company"));

        List<InlineKeyboardButton> keyboardButtonsNews = new ArrayList<>();
        keyboardButtonsNews.add(new InlineKeyboardButton().setText(menu_2_news).setCallbackData("menu_2_news").setUrl("https://www.qbots.kz/"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsCompany);
        rowList.add(keyboardButtonsNews);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setReplyMarkup(inlineKeyboardMarkup);
    }

}