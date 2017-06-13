package com.baibao.cloud.present;


import com.baibao.cloud.SynergyMerItem;
import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.WDTMerItem;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfo;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongOrderMerPost;
import com.baibao.cloud.bean.WandiantongOrderPost;

import java.util.ArrayList;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IDbPresent {
    void InsertReplaceWDTMer(WandiantongItemInfo item);
    void GetWDTMer();
    void InsertReplaceSynergyOrder(SynergyOrderInfo item);
    void InsertReplaceSynergyMer(SynergyOrderItemInfo item);
    void DeleteSynergyOrder(SynergyOrderInfo item);
    void DeleteSynergyMer(SynergyMerItem item);

    void GetSynergyOrder();
    void GetSynergyMer();
}
