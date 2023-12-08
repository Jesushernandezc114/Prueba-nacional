package com.example.conectamoviljhc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.io.UnsupportedEncodingException;

public class Chat extends AppCompatActivity {

    private static final String TAG = "MQTTChat";
    private MqttClient mqttClient;
    private TextView chatTextView;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
/*
        chatTextView = findViewById(R.id.chatTextView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);


        String serverUri = "tcp://mqtt.eclipse.org:1883";
        String clientId = "android-client-" + System.currentTimeMillis();
        try {
            mqttClient = new MqttClient(serverUri, clientId, null);
        } catch (MqttException e) {
            Log.e(TAG, "Error creating MQTT client", e);
        }

        connectToMqttBroker();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (!message.isEmpty()) {
                    publishMessage("chat_topic", message);
                    messageEditText.setText("");
                }
            }
        });
    }

    private void connectToMqttBroker() {
        try {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);

            mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Connected to MQTT broker");

                    subscribeToTopic("node/andr");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "Failed to connect to MQTT broker", exception);
                }
            });

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection to MQTT broker lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.d(TAG, "Message received on topic: " + topic);
                    Log.d(TAG, "Message content: " + new String(message.getPayload(), "UTF-8"));

                    displayMessage(new String(message.getPayload(), "UTF-8"));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery complete");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error while connecting to MQTT broker", e);
        }
    }

    private void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Subscribed to topic: " + topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "Failed to subscribe to topic: " + topic, exception);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error while subscribing to topic", e);
        }
    }

    private void publishMessage(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(message.getBytes());
            mqttClient.publish(topic, mqttMessage);
            Log.d(TAG, "Message published to topic: " + topic);
        } catch (MqttException | UnsupportedEncodingException e) {
            Log.e(TAG, "Error while publishing message", e);
        }
    }

    private void displayMessage(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                chatTextView.append(message + "\n");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectFromMqttBroker();
    }

    private void disconnectFromMqttBroker() {
        try {
            mqttClient.disconnect();
            Log.d(TAG, "Disconnected from MQTT broker");
        } catch (MqttException e) {
            Log.e(TAG, "Error while disconnecting from MQTT broker", e);
        }
  */  }
}
