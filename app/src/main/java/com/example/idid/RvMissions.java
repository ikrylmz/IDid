package com.example.idid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RvMissions extends RecyclerView.Adapter<RvMissions.TypesListHolder> {

    private LayoutInflater inflater;
    static List<MissionInfo> listData;
    private RvMissions.IButtonClick mButtonClick;

    public interface IButtonClick
    {
        void click(MissionInfo missionInfo);
    }
    public RvMissions(List<MissionInfo> listData,IButtonClick mButtonClick, Context context) {

        if (context == null)
            return;
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        this.mButtonClick = mButtonClick;
    }

    @Override
    public RvMissions.TypesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_missions, parent, false);
        return new RvMissions.TypesListHolder(view);
    }

    @Override
    public void onBindViewHolder(final RvMissions.TypesListHolder holder, final int position) {

            MissionInfo item = listData.get(position);
            if (item != null) {

                holder.txt_name.setText(item.getName());
                holder.txt_subject.setText(item.getSubject());
                holder.txt_phoneNumber.setText(item.getPhoneNumber());

            }

        else {Log.e("Contact in Adapder","Contact is  null,List position is given as final");
        return;
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            MissionInfo item = listData.get(position);
            @Override
            public void onClick(View view) {
                mButtonClick.click(item);
            }
            });

            /*
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.setChecked(true);
                    Conctact conctact = listData.get(position);
                    mListener.onItemClicked(conctact);

                }
            });

             */
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TypesListHolder extends RecyclerView.ViewHolder {

        private TextView txt_subject,txt_name,txt_phoneNumber;
        private ImageView iv_green,iv_black;
        LinearLayout linearLayout;

        public TypesListHolder(View itemView) {
            super(itemView);
            txt_subject = (TextView) itemView.findViewById(R.id.subject_txt);
            txt_name = (TextView) itemView.findViewById(R.id.user_name_m_txt);
            iv_green = (ImageView)itemView.findViewById(R.id.greenLight_iv);
            iv_black = (ImageView)itemView.findViewById(R.id.blackLight_iv);
            txt_phoneNumber = (TextView)itemView.findViewById(R.id.phoneNumber_txt);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}

