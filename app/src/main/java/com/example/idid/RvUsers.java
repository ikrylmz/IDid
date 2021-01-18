package com.example.idid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RvUsers extends RecyclerView.Adapter<RvUsers.TypesListHolder> {

    private LayoutInflater inflater;
    static List<User> listData;
    private IButtonClick mButtonClick;

    public interface IButtonClick
    {
        void click(User user);
    }

    public RvUsers(ArrayList<User> listData,IButtonClick mButtonClick ,Context context) {

        if (context == null)
            return;
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        this.mButtonClick = mButtonClick;

    }

    @Override
    public RvUsers.TypesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_users, parent, false);
        return new RvUsers.TypesListHolder(view);
    }

    @Override
    public void onBindViewHolder(final RvUsers.TypesListHolder holder, final int position) {

            User item = listData.get(position);
            if (item != null) {
                holder.txt_name.setText(item.getName());
            }
            holder.btn_choose.setOnClickListener(new View.OnClickListener() {
                User item = listData.get(position);
                @Override
                public void onClick(View view) {
                    mButtonClick.click(item);
                }
            });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TypesListHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;
        private ImageView iv_tick;
        private Button btn_choose;

        public TypesListHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.userName_txt);
            iv_tick = (ImageView)itemView.findViewById(R.id.tick_iv);
            btn_choose = (Button)itemView.findViewById(R.id.choose_btn);
        }
    }
}

