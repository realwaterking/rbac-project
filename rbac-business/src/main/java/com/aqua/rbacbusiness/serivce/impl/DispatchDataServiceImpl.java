package com.aqua.rbacbusiness.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbacbusiness.model.entity.DispatchData;
import com.aqua.rbacbusiness.serivce.DispatchDataService;
import com.aqua.rbacbusiness.mapper.DispatchDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 70742
* @description 针对表【dispatch_data(出警记录表)】的数据库操作Service实现
* @createDate 2024-02-11 22:57:29
*/
@Service
public class DispatchDataServiceImpl extends ServiceImpl<DispatchDataMapper, DispatchData>
    implements DispatchDataService{

    @Resource
    private DispatchDataMapper dispatchDataMapper;

    @Override
    public Long getTotalDispatch() {
        return dispatchDataMapper.selectCount(null);
    }

    @Override
    public Long getSuccessfulDispatch() {
        QueryWrapper<DispatchData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("result", "处理成功");
        Long count = dispatchDataMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public Long getFailedDispatch() {
        QueryWrapper<DispatchData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("result", "处理失败");
        Long count = dispatchDataMapper.selectCount(queryWrapper);
        return count;
    }
}




