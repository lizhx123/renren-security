package io.renren.modules.sys.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysMemDao;
import io.renren.modules.sys.entity.SysMemEntity;
import io.renren.modules.sys.service.SysMemService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("sysMemService")
public class SysMemServiceImpl extends ServiceImpl<SysMemDao, SysMemEntity> implements SysMemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysMemEntity> page = this.selectPage(
                new Query<SysMemEntity>(params).getPage(),
                new EntityWrapper<SysMemEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception  {

        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet==null){
            return  true;
        }
        SysMemEntity sysmementity;
        List<SysMemEntity> memlist = new ArrayList<SysMemEntity>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {//r=1 去掉首行
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            sysmementity = new  SysMemEntity();
            if( row.getCell(0).getCellType() !=1){
                throw new Exception("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");
            }
            String name = row.getCell(0).getStringCellValue();

            if(name == null || name.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,姓名未填写)");
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String type = row.getCell(1).getStringCellValue();
            if(type==null || type.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,类型未填写)");
            }
            String card = row.getCell(2).getStringCellValue();
            String summary = row.getCell(3).getStringCellValue();
            sysmementity.setCard(card);
            sysmementity.setName(name);
            sysmementity.setSummary(summary);
            sysmementity.setType(type);
            memlist.add(sysmementity);
        }
        boolean b = this.insertBatch(memlist);
        return b;
    }

}
