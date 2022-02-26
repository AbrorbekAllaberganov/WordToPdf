package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import payload.DocumentPayloadDTO;
import service.DocumentService;

import javax.print.Doc;
import java.io.File;

public class ApplicationBot extends TelegramLongPollingBot {
    private final String USERNAME = "BOTUSER";
    private final String TOKEN = "TOKEN";


    @Override
    public String getBotUsername() {
        return this.USERNAME;
    }

    @Override
    public String getBotToken() {
        return this.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        DocumentService documentService=new DocumentService();
        if (update.hasMessage()) {
            Message message=update.getMessage();
            long chat_id=message.getChatId();
            Integer message_id=message.getMessageId();
            if (message.hasText()){
                SendMessage sendMessage=new SendMessage();
                sendMessage.setChatId(String.valueOf(chat_id));
                sendMessage.setText("Menga word fayl yuboring\nFaylni pdfga o'zgartiraman");
                sendMessage.setReplyToMessageId(message_id);
                executeSendMessage(sendMessage);
            }else{
                Document document=message.getDocument();
                DocumentPayloadDTO documentPayloadDTO=documentService.getDocument(this.TOKEN,document.getFileId());
                try {
                    String pdf_path=documentService.getPdf(this.TOKEN,documentPayloadDTO.getResult().getFile_path(),chat_id);
                    SendDocument sendDocument=new SendDocument();
                    sendDocument.setChatId(String.valueOf(chat_id));
                    sendDocument.setDocument(new InputFile(new File(pdf_path)));
                    sendDocument.setCaption(document.getFileName().substring(0,document.getFileName().length()-5)+
                            ".pdf");

                    execute(sendDocument);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }


    public void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeSendVideo(SendVideo sendVideo) {
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
