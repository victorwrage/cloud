package com.baibao.cloud.view;


import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.WandiantongCallBack;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;
import com.socks.library.KLog;

import java.util.List;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IOrderView extends IView {

    void ResolveOrder(List<SynergyOrderInfo> items);

    void ResolveOrderAdd(WandiantongItemInfo item);

    void ResolveOrderCommit(SynergyOrderPostResult item);



    void ResolveWDTOrderPost(WandiantongOrderPostCallBack item);
    void ResolveWDTOrderMerPost(WandiantongOrderPostCallBack item);
    void ResolveWDTOrderStatusPost(WandiantongOrderPostCallBack item);






}
