package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import itext.PdfGenerate;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import payload.DocumentPayloadDTO;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DocumentService {

    public DocumentPayloadDTO getDocument(String TOKEN, String file_id) {
        DocumentPayloadDTO documentPayloadDTO = new DocumentPayloadDTO();
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + TOKEN + "/getFile?file_id=" + file_id)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            System.out.println(response);

            String jsonData = Objects.requireNonNull(response.body()).string();

            Type type = new TypeToken<DocumentPayloadDTO>() {
            }.getType();
            documentPayloadDTO = gson.fromJson(jsonData, type);
            return documentPayloadDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String saveFile(String TOKEN, String file_path, long chat_id) {
        String uploadFolder = "E:\\WORK\\Plus\\TextToPdf\\files\\word\\" + chat_id + ".docx";
        try (BufferedInputStream in = new BufferedInputStream(new URL("https://api.telegram.org/file/bot" + TOKEN + "/" + file_path).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFolder)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            return uploadFolder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadFolder;
    }

    public String getPdf(String TOKEN, String file_path, long chat_id) throws Exception {
        PdfGenerate pdfGenerate = new PdfGenerate();
        String inputWordPath = saveFile(TOKEN, file_path, chat_id);
        String wordText = "";
        String outputPDFPath = "E:\\WORK\\Plus\\TextToPdf\\files\\pdf\\" + chat_id + ".pdf";

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(inputWordPath)))) {

            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
            wordText = xwpfWordExtractor.getText();
            System.out.println(wordText);

            // find number of words in the document
            long count = Arrays.stream(wordText.split("\\s+")).count();
            System.out.println("Total words: " + count);

        }


        pdfGenerate.pdfGenerate(wordText,outputPDFPath);
        return outputPDFPath;
    }

}
