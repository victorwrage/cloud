package com.baibao.cloud.utils;

import com.baibao.cloud.bean.SynergyOrderInfo;

import java.util.Comparator;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/27 9:37
 */

public class SortOrderStateComparator implements Comparator<SynergyOrderInfo> {

    @Override
    public int compare(SynergyOrderInfo o1, SynergyOrderInfo o2) {

        if ( o1.getJhzt() <= o2.getJhzt()) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
