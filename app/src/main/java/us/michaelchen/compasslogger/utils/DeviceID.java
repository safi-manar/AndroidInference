package us.michaelchen.compasslogger.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

/**
 * Created by ioreyes on 5/23/16.
 */
public class DeviceID {
    private static final String TAG = "DeviceID";

    private static final String UUID_FILE_NAME = "UUID.txt";
    private static String id = null;

    public static String get(Context c) {
        // Check if there's already an ID
        if(id == null) {
            try {
                // Try to read a saved one from disk
                id = readID(c);
            } catch(IOException e) {
                // If unavailable, generate a new one and save it to disk
                id = generateID();
                writeID(c, id);
            }
        }

        return id;
    }

    /**
     *
     * @return A randomly generated UUID
     */
    private static String generateID() {
        UUID random = UUID.randomUUID();
        return random.toString();
    }

    /**
     *
     * @param c Android context
     * @return A previously generated UUID retrieved from persistent storage
     * @throws IOException If a previously generate UUID is unavailable or unreadable
     */
    private static String readID(Context c) throws IOException{
        FileInputStream inputStream = c.openFileInput(UUID_FILE_NAME);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        // Read just the top line
        String id = reader.readLine();

        reader.close();
        inputReader.close();
        inputStream.close();

        // Simple validity check: the ID is not empty
        if(id != null && !id.isEmpty()) {
            return id;
        } else {
            throw new IOException("Invalid saved UUID");
        }
    }

    /**
     *
     * @param c Android context
     * @param id Randomly generated device UUID
     */
    private static void writeID(Context c, String id){
        try {
            FileOutputStream outputStream = c.openFileOutput(UUID_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputWriter);

            // Write just one line
            writer.write(id);

            writer.close();
            outputWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        }
    }
}