package com.example.p3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment WiFi";

    private ImageView btnRoom;
    private ImageView btnGlobal;
    private ImageView btnRoulette;
    private ImageView btnFriends;
    private ImageView btnSettings;
    private ImageView imgWifi;
    private TextView mWifiSSID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "started");
        View view = inflater.inflate(R.layout.activity_main, container, false);
        btnRoom = (ImageView) view.findViewById(R.id.chat_rooms);
        btnGlobal = (ImageView) view.findViewById(R.id.global_chat);
        btnRoulette = (ImageView) view.findViewById(R.id.chat_roulette);
        btnFriends = (ImageView) view.findViewById(R.id.friends);
        btnSettings = (ImageView) view.findViewById(R.id.settings);
        imgWifi = (ImageView) view.findViewById(R.id.wifi_signal);
        mWifiSSID = (TextView) view.findViewById(R.id.wifi_ssid);


        //Hide wifi signal icons. They will be shown in the main activity instead:
        mWifiSSID.setVisibility(View.GONE);
        imgWifi.setVisibility(View.GONE);

        btnGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on Glob");
                Main.toolbar.setTitle("Global Chat");
                GlobalChatFragment.cleanLog();
                ((Main) getActivity()).changeView(1);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on Rooms");
                Main.toolbar.setTitle("Chat Rooms");
                ((Main) getActivity()).changeView(2);
            }
        });

        btnRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on Rou");
                Main.toolbar.setTitle("Chat Roulette");
                ((Main) getActivity()).changeView(3);
            }
        });
        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on Fre");
                Main.toolbar.setTitle("Users");
                ((Main) getActivity()).changeView(4);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on Set");
                Main.toolbar.setTitle("Settings");
                ((Main) getActivity()).changeView(5);
            }
        });


        return view;
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
        Log.e(TAG, "onPause: ");

        //    onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
