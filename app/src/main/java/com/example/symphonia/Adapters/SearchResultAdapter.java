package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;

import java.util.ArrayList;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private ArrayList<Container> container;
    private Boolean chooseImg;
    private Boolean setText;
    private ListItemClickListner listner;

    public interface ListItemClickListner{
        void onItemEraseListener(int pos,int containerSize);//handle clicking on close image
    }

    /**
     *constructor of the adapter
     * @param data Arraylist of Container which has the data of the adapter
     * @param b if true show enter image else show close image
     * @param l context
     */
    public SearchResultAdapter(ArrayList<Container> data,Boolean b,ListItemClickListner l) {
        container = data;
        chooseImg = b;
        setText=false;
        listner=l;
    }

    /**
     *constructor of adapter
     * @param data Arraylist of Container which has the data of the adapter
     * @param b if true show enter image else show close image
     * @param a if true the text in the second textview will be shortened
     */
    public SearchResultAdapter(ArrayList<Container> data,Boolean b,Boolean a) {
        container = data;
        chooseImg = b;
        setText=a;
    }

    /**
     *Called when RecyclerView needs a new RecyclerView.ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SearchResultViewHolder
     */
    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_item, parent, false);
        SearchResultViewHolder VH = new SearchResultViewHolder(V);
        return VH;
    }

    /**
     *Called by RecyclerView when it starts observing this Adapter.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, final int position) {
        holder.MakeResult(position);
       holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.remove(position);
                listner.onItemEraseListener(position,container.size());
            }
        });
    }

    /**
     * @return return number of items in recyclerview which will be the Arraylist size
     */
    @Override
    public int getItemCount() {
        return container.size();
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;
        private ImageView imageView;
        private ImageView show;
        private ImageView close;
        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            //attach views
            textView=(TextView)itemView.findViewById(R.id.tv_search_list_item);
            textView2=(TextView)itemView.findViewById(R.id.tv_search_list_item_type);
            imageView=(ImageView)itemView.findViewById(R.id.img_search_list_item);
            show=(ImageView)itemView.findViewById(R.id.img_show);
            close=(ImageView)itemView.findViewById(R.id.img_close);
        }

        /**
         *set text and recources in the views of the adapter's item
         * @param pos position of the adapter item
         */
        public void MakeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            if(setText){
                textView2.setText(temp.getCat_Name2().substring(temp.getCat_Name2().indexOf(".") + 1));
            }
            else {
                textView2.setText(temp.getCat_Name2());
            }
            imageView.setImageBitmap(temp.getImg_Res());
            if(chooseImg){
                show.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
            }
            else {
                show.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
            }
        }
    }
}
