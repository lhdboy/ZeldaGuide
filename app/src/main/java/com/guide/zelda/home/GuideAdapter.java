package com.guide.zelda.home;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guide.zelda.R;
import com.guide.zelda.db.model.GuideModel;

import java.util.List;

public class GuideAdapter extends BaseQuickAdapter<GuideModel, BaseViewHolder> {

    private Context context;

    public GuideAdapter(Context context, List<GuideModel> data) {
        super(R.layout.layout_item_guide, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, GuideModel item) {
        holder.setText(R.id.tv_title, item.name);
        Glide.with(context).load("file:///android_asset/Guide/" + item.image_name)
                .placeholder(R.drawable.shape_place_holder_rect)
                .error(R.drawable.shape_place_holder_rect)
                .into((ImageView) holder.getView(R.id.iv_icon));
    }

}