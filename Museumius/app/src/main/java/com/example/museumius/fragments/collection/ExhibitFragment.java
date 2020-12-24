package com.example.museumius.fragments.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.museumius.Exhibit;
import com.example.museumius.MainActivity;
import com.example.museumius.R;


public class ExhibitFragment extends Fragment {

    private Exhibit exhibit;

    public static ExhibitFragment newInstance(Exhibit exhibit) {
        Bundle args = new Bundle();
        args.putSerializable("exhibit", exhibit);
        ExhibitFragment fragment = new ExhibitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exhibit = (Exhibit) getArguments().getSerializable("exhibit");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exhibit, container, false);

        ((MainActivity) getActivity()).getNavigation().setVisibility(View.INVISIBLE);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_exhibit);
        setHasOptionsMenu(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24);
        ImageView img = (ImageView) root.findViewById(R.id.exhibit_image);
        img.setImageResource(getActivity().getResources().getIdentifier(exhibit.getImg(),
                "drawable", getActivity().getPackageName()));

        TextView textView = (TextView) root.findViewById(R.id.exhibitTextView);
        textView.setText(exhibit.getName());
        textView = (TextView) root.findViewById(R.id.textDescription);
        textView.setText(exhibit.getDescription());
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        setHasOptionsMenu(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getNavigation().setVisibility(View.VISIBLE);
        super.onStop();
    }
}
