package com.test.helloworld.test4android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.test.helloworld.test4android.network.RequestCallback;
import com.test.helloworld.test4android.network.RetrofitRequestUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkFrameActivity extends AppCompatActivity implements RequestCallback {


    @BindView(R.id.get_network_frame_btn)
    Button mGetReqNetworkFrameBtn;

    @BindView(R.id.get_rxjava_network_frame_btn)
    Button mGetRxjavaReqNetworkFrameBtn;
    @BindView(R.id.result_network_frame_tv)
    TextView mResultNetworkFrameTv;

    @OnClick({R.id.get_network_frame_btn, R.id.get_rxjava_network_frame_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.get_network_frame_btn:
                requestUtil.request();
                break;
            case R.id.get_rxjava_network_frame_btn:
                requestUtil.request2();
                break;
        }
    }

    RetrofitRequestUtil requestUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_frame_activity);
        ButterKnife.bind(this);
        requestUtil = new RetrofitRequestUtil(this);


    }

    @Override
    public void onSuccess(String s) {
        mResultNetworkFrameTv.setText(s);
    }

    @Override
    public void onFailed(String s) {
        mResultNetworkFrameTv.setText(s);
    }


}
