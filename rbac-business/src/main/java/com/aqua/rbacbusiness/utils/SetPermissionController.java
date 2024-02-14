package com.aqua.rbacbusiness.utils;

import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author water king
 * @time 2024/2/12
 */
@RestController
@RequestMapping("/set/permission")
@Slf4j
public class SetPermissionController {

    @Autowired
    private FireDataService fireDataService;

    @PostMapping("/load")
    public BaseResponse<String> load() {
        fireDataService.load();
        return ResultUtils.success("ok");
    }
}
