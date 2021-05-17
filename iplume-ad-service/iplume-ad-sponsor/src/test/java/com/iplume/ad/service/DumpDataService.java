package com.iplume.ad.service;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.Application;
import com.iplume.ad.constant.CommonStatus;
import com.iplume.ad.dao.AdPlanRepository;
import com.iplume.ad.dao.AdUnitRepository;
import com.iplume.ad.dao.CreativeRepository;
import com.iplume.ad.dao.unitcondition.AdUnitDistrictRepository;
import com.iplume.ad.dao.unitcondition.AdUnitItRepository;
import com.iplume.ad.dao.unitcondition.AdUnitKeywordRepository;
import com.iplume.ad.dao.unitcondition.CreativeUnitRepository;
import com.iplume.ad.dump.DtConstant;
import com.iplume.ad.dump.table.*;
import com.iplume.ad.entity.AdPlan;
import com.iplume.ad.entity.AdUnit;
import com.iplume.ad.entity.Creative;
import com.iplume.ad.entity.unitcondition.AdUnitDistrict;
import com.iplume.ad.entity.unitcondition.AdUnitIt;
import com.iplume.ad.entity.unitcondition.AdUnitKeyword;
import com.iplume.ad.entity.unitcondition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据导出服务类(测试类).
 *
 * @description: RunWith -> 测试用例.
 * SpringBootTest -> 配置启动入口,web配置设置为无(webEnvironment : None).
 * @author: lingchen
 * @date: 2021/5/13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    // 数据表服务仓库类.

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitDistrictRepository districtRepository;
    @Autowired
    private AdUnitItRepository itRepository;
    @Autowired
    private AdUnitKeywordRepository keywordRepository;

    /**
     * 导出所有的Ad数据表索引.
     */
    @Test
    public void dumpAdTableData() {
        // AdPlan.
        dumpAdPlanTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_PLAN));
        // AdUnit.
        dumpAdUnitTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_UNIT));
        // AdCreative.
        dumpAdCreativeTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_CREATIVE));
        // AdCreativeUnit.
        dumpAdCreativeUnitTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_CREATIVE_UNIT));
        // AdUnitDistrict.
        dumpAdUnitDistrictTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_UNIT_DISTRICT));
        // AdUnitIt.
        dumpAdUnitItTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_UNIT_IT));
        // AdUnitIt.
        dumpAdUnitKeywordTable(String.format("%s%s", DtConstant.DATA_ROOT_DIR, DtConstant.AD_UNIT_DISTRICT));
    }

    /**
     * AdPlan的索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdPlanTable(String fileName) {

        // 获取有效状态的AdPlan列表.
        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        // 将adPlans列表装载入planTables中.
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(
                        p.getId(),
                        p.getUserId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate()
                )
        ));

        // 文件存储.
        saveFile(fileName, planTables, "DumpAdPlanTable");

        // Path path = Paths.get(fileName);
        // try (BufferedWriter writer = Files.newBufferedWriter(path)) {
        //     for (AdPlanTable table : planTables) {
        //         // 将每一个数据行存储为一个JSON格式的行.
        //         writer.write(JSON.toJSONString(table));
        //         writer.newLine();
        //     }
        //     writer.close();
        // } catch (IOException ex) {
        //     log.error("DumpAdPlanTable error.");
        // }
    }

    /**
     * AdUnit索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdUnitTable(String fileName) {

        // 查询有效状态的adUnit列表.
        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        // adUnits -> unitTables装载.
        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(
                        u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId()
                )
        ));

        // 文件存储.
        saveFile(fileName, unitTables, "DumpAdUnitTable");

        // Path path = Paths.get(fileName);
        // try (BufferedWriter writer = Files.newBufferedWriter(path)) {
        //     for (AdUnitTable table : unitTables) {
        //         // 将每一个数据行存储为一个JSON格式的行.
        //         writer.write(JSON.toJSONString(table));
        //         writer.newLine();
        //     }
        //     writer.close();
        // } catch (IOException ex) {
        //     log.error("DumpAdUnitTable error.");
        // }
    }

    /**
     * AdCreative索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdCreativeTable(String fileName) {

        // 查询所有的创意Creative列表.
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        // creatives -> creativeTables装载.
        List<AdCreativeTable> creativeTables = new ArrayList<AdCreativeTable>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getHeight(),
                        c.getWidth(),
                        c.getAuditStatus(),
                        c.getUrl()
                )
        ));

        // 文件存储.
        saveFile(fileName, creativeTables, "DumpAdCreativeTable");

        // Path path = Paths.get(fileName);
        // try (BufferedWriter writer = Files.newBufferedWriter(path)) {
        //     for (AdCreativeTable table : creativeTables) {
        //         // 将每一个数据行存储为一个JSON格式的行.
        //         writer.write(JSON.toJSONString(table));
        //         writer.newLine();
        //     }
        //     writer.close();
        // } catch (IOException ex) {
        //     log.error("DumpAdCreativeTable error.");
        // }

    }

    /**
     * AdCreativeUnit索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));

        // 文件存储.
        saveFile(fileName, creativeUnitTables, "DumpAdCreativeUnitTable");
    }

    /**
     * AdUnitDistrict索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> districts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(districts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        districts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));

        // 文件存储.
        saveFile(fileName, unitDistrictTables, "DumpAdUnitDistrictTable");
    }

    /**
     * AdUnitIt索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> itTables = new ArrayList<>();
        unitIts.forEach(i -> itTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));

        // 文件存储.
        saveFile(fileName, itTables, "DumpAdUnitItTable");
    }

    /**
     * AdUnitKeyword索引存储.
     *
     * @param fileName 文件路径.
     */
    private void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> keywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> keywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));

        // 文件存储.
        saveFile(fileName, keywordTables, "DumpAdUnitKeywordTable");
    }

    /**
     * 保存文件操作.
     *
     * @param fileName  文件路径.
     * @param tableList 索引存储列表.
     * @param dumpMsg   错误信息.
     */
    private static <T> void saveFile(String fileName, List<T> tableList, String dumpMsg) {
        // 文件存储.
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (T table : tableList) {
                // 将每一个数据行存储为一个JSON格式的行.
                writer.write(JSON.toJSONString(table));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("Dump " + dumpMsg + " error.");
        }
    }
}
