package jiyun.com.recyclertest.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import jiyun.com.recyclertest.Entity.FilmBean;
import jiyun.com.recyclertest.R;
import jiyun.com.recyclertest.SetItemRecyclerViewListener;

public class FilmItemAdapter extends BaseAdapter<FilmBean.MoviesBean> {
    private ImageView mMasterImage, m3dImage, mMaxImage, mBigImage;
    private Boolean flag = true;
    private SetItemRecyclerViewListener listner;
    private Button button;

    public FilmItemAdapter(Context context, List<FilmBean.MoviesBean> datas) {
        super(context, R.layout.activity_film, datas);
    }

    public void setItemListner(SetItemRecyclerViewListener listner) {
        if (listner != null) {
            this.listner = listner;
        }
    }

    @Override
    public void convert(ViewHolder holder, FilmBean.MoviesBean moviesBean) {
        mMasterImage = (ImageView) holder.itemView.findViewById(R.id.Film_Image);
        m3dImage = (ImageView) holder.itemView.findViewById(R.id.Image_3D);
        mMaxImage = (ImageView) holder.itemView.findViewById(R.id.Image_MAX);
        mBigImage = (ImageView) holder.itemView.findViewById(R.id.Image_Big);
        holder.setText(R.id.Film_Title, moviesBean.getTitleCn());
        holder.setText(R.id.Film_Content,"“" + moviesBean.getCommonSpecial());
        holder.setText(R.id.Film_Time, moviesBean.getRMonth() + "月" + moviesBean.getRDay() + "日上映");
        //holder.setText(R.id.Film_Release, moviesBean.getTitleCn());
        Glide.with(context).load(moviesBean.getImg()).into(mMasterImage);
        if (moviesBean.isIs3D() == flag) {
            Glide.with(context).load(R.mipmap.is3d).into(m3dImage);
        } else {
            Glide.with(context).load("").into(m3dImage);
        }
        if (moviesBean.isIsIMAX() == flag) {
            Glide.with(context).load(R.mipmap.ismax).into(mMaxImage);
        } else {
            Glide.with(context).load("").into(mMaxImage);
        }
        if (moviesBean.isIsIMAX() == flag) {
            Glide.with(context).load(R.mipmap.isbig).into(mBigImage);
        } else {
            Glide.with(context).load("").into(mBigImage);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.setItemRecyclerViewListner(v, position);
                }
            }
        });
        button = holder.getView(R.id.Film_Btn_Detail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.setItemRecyclerViewListner(v, position);
                }
            }
        });
    }
}