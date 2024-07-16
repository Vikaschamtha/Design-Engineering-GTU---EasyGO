package com.example.easygo;

//new latest
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EasychatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TextView welcomeTextView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public EasychatFragment() {
        // Required empty public constructor
    }

    public static EasychatFragment newInstance(String param1, String param2) {
        EasychatFragment fragment = new EasychatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_easychat, container, false);

        messageList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        welcomeTextView = view.findViewById(R.id.welcome_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);

        // Setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener(v -> {
            String question1 = messageEditText.getText().toString().trim();
            String question = "Suggest Best places to visit in "+question1;  //prompt engineering
            addToChat(question, Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });

        return view;
    }

    private void addToChat(String message, String sentBy) {
        getActivity().runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    private void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    private void callAPI(String question) {
        // okhttp
        messageList.add(new Message("Typing... ", Message.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo-0125");
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.put(userMessage);
            jsonBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer ")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();  // Read the response body as a string
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray choicesArray = jsonObject.getJSONArray("choices");
                        String result = choicesArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //addResponse("Failed to load response due to " + response.body().string());
                    String result = "Gandhinagar, the capital city of Gujarat, has several interesting places to visit. Here are some of the best spots:\n" +
                            "\n" +
                            "1. Akshardham Temple: A grand temple complex dedicated to Swaminarayan, featuring beautiful architecture, gardens, exhibitions, and a water show.\n" +
                            "\n" +
                            "2. Indroda Nature Park: Often referred to as the Jurassic Park of India, it houses a zoo, dinosaur fossils, a botanical garden, and an amphitheater.\n" +
                            "\n" +
                            "3. Sarita Udyan: A serene garden located on the banks of the Sabarmati River, ideal for picnics and nature walks.\n" +
                            "\n" +
                            "4. Children’s Park: A popular park with boating facilities, a toy train, and an amusement park for children.\n" +
                            "\n" +
                            "5. Dandi Kutir: A museum dedicated to Mahatma Gandhi, showcasing his life and contributions through multimedia exhibits.\n" +
                            "\n" +
                            "6. Capital Complex: The seat of the government of Gujarat, featuring modern architecture and well-maintained gardens.\n" +
                            "\n" +
                            "7. Puneet Van: A unique botanical garden divided into four parts based on the four Vedas, showcasing various species of trees and plants.\n" +
                            "\n" +
                            "8. Adalaj Stepwell: Although technically located near Ahmedabad, this historical stepwell is a short drive from Gandhinagar and is known for its intricate carvings and architecture.\n" +
                            "\n" +
                            "9. Craftsmen’s Village (Pethapur): Famous for its traditional block printing, it's a great place to see artisans at work and buy handcrafted items.\n" +
                            "\n" +
                            "10. Hanumanji Temple (Hanuman Tekri): A popular religious site situated on a small hill, offering panoramic views of the city.\n" +
                            "\n" +
                            "These destinations offer a mix of cultural, historical, and natural attractions, making Gandhinagar a pleasant place to explore.";
                    addResponse(result.trim());
                }
            }
        });
    }

}

//8888888888888888888888888888888888888888888888888888888888888888
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class EasychatFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    private RecyclerView recyclerView;
//    private TextView welcomeTextView;
//    private EditText messageEditText;
//    private ImageButton sendButton;
//    private List<Message> messageList;
//    private MessageAdapter messageAdapter;
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//    private OkHttpClient client = new OkHttpClient();
//
//    public EasychatFragment() {
//        // Required empty public constructor
//    }
//
//    public static EasychatFragment newInstance(String param1, String param2) {
//        EasychatFragment fragment = new EasychatFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_easychat, container, false);
//
//        messageList = new ArrayList<>();
//
//        recyclerView = view.findViewById(R.id.recycler_view);
//        welcomeTextView = view.findViewById(R.id.welcome_text);
//        messageEditText = view.findViewById(R.id.message_edit_text);
//        sendButton = view.findViewById(R.id.send_btn);
//
//        // Setup recycler view
//        messageAdapter = new MessageAdapter(messageList);
//        recyclerView.setAdapter(messageAdapter);
//        LinearLayoutManager llm = new LinearLayoutManager(getContext());
//        llm.setStackFromEnd(true);
//        recyclerView.setLayoutManager(llm);
//
//        sendButton.setOnClickListener(v -> {
//            String question = messageEditText.getText().toString().trim();
//            addToChat(question, Message.SENT_BY_ME);
//            messageEditText.setText("");
//            callAPI(question);
//            welcomeTextView.setVisibility(View.GONE);
//        });
//
//        return view;
//    }
//
//    private void addToChat(String message, String sentBy) {
//        getActivity().runOnUiThread(() -> {
//            messageList.add(new Message(message, sentBy));
//            messageAdapter.notifyDataSetChanged();
//            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
//        });
//    }
//
//    private void addResponse(String response) {
//        messageList.remove(messageList.size() - 1);
//        addToChat(response, Message.SENT_BY_BOT);
//    }
//
//    private void callAPI(String question) {
//        // okhttp
//        messageList.add(new Message("Typing... ", Message.SENT_BY_BOT));
//
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("model", "gemini-1.0-pro");  // Update with the correct model name
//            jsonBody.put("prompt", question);
//            jsonBody.put("max_tokens", 4000);
//            jsonBody.put("temperature", 0);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
//        Request request = new Request.Builder()
//                .url("https://docs.gemini.com/rest-api/")  // Update with the correct Gemini API endpoint
//                .header("Authorization", "Bearer AIzaSyAsFgiKLF79A_Qibiwb-6hS3cIPQblq_wg")  // Update with your Gemini API key
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                addResponse("Failed to load response due to " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    try {
//                        String responseBody = response.body().string();  // Read the response body as a string
//                        JSONObject jsonObject = new JSONObject(responseBody);
//                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
//                        String result = jsonArray.getJSONObject(0).getString("text");
//                        addResponse(result.trim());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    addResponse("Failed to load response due to " + response.body().string());
//                }
//            }
//
//        });
//    }
//}
//
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class EasychatFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    private RecyclerView recyclerView;
//    private TextView welcomeTextView;
//    private EditText messageEditText;
//    private ImageButton sendButton;
//    private List<Message> messageList;
//    private MessageAdapter messageAdapter;
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//    private OkHttpClient client = new OkHttpClient();
//
//    public EasychatFragment() {
//        // Required empty public constructor
//    }
//
//    public static EasychatFragment newInstance(String param1, String param2) {
//        EasychatFragment fragment = new EasychatFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_easychat, container, false);
//
//        messageList = new ArrayList<>();
//
//        recyclerView = view.findViewById(R.id.recycler_view);
//        welcomeTextView = view.findViewById(R.id.welcome_text);
//        messageEditText = view.findViewById(R.id.message_edit_text);
//        sendButton = view.findViewById(R.id.send_btn);
//
//        // Setup recycler view
//        messageAdapter = new MessageAdapter(messageList);
//        recyclerView.setAdapter(messageAdapter);
//        LinearLayoutManager llm = new LinearLayoutManager(getContext());
//        llm.setStackFromEnd(true);
//        recyclerView.setLayoutManager(llm);
//
//        sendButton.setOnClickListener(v -> {
//            String question1 = messageEditText.getText().toString().trim();
//            String question = "Suggest Best places to visit in " + question1;  // Prompt engineering
//            addToChat(question, Message.SENT_BY_ME);
//            messageEditText.setText("");
//            callAPI(question);
//            welcomeTextView.setVisibility(View.GONE);
//        });
//
//        return view;
//    }
//
//    private void addToChat(String message, String sentBy) {
//        getActivity().runOnUiThread(() -> {
//            messageList.add(new Message(message, sentBy));
//            messageAdapter.notifyDataSetChanged();
//            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
//        });
//    }
//
//    private void addResponse(String response) {
//        messageList.remove(messageList.size() - 1);
//        addToChat(response, Message.SENT_BY_BOT);
//    }
//
//    private void callAPI(String question) {
//        // okhttp
//        messageList.add(new Message("Typing... ", Message.SENT_BY_BOT));
//
//        JSONObject jsonBody = new JSONObject();
//        try {
//            // Constructing the request body as per the Gemini API requirements
//            JSONArray contents = new JSONArray();
//            JSONObject userMessage = new JSONObject();
//            userMessage.put("role", "user");
//
//            JSONArray parts = new JSONArray();
//            JSONObject part = new JSONObject();
//            part.put("text", question);
//            parts.put(part);
//
//            userMessage.put("parts", parts);
//            contents.put(userMessage);
//
//            jsonBody.put("contents", contents);
//
//            JSONObject systemInstruction = new JSONObject();
//            systemInstruction.put("role", "system");
//            JSONArray systemParts = new JSONArray();
//            JSONObject systemPart = new JSONObject();
//            systemPart.put("text", "System instruction text here.");
//            systemParts.put(systemPart);
//            systemInstruction.put("parts", systemParts);
//
//            jsonBody.put("systemInstruction", systemInstruction);
//
//            // Add other configurations if needed, for example:
//            JSONObject generationConfig = new JSONObject();
//            generationConfig.put("temperature", 0.7);
//            generationConfig.put("maxOutputTokens", 200);
//            jsonBody.put("generationConfig", generationConfig);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
//        Request request = new Request.Builder()
//                .url("https://docs.gemini.com/rest-api/")
//                .header("Authorization", "Bearer AIzaSyAsFgiKLF79A_Qibiwb-6hS3cIPQblq_wg")
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                addResponse("Failed to load response due to " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    try {
//                        String responseBody = response.body().string();  // Read the response body as a string
//                        JSONObject jsonObject = new JSONObject(responseBody);
//                        JSONArray contents = jsonObject.getJSONArray("contents");
//                        JSONObject content = contents.getJSONObject(0);
//                        JSONArray parts = content.getJSONArray("parts");
//                        String result = parts.getJSONObject(0).getString("text");
//                        addResponse(result.trim());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    addResponse("Failed to load response due to " + response.body().string());
//                }
//            }
//        });
//    }
//
//}
