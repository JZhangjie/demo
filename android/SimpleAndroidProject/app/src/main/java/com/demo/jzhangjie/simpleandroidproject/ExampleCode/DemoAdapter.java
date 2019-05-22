package com.demo.jzhangjie.simpleandroidproject.ExampleCode;

import android.view.View;

import java.util.List;

/**
 * Created by admin on 2017/9/14.
 */

public class DemoAdapter {
    //region 属性字段
    //数据字段
    List<Object> mList;
    //事件监听对象
    private DemoAdapterListener listener;
    //endregion

    //region 对外接口方法，这里定义该类需要对外提供的共有方法。
    //endregion

    //region 内部功能实现方法，内部功能性方法的实现
    //endregion

    //region 继承函数，Adapter的实现一般需要继承具体的类，如BaseAdapter等
    //endregion

    //region 事件监听，此处定义的Listener需要在Activity中使用匿名类实现，以此来调用activity中的方法实现对于界面的操作。对于界面的操作，都放在对应的activity中实现，可以防止逻辑混乱。
    public static abstract class DemoAdapterListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            click();
        }
        public abstract void click();
    }
    //endregion
}
