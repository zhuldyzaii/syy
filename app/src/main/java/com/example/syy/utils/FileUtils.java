package com.example.syy.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static String getPath(Context context, Uri uri) throws Exception {
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameIndex);
        File file = new File(context.getCacheDir(), fileName);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        FileOutputStream outputStream = new FileOutputStream(file);
        int read;
        byte[] buffers = new byte[1024];
        while ((read = inputStream.read(buffers)) != -1) {
            outputStream.write(buffers, 0, read);
        }
        inputStream.close();
        outputStream.close();
        return file.getAbsolutePath();
    }
}
