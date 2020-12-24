package com.example.museumius.fragments.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.museumius.Exhibit;
import com.example.museumius.MainActivity;
import com.example.museumius.R;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    private ArrayList<Exhibit> exhibits;
    private CollectionAdapter collectionAdapter;

    public static CollectionFragment newInstance(ArrayList<Exhibit> exhibits) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putSerializable("exhibits", exhibits);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exhibits = (ArrayList<Exhibit>) getArguments().getSerializable("exhibits");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);
        setHasOptionsMenu(true);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_collection);

        GridView collection = (GridView) root.findViewById(R.id.Collection);

        collectionAdapter = new CollectionAdapter(getActivity(), exhibits);
        collection.setAdapter(collectionAdapter);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                collectionAdapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_view:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
