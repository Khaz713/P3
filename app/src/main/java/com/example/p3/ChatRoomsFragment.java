package com.example.p3;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatRoomsFragment extends Fragment {
    private static final String TAG = "ChatRoomsFragment";
    private ListView list;
    private FloatingActionButton button;
    private FloatingActionButton button2;
    static ArrayAdapter arrayAdapter;

    static Set<String> items = new HashSet<>();
    static List<String> items_list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatrooms_fragment, container, false);
        Log.d(TAG, "started");
        list = (ListView) view.findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Main.location = "2||" + list.getItemAtPosition(position).toString();
                Main.toolbar.setTitle(list.getItemAtPosition(position).toString());
            }
        });

        button = view.findViewById(R.id.floatingAddChatRoom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("New Room");
                dialog.setContentView(R.layout.dialog_template);
                final EditText text = dialog.findViewById(R.id.write);
                final Button buttonDialog = dialog.findViewById(R.id.SaveNow);
                buttonDialog.setText(R.string.buttonChatRoom);
                text.setHint("Room name");
                text.setEnabled(true);
                buttonDialog.setEnabled(true);
                buttonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!text.getText().toString().trim().equals("")) {
                            Main.location = "2||" + text.getText().toString();
                            Main.toolbar.setTitle(text.getText().toString());
                            GlobalChatFragment.cleanLog();
                            Main.mViewPager.setCurrentItem(1);
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();
            }
        });
        items_list = new ArrayList<>();


        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, items_list);
        list.setAdapter(arrayAdapter);

        refresh();

        button2 = view.findViewById(R.id.floatingRefreshRooms);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items_list.clear();
                refresh();
            }
        });


        return view;


    }

    public static void refresh() {
        items_list.addAll(items);
        arrayAdapter.notifyDataSetChanged();
        Log.e(TAG, "BUTTON IS WORKING: " + items_list.toString());
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
