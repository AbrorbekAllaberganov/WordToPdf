package bot;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Maina {
    public static void main(String[] args) {

        System.out.println("RUN...");
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ApplicationBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
