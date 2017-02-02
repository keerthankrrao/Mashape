package com.keerthan.mashape;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LENOVO on 30-01-2017.
 */

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder> {

    private ArrayList<Country>countries;
    private Context mContext;
    private onClickListener clickListener;

    public interface onClickListener
    {
        public void onclick(int position);
    }

    public CountryListAdapter(Context context, ArrayList<Country> countryArrayList,onClickListener listener) {
        this.mContext = context;
        this.countries = countryArrayList;
        this.clickListener = listener;
    }

    @Override
    public CountryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_items,null);
        CountryListViewHolder countryListViewHolder = new CountryListViewHolder(view);
        return countryListViewHolder;
    }

    @Override
    public void onBindViewHolder(CountryListViewHolder holder, final int position) {
        final Country countryObj = countries.get(position);
        holder.txtCountryName.setText("Country Name :"+countryObj.getCountryName());
        holder.txtCountryRegion.setText("Country Region :"+countryObj.getCountryRegion());
        holder.txtCountryPopulation.setText("Country Population :"+countryObj.getCountryPopulation());
        holder.txtCountryCapital.setText("Country Capital :"+countryObj.getCountryCapital());
        holder.txtCountryLatLang.setText("Country Co-ordintates :"+countryObj.getCountryLatLang());
        holder.txtCountryTimeZone.setText("Country Time-zone :"+countryObj.getCountryTimeZone());
        holder.txtCountrySubRegion.setText("Country Subregion :"+countryObj.getCountrySubRegion());
        holder.txtCountryDomain.setText("Domain Name :"+countryObj.getCountryDomain());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryListViewHolder extends RecyclerView.ViewHolder{
        TextView txtCountryName;
        TextView txtCountryCapital;
        TextView txtCountryRegion;
        TextView txtCountryPopulation;
        TextView txtCountrySubRegion;
        TextView txtCountryTimeZone;
        TextView txtCountryDomain;
        TextView txtCountryLatLang;

        public CountryListViewHolder(View itemView) {
            super(itemView);
            txtCountryName = (TextView) itemView.findViewById(R.id.textView);
            txtCountryCapital = (TextView) itemView.findViewById(R.id.textView2);
            txtCountryPopulation = (TextView) itemView.findViewById(R.id.textView3);
            txtCountryRegion = (TextView) itemView.findViewById(R.id.textView4);
            txtCountrySubRegion = (TextView) itemView.findViewById(R.id.textView5);
            txtCountryDomain = (TextView) itemView.findViewById(R.id.textView6);
            txtCountryTimeZone = (TextView) itemView.findViewById(R.id.textView7);
            txtCountryLatLang = (TextView) itemView.findViewById(R.id.textView8);
        }
    }
}
