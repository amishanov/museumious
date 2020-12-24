package com.example.museumius.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.museumius.MainActivity;
import com.example.museumius.R;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance() {
        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_info);

        EditText editName = root.findViewById(R.id.edit_name);
        EditText editPhone = root.findViewById(R.id.edit_phone);
        Button button = root.findViewById(R.id.ticket_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                if (name.length() == 0 || phone.length() == 0){
                    Toast.makeText(getActivity(), "Заполните все поля",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    ((MainActivity) getActivity()).getDbManager().insertToDbRequest(name, phone);
                    Toast.makeText(getActivity(), "Билет куплен.",
                            Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editPhone.setText("");
                }
            }
        });
        return root;
    }




}
