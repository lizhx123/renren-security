package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.SysMemEntity;
import io.renren.modules.sys.service.SysMemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 会员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-31 15:07:12
 */
@RestController
@RequestMapping("sys/sysmem")
public class SysMemController {
    @Autowired
    private SysMemService sysMemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysmem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysmem:info")
    public R info(@PathVariable("id") Long id){
        SysMemEntity sysMem = sysMemService.selectById(id);

        return R.ok().put("sysMem", sysMem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysmem:save")
    public R save(@RequestBody SysMemEntity sysMem){
        sysMemService.insert(sysMem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysmem:update")
    public R update(@RequestBody SysMemEntity sysMem){
        ValidatorUtils.validateEntity(sysMem);
        sysMemService.updateAllColumnById(sysMem);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysmem:delete")
    public R delete(@RequestBody Long[] ids){
        sysMemService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导入excel
     */
    @RequestMapping(value="/import", method = RequestMethod.POST)
    public R uploadFile(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception{
        if(file==null)
            return R.error(1, "上传文件不能为空");
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return R.error(2, "上传文件格式错误，请上传后缀为.xls或.xlsx的文件");
        }
        boolean a = false;
        try {
            a = sysMemService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(a){
            return R.error(0, "导入成功");
        }else{
            return R.error(3, "导入数据异常");
        }

    }

}
