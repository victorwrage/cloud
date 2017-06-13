package com.baibao.cloud.view;


import com.baibao.cloud.SynergyMerItem;
import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.bean.DbResultBean;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IDbView extends IView {

    void ResolveInsertDb(int type,DbResultBean result);
    void ResolveReadDbSynergyOrder(int type, DbResultBean result, List<SynergyOrderItem> items);
    void ResolveReadDbSynergyMer(int type, DbResultBean result, List<SynergyMerItem> items);
    void ResolveDeleteDb(int type,DbResultBean result);

}
