package com.aqua.rbacbusiness.serivce;

import com.aqua.rbacbusiness.model.entity.DispatchData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 70742
* @description 针对表【dispatch_data(出警记录表)】的数据库操作Service
* @createDate 2024-02-11 22:57:29
*/
public interface DispatchDataService extends IService<DispatchData> {

    /**
     * 获取总出警数
     * @return
     */
    Long getTotalDispatch();

    /**
     * 获取处理成功的数量
     * @return
     */
    Long getSuccessfulDispatch();

    /**
     * 获取处理失败的数量
     * @return
     */
    Long getFailedDispatch();
}
