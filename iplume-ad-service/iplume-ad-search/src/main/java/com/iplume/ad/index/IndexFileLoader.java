package com.iplume.ad.index;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.dump.DtConstant;
import com.iplume.ad.dump.table.*;
import com.iplume.ad.handler.AdLevelDataHandler;
import com.iplume.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 索引文件加载处理类.
 * <p>
 * 注解的方法在整个Bean初始化中的执行顺序:
 * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
 *
 * @author: lingchen
 * @date: 2021/5/14
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    /**
     * 索引加载方法.
     * 加载顺序:Level2 > Level3 > Level4.
     */
    @PostConstruct
    public void init() {

        // 读取AdPlan索引.
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_PLAN)
        );

        // Level2:AdPlan索引处理.
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));

        // 读取AdPlan索引.
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_CREATIVE)
        );

        // Level2:AdCreative索引处理.
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpType.ADD
        ));

        // 读取AdUnit索引.
        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_UNIT)
        );

        // Level3:AdUnit索引处理.
        adUnitStrings.forEach(c -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(c, AdUnitTable.class),
                OpType.ADD
        ));

        // 读取AdCreativeUnit索引.
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_CREATIVE_UNIT)
        );

        // Level3:AdCreativeUnit索引处理.
        adCreativeUnitStrings.forEach(c -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(c, AdCreativeUnitTable.class),
                OpType.ADD
        ));

        // 读取AdUnitDistrict索引.
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_UNIT_DISTRICT)
        );

        // Level4:AdUnitDistrict索引处理.
        adUnitDistrictStrings.forEach(c -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(c, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        // 读取AdUnitIt索引.
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_UNIT_IT)
        );

        // Level4:AdUnitIt索引处理.
        adUnitItStrings.forEach(c -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(c, AdUnitItTable.class),
                OpType.ADD
        ));

        // 读取AdUnitKeyword索引.
        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DtConstant.DATA_ROOT_DIR,
                        DtConstant.AD_UNIT_KEYWORD)
        );

        // Level4:AdUnitKeyword索引处理.
        adUnitKeywordStrings.forEach(c -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(c, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    /**
     * 加载索引文件.
     *
     * @param fileName 文件路径.
     * @return 索引文件内容列表.
     */
    private List<String> loadDumpData(String fileName) {

        // 读取文件中所有行,返回一个String列表.
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
