package com.example.abirhasan.finaltest.ui.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.models.BaseTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvHeader)
    TextView tvHeader;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(BaseTask baseTask) {
        tvHeader.setText(baseTask.getTitle());
    }
}
