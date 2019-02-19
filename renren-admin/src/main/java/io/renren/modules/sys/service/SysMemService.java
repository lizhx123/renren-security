package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMemEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 会员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-31 15:07:12
 */
public interface SysMemService extends IService<SysMemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}

