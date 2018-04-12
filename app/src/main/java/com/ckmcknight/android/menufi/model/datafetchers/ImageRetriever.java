package com.ckmcknight.android.menufi.model.datafetchers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.URLUtil;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ImageRetriever {
    private static Logger logger = Logger.getLogger("Image Retriever");

    public Bitmap retreiveBitmapFromUrl(String url) {
        try {
            File f = File.createTempFile("image-", "-image");
            URL imageURL = new URL(url);
            HttpURLConnection conn = setupConnection(imageURL);
            InputStream is = conn.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            logger.warning("Failed to decode url for image");
            logger.warning(e.getMessage());
        } catch (IOException e) {
            logger.warning("Failed to open http connection");
            logger.warning(e.getMessage());
        }
        return null;
    }

    private HttpURLConnection setupConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        conn.setInstanceFollowRedirects(true);
        conn.connect();
        return conn;
    }
}

