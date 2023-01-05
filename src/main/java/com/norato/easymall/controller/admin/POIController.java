package com.norato.easymall.controller.admin;

import com.norato.easymall.entity.Product;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@Tag(name = "POI打印")
@RequestMapping("/admin")
public class POIController {
    private Logger logger = Logger.getLogger(POIController.class.getName());

    @GetMapping("/print")
    public void POI() {
        // 创建需要写入的数据列表
        List<Product> dataList = new ArrayList<>(2);
        Product dataVO = new Product();
        dataVO.setId("09f47493-214d-44bc-927d-6ce0bf89a057");
        dataVO.setName("爱疯9S(0315-01)");
        dataVO.setDescription("爱疯9S(0315-01)");
        dataVO.setCategory("手机数码");
        dataVO.setPrice(1000.0);
        dataVO.setSoldnum(8);
        Product dataV1 = new Product();
        dataV1.setId("3f36ac54-5da0-4cd8-9991-2ee86cc348c2");
        dataV1.setName("金士顿8G内存条");
        dataV1.setDescription("3级内存条，拿货220，数量有限！");
        dataV1.setCategory("手机数码");
        dataV1.setPrice(300.0);
        dataV1.setSoldnum(5);
        Product dataV2 = new Product();
        dataV2.setId("103e5414-0da2-4fba-b92f-0ba876e08939");
        dataV2.setName("滑雪套装");
        dataV2.setDescription("这种运动值得你拥有");
        dataV2.setCategory("户外运动");
        dataV2.setPrice(2000.0);
        dataV2.setSoldnum(3);
        Product dataV3 = new Product();
        dataV3.setId("17c3f20e-ef86-4857-9293-f29e52954a95");
        dataV3.setName("打印机");
        dataV3.setDescription("一个神奇的打印机?!");
        dataV3.setCategory("手机数码");
        dataV3.setPrice(180.0);
        dataV3.setSoldnum(3);
        Product dataV4 = new Product();
        dataV4.setId("70ee3179-3e76-4a3d-bd30-55d740f022dc");
        dataV4.setName("美的空调");
        dataV4.setDescription("美的(Midea) 新一级 风酷 大1.5匹变频冷暖壁挂式空调挂机大风口");
        dataV4.setCategory("家用电器");
        dataV4.setPrice(2599.0);
        dataV4.setSoldnum(3);
        Product dataV5 = new Product();
        dataV5.setId("a08b13e9-c16a-4657-94ee-3b9bee2bd9c6");
        dataV5.setName("华为荣耀8");
        dataV5.setDescription("挺好的");
        dataV5.setCategory("手机数码");
        dataV5.setPrice(1100.0);
        dataV5.setSoldnum(1);
        Product dataV6 = new Product();
        dataV6.setId("6c28bc1a-9c9b-4be3-b1cf-0068565e64e4");
        dataV6.setName("格力空调");
        dataV6.setDescription("1.5匹 云佳 新能效 变频冷暖 自清洁 壁挂式卧室空调挂机");
        dataV6.setCategory("家用电器");
        dataV6.setPrice(2699.0);
        dataV6.setSoldnum(1);
        dataList.add(dataVO);
        dataList.add(dataV1);
        dataList.add(dataV2);
        dataList.add(dataV3);
        dataList.add(dataV4);
        dataList.add(dataV5);
        dataList.add(dataV6);


        // 写入数据到工作簿对象内
        Workbook workbook = POIwriter.exportData(dataList);

        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "D:/writeExample.xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }
            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
        } catch (Exception e) {
            logger.warning("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.warning("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }
    }
}
