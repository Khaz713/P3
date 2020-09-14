package com.example.p3;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private TextView currentNickaname;
    SharedPreferences pref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        Log.d(TAG, "started");
        pref = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        currentNickaname = view.findViewById(R.id.current_nickname);
        currentNickaname.setText(Main.NICKNAME);
        currentNickaname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("New Nickname");
                dialog.setContentView(R.layout.dialog_template);
                final EditText write = dialog.findViewById(R.id.write);
                final Button button = dialog.findViewById(R.id.SaveNow);
                write.setEnabled(true);
                button.setEnabled(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (write.getText().toString().trim().length() < 3 || write.getText()
                                .toString().trim().length() > 16) {
                            write.setError("Nickname has to be 3-16 characters long.");
                        }
                        else if(!write.getText().toString().trim().equals("")){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Nickname", write.getText().toString());
                            editor.apply();
                            Main.NICKNAME = write.getText().toString();
                            currentNickaname.setText(Main.NICKNAME);
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();
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
        Log.e(TAG, "onResume: " );
        super.onResume();
    }

    @Override
    public void onPause() {
        ((Main)getActivity()).tearDownChecker(3);
        Log.e(TAG, "onPause: " );

        //    onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
    }
}
