package com.dicoding.picodiploma.gastiadiapp.ui.chat.messages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.gastiadiapp.ui.MainActivity;
import com.dicoding.picodiploma.gastiadiapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import np.com.blackspring.adoniswebsocketclient.Socket;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MessagesActivity extends AppCompatActivity {

//    /**
     private WebSocket webSocket;
     private MessageAdapter adapter;
     private ProgressBar progressBar;
     private ListView messageList;
     private RelativeLayout messagesContent;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_messages);

         progressBar = findViewById(R.id.progressBar);
         messagesContent = findViewById(R.id.messagesContent);

         messageList = findViewById(R.id.messageList);
         final EditText messageBox = findViewById(R.id.messageBox);
         TextView send = findViewById(R.id.send);

         instantiateWebSocket();

         adapter = new MessageAdapter();
         messageList.setAdapter(adapter);

         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String message = messageBox.getText().toString();
                 Timestamp stamp = new Timestamp(System.currentTimeMillis());
                 Date date = new Date(stamp.getTime());
                 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");

                 if (!message.isEmpty()) {
                     webSocket.send(message);
                     messageBox.setText("");

                     JSONObject jsonObject = new JSONObject();

                     try {
                         jsonObject.put("message", message);
                         jsonObject.put("byServer", false);

                         sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                         String formattedDate = sdf.format(date);

                         jsonObject.put("time", formattedDate);

                         adapter.addItem(jsonObject);

                         messageList.smoothScrollToPosition(messageList.getCount() - 1);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }
         });
     }

     private void instantiateWebSocket() {
         OkHttpClient client = new OkHttpClient();


         //replace x.x.x.x with your machine's IP Address
         //Request request = new Request.Builder().url("ws://8e21fdbb7cea.ap.ngrok.io/gastiadi-ws").build();
         Request request = new Request.Builder().url("ws://echo.websocket.org").build();

         SocketListener socketListener = new SocketListener(this);

         webSocket = client.newWebSocket(request, socketListener);

         setLoading(true);
     }

     public class SocketListener extends WebSocketListener {

         public MessagesActivity activity;
         private Integer count = 0;

         public SocketListener(MessagesActivity activity) {
             this.activity = activity;
         }

         @Override
         public void onOpen(WebSocket webSocket, Response response) {
             activity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     Toast.makeText(activity, "Connection Established!", Toast.LENGTH_LONG).show();
                     webSocket.send("coba aja dulu");
                     setLoading(false);
                 }
             });
         }

         @Override
         public void onMessage(WebSocket webSocket, final String text) {
             Log.d("count", String.valueOf(count));

             activity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     JSONObject jsonObject = new JSONObject();

                     Timestamp stamp = new Timestamp(System.currentTimeMillis());
                     Date date = new Date(stamp.getTime());
                     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
                     try {
                         sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                         String formattedDate = sdf.format(date);
                         if (count == 0) {
                             jsonObject.put("message", "Halo, selamat datang di Gastiadi. Ada yang bisa kami bantu?");
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 1) {
                             jsonObject.put("message", "Dimana lokasi kejadian itu");
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 2) {
                             jsonObject.put("message", "Bisakah anda menceritakan kronologisnya");
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 3) {
                             jsonObject.put("message", "Bisakah anda memberitahu kondisi terkini dari korban?");
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 4) {
                             jsonObject.put(
                                     "message",
                                     "Apakah korban sudah mendapatkan pertolongan pertama?"
                             );
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 5) {
                             jsonObject.put(
                                     "message",
                                     "Apakah anda mengenal korban?"
                             );
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 6) {
                             jsonObject.put(
                                     "message",
                                     "Bisakah anda memberikan identitas dan alamat korban?"
                             );
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 6) {
                             jsonObject.put(
                                     "message",
                                     "Ada lagi yang bisa dibantu?"
                             );
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         } else if (count == 7) {
                             jsonObject.put(
                                     "message",
                                     "Baik terima kasih atas informasinya. Kami akan proses aduan Anda segera"
                             );
                             jsonObject.put("byServer", true);
                             jsonObject.put("time", formattedDate);
                             adapter.addItem(jsonObject);
                         }

                         messageList.smoothScrollToPosition(messageList.getCount() - 1);
                         count++;
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             });
         }

         @Override
         public void onMessage(WebSocket webSocket, ByteString bytes) {
             super.onMessage(webSocket, bytes);
         }

         @Override
         public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
         }

         @Override
         public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
         }

         @Override
         public void onFailure(WebSocket webSocket, final Throwable t, @Nullable final Response response) {
            super.onFailure(webSocket, t, response);
         }
     }
//     */

    public class MessageAdapter extends BaseAdapter {

        List<JSONObject> messagesList = new ArrayList<>();

        @Override
        public int getCount() {
            return messagesList.size();
        }

        @Override
        public Object getItem(int i) {
            return messagesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.message_list_item, viewGroup, false);


            CardView sentMessageContainer = view.findViewById(R.id.sentMessageContainer);
            TextView sentMessage = view.findViewById(R.id.sentMessage);
            TextView sentTimeStamp = view.findViewById(R.id.sentTimeStamp);

            CardView receivedMessageContainer = view.findViewById(R.id.receivedMessageContainer);
            TextView receivedMessage = view.findViewById(R.id.receivedMessage);
            TextView receivedTimeStamp = view.findViewById(R.id.receivedTimeStamp);


            JSONObject item = messagesList.get(i);

            try {

                if (item.getBoolean("byServer")) {
                    receivedMessageContainer.setVisibility(View.VISIBLE);
                    receivedMessage.setText(item.getString("message"));
                    receivedTimeStamp.setText(item.getString("time"));

                    sentMessageContainer.setVisibility(View.INVISIBLE);
                } else {
                    sentMessageContainer.setVisibility(View.VISIBLE);
                    sentMessage.setText(item.getString("message"));
                    sentTimeStamp.setText(item.getString("time"));

                    receivedMessageContainer.setVisibility(View.INVISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }



        void addItem(JSONObject item) {


            messagesList.add(item);
            notifyDataSetChanged();


        }

    }

    private void setLoading(Boolean state) {
         if (state) {
             messagesContent.setVisibility(View.INVISIBLE);
             progressBar.setVisibility(View.VISIBLE);
         } else {
             messagesContent.setVisibility(View.VISIBLE);
             progressBar.setVisibility(View.INVISIBLE);
         }
    }

    /**

    private TextView output, send;
    private Socket socket;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        output = findViewById(R.id.output);
        send = findViewById(R.id.send);
        final EditText messageBox = findViewById(R.id.messageBox);
        ListView messageList = findViewById(R.id.messageList);

        try {
            start();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MessageAdapter();
        messageList.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = messageBox.getText().toString();

                if (!message.isEmpty()) {

                    JSONObject jsonObjectSend = new JSONObject();
                    try {
                        jsonObjectSend.put("body", message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socket.send("chat", jsonObjectSend.toString());

                    messageBox.setText("");

                    JSONObject jsonObject = new JSONObject();

                    try {

                        jsonObject.put("message", message);
                        jsonObject.put("byServer", false);

                        adapter.addItem(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

//
//        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                socket.join("chat");
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("username", "mobile");
//                    jsonObject.put("body", "from mobile");
//                    socket.sendOnOpen("chat", String.valueOf(jsonObject));
//                    Log.d("hai", jsonObject.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void start() throws JSONException {

//        socket = Socket.Builder.with("ws://8e21fdbb7cea.ap.ngrok.io/gastiadi-ws").build();
        socket = Socket.Builder.with("ws://echo.websocket.org").build();
        socket.connect();
        socket.join("chat");

        socket.onEvent(Socket.EVENT_OPEN, new Socket.OnEventListener() {
            @Override
            public void onMessage(String event) {
                Log.d("onEvent event", event);
                Log.d("test", event);
                output("connected");
            }
        });

        socket.setOnChangeStateListener(new Socket.OnStateChangeListener() {
            @Override
            public void onChange(Socket.State status) {
                Log.d("status", status.toString());
            }
        });

        socket.onEventResponse("chat", new Socket.OnEventResponseListener() {
            @Override
            public void onMessage(String event, String data) {
                Log.v("test B", event);
                Log.v("test C", data);
                Log.d("onEventResponse event", event);
                Log.d("onEventResponse data", data);
                output(data);
            }
        });

        socket.onEventResponse("message", new Socket.OnEventResponseListener() {
            @Override
            public void onMessage(String event, String data) {
                Log.v("test B", event);
                Log.v("test C", data);
                Log.d("onEventResponse event", event);
                Log.d("onEventResponse data", data);
                output(data);
            }
        });

        socket.onEventResponse("data", new Socket.OnEventResponseListener() {
            @Override
            public void onMessage(String event, String data) {
                Log.v("test B", event);
                Log.v("test C", data);
                Log.d("onEventResponse event", event);
                Log.d("onEventResponse data", data);
                output(data);
            }
        });

        socket.onEvent(Socket.EVENT_RECONNECT_ATTEMPT, new Socket.OnEventListener() {
            @Override
            public void onMessage(String event) {
                output("reconnecting");
            }
        });
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(output.getText().toString() + "\n\n" + txt);
            }
        });
    }
     */
}