package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.dispatchdata.DispatchDataAddRequest;
import com.aqua.rbacbusiness.model.dto.dispatchdata.DispatchDataQueryRequest;
import com.aqua.rbacbusiness.model.dto.dispatchdata.DispatchDataUpdateRequest;
import com.aqua.rbacbusiness.model.entity.DispatchData;
import com.aqua.rbacbusiness.model.vo.DispatchDataVO;
import com.aqua.rbacbusiness.serivce.DispatchDataService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/11
 */
@RestController
@RequestMapping("/dispatch")
@Slf4j
public class DispatchDataController {

    @Autowired
    private DispatchDataService dispatchDataService;

    /**
     * 添加出警信息
     * @param dispatchDataAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加出警信息的权限", requirePermission = "dispatchData:add")
    public BaseResponse<Long> addDispatchData(@RequestBody DispatchDataAddRequest dispatchDataAddRequest) {
        if (dispatchDataAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        DispatchData dispatchData = new DispatchData();
        BeanUtils.copyProperties(dispatchDataAddRequest, dispatchData);
        boolean result = dispatchDataService.save(dispatchData);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(dispatchData.getId());
    }

    /**
     * 删除出警信息
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除出警信息的权限", requirePermission = "dispatchData:delete")
    public BaseResponse<Boolean> deleteDispatchData(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = dispatchDataService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新出警信息
     * @param dispatchDataUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新出警信息的权限", requirePermission = "dispatchData:update")
    public BaseResponse<Boolean> updateDispatchData(@RequestBody DispatchDataUpdateRequest dispatchDataUpdateRequest) {
        if (dispatchDataUpdateRequest == null || dispatchDataUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        DispatchData dispatchData = new DispatchData();
        BeanUtils.copyProperties(dispatchDataUpdateRequest, dispatchData);
        boolean result = dispatchDataService.updateById(dispatchData);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取出警信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个出警信息的权限", requirePermission = "dispatchData:selectOne")
    public BaseResponse<DispatchDataVO> getDispatchDataById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        DispatchData dispatchData = dispatchDataService.getById(id);
        DispatchDataVO dispatchDataVO = new DispatchDataVO();
        BeanUtils.copyProperties(dispatchData, dispatchDataVO);
        return ResultUtils.success(dispatchDataVO);
    }

    /**
     * 获取出警信息列表
     * @param dispatchDataQueryRequest
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取出警信息列表的权限", requirePermission = "dispatchData:selectList")
    public BaseResponse<List<DispatchDataVO>> listDispatchData(DispatchDataQueryRequest dispatchDataQueryRequest) {
        DispatchData dispatchDataQuery = new DispatchData();
        if (dispatchDataQueryRequest != null) {
            BeanUtils.copyProperties(dispatchDataQueryRequest, dispatchDataQuery);
        }
        QueryWrapper<DispatchData> queryWrapper = new QueryWrapper<>(dispatchDataQuery);
        List<DispatchData> dispatchDataList = dispatchDataService.list(queryWrapper);
        List<DispatchDataVO> dispatchDataVOList = dispatchDataList.stream().map(dispatchData -> {
            DispatchDataVO dispatchDataVO = new DispatchDataVO();
            BeanUtils.copyProperties(dispatchData, dispatchDataVO);
            return dispatchDataVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(dispatchDataVOList);
    }

    /**
     * 分页查询出警信息
     * @param dispatchDataQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询出警信息的权限", requirePermission = "dispatchData:selectByPage")
    public BaseResponse<Page<DispatchDataVO>> listDispatchDataByPage(DispatchDataQueryRequest dispatchDataQueryRequest) {
        long current = 1;
        long size = 5;
        DispatchData dispatchDataQuery = new DispatchData();
        if (dispatchDataQueryRequest != null) {
            BeanUtils.copyProperties(dispatchDataQueryRequest, dispatchDataQuery);
            current = dispatchDataQueryRequest.getCurrent();
            size = dispatchDataQueryRequest.getPageSize();
        }
        QueryWrapper<DispatchData> queryWrapper = new QueryWrapper<>(dispatchDataQuery);
        Page<DispatchData> dispatchDataPage = dispatchDataService.page(new Page<>(current, size), queryWrapper);
        Page<DispatchDataVO> dispatchDataVOPage = new PageDTO<>(dispatchDataPage.getCurrent(), dispatchDataPage.getSize(), dispatchDataPage.getTotal());
        List<DispatchDataVO> dispatchDataVOList = dispatchDataPage.getRecords().stream().map(dispatchData -> {
            DispatchDataVO dispatchDataVO = new DispatchDataVO();
            BeanUtils.copyProperties(dispatchData, dispatchDataVO);
            return dispatchDataVO;
        }).collect(Collectors.toList());
        dispatchDataVOPage.setRecords(dispatchDataVOList);
        return ResultUtils.success(dispatchDataVOPage);
    }
}
