package com.example.p3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment {
    private static final String TAG = "FriendsListFragment";
    private FloatingActionButton button;
    private ListView list;
    static List<String> items_list;
    static ArrayAdapter arrayAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);
        Log.d(TAG, "started");

        list = view.findViewById(R.id.list_users);


        items_list = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, items_list);
        list.setAdapter(arrayAdapter);
        refresh();

        button = view.findViewById(R.id.floatingRefreshUsers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items_list.clear();
                refresh();
            }
        });


        return view;
    }

    public static void refresh(){
        Log.d(TAG, "Button clicked");
        items_list.addAll(Main.users);
        arrayAdapter.notifyDataSetChanged();
        Main.users.clear();
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
