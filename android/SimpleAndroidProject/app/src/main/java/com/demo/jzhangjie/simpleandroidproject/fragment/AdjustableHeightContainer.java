package com.demo.jzhangjie.simpleandroidproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.demo.jzhangjie.simpleandroidproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdjustableHeightContainer extends Fragment implements View.OnClickListener, View.OnTouchListener {
    //region 界面对象
    private View mRootView;
    private ImageButton mTitleButton;
    private ImageButton mCloseButton;
    private TextView mTitleText;
    private FrameLayout mContent;
    private FrameLayout mRoot;
    //endregion

    // region 其他对象
    private AdjustableHeightContainerCallback callback;
    private List<Object> mContents = new ArrayList<>();
    private List<String> mKeys = new ArrayList<>();
    private int lastY;
    private int minY = 100;
    private int topY = 200;
    //endregion

    public AdjustableHeightContainer() {
    }



    //region 对外接口方法
    public void close() {
        this.getActivity().getSupportFragmentManager().beginTransaction().hide(this).commit();
    }

    public void add(String key, Object object) {
        if (!mContents.contains(object)) {
            int i = mContents.indexOf(object);
            if (i == -1) {
                mKeys.add(key);
                mContents.add(object);
            } else {
                mContents.remove(i);
                mContents.add(i, object);
            }
            if (object instanceof android.support.v4.app.Fragment) {
                android.support.v4.app.Fragment f = (android.support.v4.app.Fragment) object;
                this.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_content, f, key).hide(f).commit();
            }
            if (object instanceof View) {
                View v = (View) object;
                this.mContent.addView(v);
            }
        }
    }

    public void show(String key) {
        this.getActivity().getSupportFragmentManager().beginTransaction().show(this).commit();
        if (mKeys.contains(key)) {
            //隐藏所有对象
            Object object = mContents.get(mKeys.indexOf(key));
            for (Object o : mContents) {
                if (o instanceof android.support.v4.app.Fragment) {
                    if (o == object) {
                        this.getActivity().getSupportFragmentManager().beginTransaction().show((android.support.v4.app.Fragment) o).commit();
                    } else {
                        this.getActivity().getSupportFragmentManager().beginTransaction().hide((android.support.v4.app.Fragment) o).commit();
                    }
                }
                if (o instanceof View) {
                    ((View) o).setVisibility(View.GONE);
                    if (o == object) {
                        ((View) o).setVisibility(View.VISIBLE);
                    }
                }
            }
            //显示当前对象
            this.mTitleText.setText(key);
        }
    }
    //endregion

    //region 初始化函数
    private void initView() {
        mTitleButton = (ImageButton) mRootView.findViewById(R.id.container_move);
        mCloseButton = (ImageButton) mRootView.findViewById(R.id.container_close);
        mContent = (FrameLayout) mRootView.findViewById(R.id.container_content);
        mRoot = (FrameLayout) mRootView.findViewById(R.id.container_root);
        mTitleText = (TextView) mRootView.findViewById(R.id.container_text);
        mTitleButton.setOnTouchListener(this);
        mTitleText.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);
    }
    //endregion

    //region 生命周期回调函数
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AdjustableHeightContainerCallback)
        {
            callback = (AdjustableHeightContainerCallback)context;
        }
        else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_adjustable_height_container, container, false);
        initView();
        return mRootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
    //endregion

    //region 事件响应函数
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int move = lastY - y;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mRoot.getLayoutParams();
                if (y > topY) {
                    params.height = params.height + move;
                }
                if (params.height < minY) {
                    params.height = minY + 1;
                }
                mRoot.setLayoutParams(params);
                lastY = y;
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container_close:
                close();
                break;
            case R.id.container_text:
                String key = this.mTitleText.getText().toString().trim();
                int i = mKeys.indexOf(key);
                if (i != -1) {
                    i = i + 1;
                    if (i == mKeys.size())
                        i = 0;
                    String newkey = mKeys.get(i);
                    callback.TopButtonClick(key);
                    show(newkey);
                }
                break;
        }
    }
    //endregion

    public interface AdjustableHeightContainerCallback{
        void TopButtonClick(String key);
    }
}
