package com.baibao.cloud.present;


import com.baibao.cloud.bean.CheckPayInfo;
import com.baibao.cloud.bean.LoginInfoRequest;
import com.baibao.cloud.bean.PayInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfoTest;
import com.baibao.cloud.bean.SynergyOrderPostItemInfo;
import com.baibao.cloud.bean.SynergyPayBack;
import com.baibao.cloud.bean.SynergyRequest;
import com.baibao.cloud.bean.WandiantongOrderMerPost;
import com.baibao.cloud.bean.WandiantongOrderPost;

import java.util.ArrayList;

import okhttp3.RequestBody;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IRequestPresent {
    void QueryOder(SynergyRequest synergyRequest);
    void QueryOderSingle(String order_no);

    void Login(String username,String password,String company);
    void FetchOrderAdd(String secret,String company_id,String ucode);
    void CommitOrder(String  data);
    void CommitOrderTest(SynergyOrderPostInfoTest data);

    void PostWDTOrder(WandiantongOrderPost wandiantongOrderPost);
    void PostWDTOrderMer(WandiantongOrderMerPost wandiantongOrderMerPost);
    void PostWDTOrderStatus(String secret,String paystate,String code,String ucode);

}
