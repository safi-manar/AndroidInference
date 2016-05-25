package us.michaelchen.compasslogger.sensorservices;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ioreyes on 5/25/16.
 */
public abstract class AbstractSensorService extends AbstractRecordingService {
    private SensorManager sensorManager = null;

    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            sensorManager.unregisterListener(this);
            Log.d(tag, event.toString());

            Map<String, Object> data = processSensorData(event);
            updateDatabase(data);
        }
    };

    protected AbstractSensorService(String subclassName) {
        super(subclassName);
    }

    /**
     * Defines which sensor to use
     * @return A Sensor.SENSOR_TYPE enumerated value
     */
    protected abstract int getSensorType();

    @Override
    protected final void onHandleIntent(Intent intent) {
        if(intent != null) {
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            Sensor sensor = sensorManager.getDefaultSensor(getSensorType());
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * Read sensor data.
     * @param event
     * @return A map of labels and corresponding values
     */
    protected Map<String, Object> processSensorData(SensorEvent event) {
        float value = event.values[0];

        Map<String, Object> vals = new HashMap<>();
        vals.put(broadcastKey(), value);

        return vals;
    }
}