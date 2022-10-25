package com.example.qbots.service;

import com.example.qbots.config.BotConfig;
import com.example.qbots.view.TelegramMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    TelegramMenu menu = new TelegramMenu();
    BotConfig config = new BotConfig();

    static final String CHAT_KZ = "Чат - бот немесе басқаша айтқанда \"виртуалды көмекші\" — бұл тәулігіне 24 сағат, аптасына 7 күн жұмыс істейтін және келесі тапсырмаларды орындайтын әмбебап менеджер:\n" +
            "Байланыс орталығын 80%-ға дейін түсіріп, стандартты сұрақтарға жауап береді \n" +
            "Сіздің қызметкерлеріңіздің жұмысын ұйымдастырады және бақылайды\n" +
            "Шоттарды, тапсырыстарды және жеткізілімдерді басқарады\n" +
            "Сіздің өніміңізді клиентке ұсынады, тапсырысты рәсімдеуге және оны онлайн төлеуге көмектеседі\n" +
            "Барлық танымал CRM жүйелерімен интеграция";
    static final String CHAT_RU = "Чат-бот или другими словами \"Виртуальный ассистент\" — это универсальный менеджер, который работает 24 часа в сутки, 7 дней в неделю и выполняет следующие задачи:\n" +
            "Разгрузит контактный центр до 80% и ответит на стандартные вопросы \n" +
            "Организует и проконтролирует работу ваших сотрудников\n" +
            "Управляет счетами, заказами и доставкой\n" +
            "Презентует ваш продукт клиенту, поможет оформить заказ и оплатить его онлайн\n" +
            "Интеграция со всеми популярными CRM системам";
    static final String ABOUT_KZ = "Qbots компаниясы Telegram мессенджеріне арналған кіші жүйелік бағдарламалық қамтамасыз етуді әзірлеумен айналысады.\n" +
            "Біз мемлекеттік ұйымдар, ұлттық компаниялар сияқты экономиканың 10 саласы үшін 50-ден астам ірі жобаны іске асырдық.\n" +
            "Мекенжайы: 050000, Алматы қ., Байзақов к-сі, 17, 4 кеңсе,\n" +
            "БСН: 170540017769\n" +
            "Е-mail: qbots2020@gmail.com,\n" +
            "www.qbots.kz\n" +
            "Байланыс Телефоны: 8 778 349 99 94 Нұрлан";
    static final String ABOUT_RU = "Компания Qbots занимается разработкой подсистемного программного обеспечения для мессенджера Telegram. \n " +
            "Мы реализовали более 50 крупных проектов для 10 отраслей экономики как, государственные организации, национальные компании.\n" +
            "Адрес: 050000, г. Алматы, ул. Байзакова 17, офис 4,\n" +
            "БИН: 170540017769\n" +
            "Е-mail: qbots2020@gmail.com,\n" +
            "www.qbots.kz\n" +
            "Контакты: 8 778 349 99 94 Нурлан";
    private String message_now = "", language = "";

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            if (update.hasMessage()) {
                if (message.getText().equals("/start")) {

                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Введите ваше имя")
                    );

                    message_now = "Введите ваше имя";
                } else {

                    if (message_now.equals("Введите ваше имя")) {

                        execute(new SendMessage().setChatId(update.getMessage().getChatId())
                                .setText("Введите номер телефона")
                        );

                        message_now = "Введите номер телефона";
                    } else if (message_now.equals("Введите номер телефона")) {

                        execute(menu.buttons_select_language(
                                message.getChatId()).setText("Вас приветствует онлайн-консультант компании Qbots! Просим Вас выбрать язык интерфейса")
                        );

                        message_now = "Выберите язык";

                    } else if (message_now.equals("menu_1_callback")) {

                        String answer = "";

                        if (language.equals("kz")) {
                            answer = "Қызығушылық танытқаныңыз үшін рахмет. Жұмыс уақытында компания қызметкері Сіз көрсеткен нөмірге қайта қоңырау шалады";
                        } else {
                            answer = "Благодарим за проявленный интерес. В рабочее время сотрудник компании перезвонит на указанный Вами номер";
                        }

                        execute(new SendMessage().setChatId(update.getMessage().getChatId())
                                .setText(answer)
                        );
                    }
                }
            } else if (update.hasCallbackQuery()) {
                String text_menu = "", description_bots = "", description_callback = "", description_company = "";
                String temp = update.getCallbackQuery().getData();

                if (language.equals("") || (temp.equals("ru") || temp.equals("kz"))) {
                    language = temp;
                }

                if (language.equals("kz")) {
                    text_menu = "Әрекеттердің бірін таңдаңыз";
                    description_bots = CHAT_KZ;
                    description_callback = "Егер сіз \"Кері қоңырауға\" тапсырыс бергіңіз келсе, өз Атыңызды және байланыс телефоныңыздың нөмірін жазуыңызды сұраймыз";
                    description_company = ABOUT_KZ;
                } else {
                    text_menu = "Выберите одно из действий";
                    description_bots = CHAT_RU;
                    description_callback = "Если Вы хотите заказать \"Обратный звонок\" просим написать Ваше имя и номер контактного телефона";
                    description_company = ABOUT_RU;
                }

                if (message_now.equals("Выберите язык") || update.getCallbackQuery().getData().equals(language)) {
                    message_now = "menu_1";
                    execute(menu.buttons_menu_1(
                                    update.getCallbackQuery()
                                            .getMessage()
                                            .getChatId(), language)
                            .setText(text_menu)
                    );

                } else {
                    if (update.getCallbackQuery().getData().equals("menu_1_company")) {

                        execute(menu.buttons_menu_company(
                                update.getCallbackQuery().getMessage().getChatId()
                        ).setText(text_menu));

                    } else if (update.getCallbackQuery().getData().equals("menu_1_bots")) {

                        execute(new SendMessage().setChatId(
                                                update.getCallbackQuery()
                                                        .getMessage()
                                                        .getChatId()
                                        )
                                        .setText(description_bots)
                        );

                    } else if (update.getCallbackQuery().getData().equals("menu_1_callback")) {

                        message_now = "menu_1_callback";

                        execute(new SendMessage().setChatId(
                                                update.getCallbackQuery()
                                                        .getMessage()
                                                        .getChatId()
                                        )
                                        .setText(description_callback)
                        );

                    } else if (update.getCallbackQuery().getData().equals("menu_2_company")) {
                        execute(new SendMessage().setText(description_company)
                                .setChatId(update.getCallbackQuery()
                                        .getMessage()
                                        .getChatId()
                                )
                        );
                    }
                }

            }
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
//        return "QBotkz_bot";
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
      return "5785352828:AAHnXQbNO0IeVtAvlPCCqS4S6z-ZvXkucsg";
//        return config.getToken();
    }
}
