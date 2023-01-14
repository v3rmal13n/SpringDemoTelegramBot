package io.proj3ct.SpringDemoBot.service;

import io.proj3ct.SpringDemoBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":

                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                case "Плохо":

                    noCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "Хорошо":

                    yesCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "Как дела?":

                    ritaCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "Круто":
                    lastComand(chatId, update.getMessage().getChat().getFirstName());
                    break;


                default:

                        sendMessage(chatId, "Пока что это в разработке...");

            }
        }

    }


    private void startCommandReceived (long chatId, String name) {

        String answer = "Привет, " + name + ", как у тебя дела?!\n(доступные команды: Хорошо , Плохо)\n";

        sendMessage(chatId, answer);
    }

    private void noCommandReceived (long chatId, String name) {

        String answer = "не грусти, " + name + ", все хорошо";

        sendMessage(chatId, answer);
    }

    private void yesCommandReceived (long chatId, String name) {

        String answer = "молодец, " + name + "!\n хорошего тебе настроения!";

        sendMessage(chatId, answer);
    }

    private void ritaCommandReceived (long chatId, String name) {

        String answer = " Замечательно! У тебя как?\n(Круто)";

        sendMessage(chatId, answer);
    }

    private void lastComand (long chatId, String name) {

        String answer = "Я рад за тебя, удачи!";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }catch (TelegramApiException e) {

        }
    }
}
