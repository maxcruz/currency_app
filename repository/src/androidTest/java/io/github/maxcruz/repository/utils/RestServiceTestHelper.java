package io.github.maxcruz.repository.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class used to load the content from JSON
 */
public class RestServiceTestHelper {

    /**
     *  Return the content for the given InputStream.
     *
     * @param inputStream Stream to read.
     * @return The content of the InputStream.
     * @throws IOException when you have an error reading the file.
     */
    private static String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Read the given file and return his content.
     *
     * @param context test instrumentation context
     * @param filePath path relative to the asset directory
     * @return the content in the file.
     * @throws IOException when you have an error reading the file.
     */
    public static String getStringFromFile(Context context, String filePath) throws IOException {
        final InputStream stream = context.getResources().getAssets().open(filePath);
        String ret = convertStreamToString(stream);
        stream.close();
        return ret;
    }
}
