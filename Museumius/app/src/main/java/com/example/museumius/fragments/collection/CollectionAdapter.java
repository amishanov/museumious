package com.example.museumius.fragments.collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.museumius.Exhibit;
import com.example.museumius.MainActivity;
import com.example.museumius.R;

import java.util.ArrayList;

public class CollectionAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Exhibit> exhibits;
    private ArrayList<Exhibit> filteredExhibits;
    private Context context;

    public CollectionAdapter(Context context, ArrayList<Exhibit> exhibits) {
        this.context = context;
        this.exhibits = exhibits;
        this.filteredExhibits = exhibits;
    }

    @Override
    public int getCount() {
        return filteredExhibits.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_layout, null);
        Exhibit exhibit = filteredExhibits.get(position);
        ImageView img = view.findViewById(R.id.image_view);
        CardView cardView = view.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExhibitFragment exhibitFragment = ExhibitFragment.newInstance(exhibit);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_close_enter, R.anim.fragment_close_exit,
                                R.anim.fragment_close_enter, R.anim.fragment_close_exit)
                        .replace(R.id.host, exhibitFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        img.setImageResource(context.getResources().getIdentifier(exhibit.getImg(),
                "drawable", context.getPackageName()));
        String str = exhibit.getName();

        TextView textView = view.findViewById(R.id.exhibitName);
        textView.setText(str);
        return view;
    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = exhibits.size();
                    filterResults.values = exhibits;
                }
                else {
                    String searchStr = constraint.toString().toLowerCase();
                    ArrayList<Exhibit> resultData = new ArrayList<>();

                    for (Exhibit e : exhibits) {
                        if (e.getName().toLowerCase().contains(searchStr))
                            resultData.add(e);
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredExhibits = (ArrayList<Exhibit>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}
