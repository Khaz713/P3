package com.example.p3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GlobalChatFragment extends Fragment {
    private static final String TAG = "GlobalChatFragment";
    private Button btnSend;
    private EditText editText;
    private static LinearLayout linearLayout;
    static int lineCounter = 0;
    static File log;
    static FileObserver observer;
    static Handler handler;
    static String lines[];
    static String logPath;


    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        logPath = getContext().getFilesDir().toString() + "log.txt";

        View view = inflater.inflate(R.layout.global_chat_fragment, container, false);
        Log.d(TAG, "started");
        btnSend = (Button) view.findViewById(R.id.sendButton);
        editText = (EditText) view.findViewById(R.id.plain_text_input);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_global_chat);


        try {
            log = new File(getContext().getFilesDir(), "log.txt");

            observer = new FileObserver(log.getPath()) {
                @Override
                public void onEvent(int event, @Nullable String path) {
                    if (event == FileObserver.MODIFY) {
                        Log.e(TAG, "I'm here");
                        Log.e(TAG, log.getPath());
                        String test;
                        StringBuilder stringBuilder = new StringBuilder();
                        try {
                            FileInputStream fis = new FileInputStream(log);
                            InputStreamReader inputStreamReader = new InputStreamReader(fis,
                                    StandardCharsets.UTF_8);
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            String line = reader.readLine();
                            int count = 0;
                            while (line != null) {
                                if (count >= lineCounter) {
                                    stringBuilder.append(line).append("\n");
                                }
                                line = reader.readLine();
                                count++;
                            }
                            lineCounter = count;
                            fis.close();
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        } finally {
                            test = stringBuilder.toString();
                            lines = test.split("\\r?\\n");
                            handler.sendEmptyMessage(1);
                        }
                        Log.e(TAG, test + " " + lineCounter);
                    }
                }
            };
            observer.startWatching();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = String.valueOf(editText.getText());
                editText.setText("");

                if (!message.equals("")) {
                    try {
                        FileWriter fileWriter = new FileWriter(log, true);
                        Main.isMessage = true;
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        String testLog = "M" + Main.getMacAddr() + "\n" + Main.NICKNAME + "\n" + message + "\n";
                        Log.d(TAG, testLog);
                        Main.message2Send = testLog;
                        ((Main) getActivity()).distribute2All(testLog);
                        bufferedWriter.write(testLog);
                        bufferedWriter.close();
                        fileWriter.close();

                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    AppCompatTextView textView1;
                    textView1 = new AppCompatTextView(getActivity());
                    TextView textView2 = new AppCompatTextView(getActivity());
                    Log.d(TAG, lines[0] + " " + Main.getMacAddr());
                    if (lines[0].equals("M" + Main.getMacAddr())) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                        params.gravity = Gravity.RIGHT;
                        params.topMargin = 2;
                        params.leftMargin = 100;
                        textView1.setLayoutParams(params);
                        textView2.setLayoutParams(params);
                        textView1.setBackgroundResource(R.drawable.box3);
                    } else {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                        params.gravity = Gravity.LEFT;
                        params.topMargin = 2;
                        params.rightMargin = 100;
                        textView1.setLayoutParams(params);
                        textView2.setLayoutParams(params);
                        textView1.setBackgroundResource(R.drawable.box4);
                    }
                    textView1.setText(lines[2]);
                    textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    textView2.setText(lines[1]);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    linearLayout.addView(textView2);
                    linearLayout.addView(textView1);
                    ((Main) getActivity()).reportOnFragments();
                }
            }
        };


        return view;
    }


    static void cleanLog() {

        try {
            lineCounter = 0;
            observer.stopWatching();
            PrintWriter writer = new PrintWriter(log);
            writer.print("");
            writer.close();
            Log.e(TAG, "LOG CLEANED");
            linearLayout.removeAllViews();
            observer.startWatching();
        } catch (Exception e) {

        }
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onResume() {
        //((Main)getActivity()).tearDownChecker(0);
        Log.e(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        ((Main) getActivity()).tearDownChecker(3);

        observer.stopWatching();
        lineCounter = 0;
        try {
            PrintWriter writer = new PrintWriter(log);
            writer.print("");
            writer.close();
        } catch (Exception e) {

        }
        Log.e(TAG, "onPause: " + lineCounter);

        //    onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        lineCounter = 0;
        Log.e(TAG, "onDestroy: " + lineCounter);


        super.onDestroy();
    }
}
