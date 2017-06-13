package com.baibao.cloud.view;


import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.WandiantongItemInfo;

import java.util.List;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IOrderItemView extends IView{

    void ResolveOrderItem(List<SynergyOrderItemInfo> items);

}
