package com.ityulkanov;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ColorizerBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "bnwtocolor_bot";
    }

    @Override
    public String getBotToken() {
        return "1657936640:AAGJuAC_LvFv3G-hpE-tvUs2E_pGvH6vIW0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<PhotoSize> photos = update.getMessage().getPhoto();
        Optional<PhotoSize> photo = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize));
        PhotoSize photoSize = photo.get();

        GetFile getFile = new GetFile();
        getFile.setFileId(photoSize.getFileId());
        File file = null;
        try {
            file = execute(getFile);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        java.io.File file2 = null;
        try {
            file2 = downloadFile(file);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("").build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            final ResponseBody body = response.body();

        }


        SendPhoto build = SendPhoto.builder().photo(new InputFile(file2.getAbsoluteFile())).chatId(chatId).build();

        try {
            execute(build);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
