package com.example.museumius.fragments.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumius.MainActivity;
import com.example.museumius.R;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    private ArrayList<String> events;
    public static EventsFragment newInstance(ArrayList<String> events) {
        Bundle args = new Bundle();
        args.putStringArrayList("events", events);
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = getArguments().getStringArrayList("events");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_events);
        RecyclerView recyclerView = root.findViewById(R.id.event_list);
        EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(eventsAdapter);
        return root;
    }
}
