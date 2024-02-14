package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firedata.FireDataAddRequest;
import com.aqua.rbacbusiness.model.dto.firedata.FireDataQueryRequest;
import com.aqua.rbacbusiness.model.dto.firedata.FireDataUpdateRequest;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.vo.FireDataVO;
import com.aqua.rbacbusiness.serivce.FireDataService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/8
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class FireDataController {

    @Autowired
    private FireDataService fireDataService;

    /**
     * 添加消防数据
     * @param fireDataAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加消防数据的权限", requirePermission = "fireData:add")
    public BaseResponse<Long> addFireData(@RequestBody FireDataAddRequest fireDataAddRequest) {
        if (fireDataAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireData fireData = new FireData();
        BeanUtils.copyProperties(fireDataAddRequest, fireData);
        boolean result = fireDataService.save(fireData);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(fireData.getId());
    }

    /**
     * 删除消防数据
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除消防数据的权限", requirePermission = "fireData:delete")
    public BaseResponse<Boolean> deleteFireData(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = fireDataService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新消防数据
     * @param fireDataUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新消防报警数据", requirePermission = "fireData:update")
    public BaseResponse<Boolean> updateFireData(@RequestBody FireDataUpdateRequest fireDataUpdateRequest) {
        if (fireDataUpdateRequest == null || fireDataUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireData fireData = new FireData();
        BeanUtils.copyProperties(fireDataUpdateRequest, fireData);
        boolean result = fireDataService.updateById(fireData);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取单个消防报警信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个消防报警信息的权限", requirePermission = "fireData:selectOne")
    public BaseResponse<FireDataVO> getFireDataById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireData fireData = fireDataService.getById(id);
        FireDataVO fireDataVO = new FireDataVO();
        BeanUtils.copyProperties(fireData, fireDataVO);
        return ResultUtils.success(fireDataVO);
    }

    /**
     * 获取消防报警信息列表
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取消防报警信息列表", requirePermission = "fireData:list")
    public BaseResponse<List<FireDataVO>> listFireData() {
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        List<FireData> fireDataList = fireDataService.list(queryWrapper);
        List<FireDataVO> list = new ArrayList<>();
        for (FireData fireData : fireDataList) {
            if (fireData != null) {
                FireDataVO fireDataVO = new FireDataVO();
                BeanUtils.copyProperties(fireData,fireDataVO);
                list.add(fireDataVO);
            }
        }
        return ResultUtils.success(list);
    }

    /**
     * 分页查询报警信息
     * @param fireDataQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防报警信息的权限", requirePermission = "fireData:selectByPage")
    public BaseResponse<Page<FireDataVO>> listFireDataByPage(FireDataQueryRequest fireDataQueryRequest) {
        long current = 1;
        long size = 5;
        if (fireDataQueryRequest != null) {
            current = fireDataQueryRequest.getCurrent();
            size = fireDataQueryRequest.getPageSize();
        }
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        Page<FireData> fireDataPage = fireDataService.page(new Page<>(current, size), queryWrapper);
        Page<FireDataVO> fireDataVOPage = new PageDTO<>(fireDataPage.getCurrent(), fireDataPage.getSize(), fireDataPage.getTotal());
        List<FireDataVO> fireDataVOList = fireDataPage.getRecords().stream().map(fireData -> {
            FireDataVO fireDataVO = new FireDataVO();
            BeanUtils.copyProperties(fireData, fireDataVO);
            return fireDataVO;
        }).collect(Collectors.toList());
        fireDataVOPage.setRecords(fireDataVOList);
        return ResultUtils.success(fireDataVOPage);
    }
}
