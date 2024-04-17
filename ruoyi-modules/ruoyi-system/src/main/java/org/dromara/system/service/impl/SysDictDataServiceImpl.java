package org.dromara.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.constant.CacheNames;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.redis.utils.CacheUtils;
import org.dromara.system.domain.SysDictData;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.domain.vo.SysDictEnumVo;
import org.dromara.system.mapper.SysDictDataMapper;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典 业务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    private final SysDictDataMapper baseMapper;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public TableDataInfo<SysDictDataVo> selectPageDictDataList(SysDictDataBo dictData, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictData);
        Page<SysDictDataVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictDataVo> selectDictDataList(SysDictDataBo dictData) {
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictData);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysDictData> buildQueryWrapper(SysDictDataBo bo) {
        LambdaQueryWrapper<SysDictData> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDictSort() != null, SysDictData::getDictSort, bo.getDictSort());
        lqw.like(StringUtils.isNotBlank(bo.getDictLabel()), SysDictData::getDictLabel, bo.getDictLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getDictType()), SysDictData::getDictType, bo.getDictType());
        lqw.orderByAsc(SysDictData::getDictSort);
        return lqw;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysDictData>()
                .select(SysDictData::getDictLabel)
                .eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getDictValue, dictValue))
            .getDictLabel();
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictDataVo selectDictDataById(Long dictCode) {
        return baseMapper.selectVoById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictData data = baseMapper.selectById(dictCode);
            baseMapper.deleteById(dictCode);
            CacheUtils.evict(CacheNames.SYS_DICT, data.getDictType());
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> insertDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        int row = baseMapper.insert(data);
        if (row > 0) {
            return baseMapper.selectDictDataByType(data.getDictType());
        }
        throw new ServiceException("操作失败");
    }

    /**
     * 修改保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> updateDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        int row = baseMapper.updateById(data);
        if (row > 0) {
            return baseMapper.selectDictDataByType(data.getDictType());
        }
        throw new ServiceException("操作失败");
    }

    @Override
    public List<SysDictEnumVo> generateEnum(String dictType) {
        List<SysDictEnumVo> res = new ArrayList<>();
        //生成Java枚举类
        //组织数据
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enumTypeName", toPascalCase(dictType)+"Enum");
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        List<SysDictData> sysDictDataList = baseMapper.selectList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, dictType));
        sysDictDataList.forEach(dictData->{
            HashMap<String, Object> item = new HashMap<>();
            item.put("eng", dictData.getDictEng());
            item.put("value", dictData.getDictValue());
            item.put("label", dictData.getDictLabel());
            items.add(item);
        });
        model.put("items", items);
        //填充
        try {
            Template template = freemarkerConfig.getTemplate("enumTemplate.ftl");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            SysDictEnumVo enumVo = new SysDictEnumVo();
            enumVo.setName("java");
            enumVo.setContent(s);
            res.add(enumVo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //生成Ts枚举类
        try {
            Template template = freemarkerConfig.getTemplate("enumTemplateTs.ftl");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            SysDictEnumVo enumVo = new SysDictEnumVo();
            enumVo.setName("ts");
            enumVo.setContent(s);
            res.add(enumVo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    /**
     * 下划线转大驼峰
     *
     * @param input 输入
     * @return {@link String}
     */
    public static String toPascalCase(String input) {
        if (input == null) {
            return null;
        }

        String[] parts = input.split("_");
        StringBuilder sb = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            sb.append(Character.toUpperCase(part.charAt(0)));
            sb.append(part.substring(1).toLowerCase());
        }

        return sb.toString();
    }

}
