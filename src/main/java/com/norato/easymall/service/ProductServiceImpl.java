package com.norato.easymall.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norato.easymall.dto.ProductInfo;
import com.norato.easymall.entity.Category;
import com.norato.easymall.entity.Product;
import com.norato.easymall.mapper.CategoryMapper;
import com.norato.easymall.mapper.ProductsMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

@Service("productsService")
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<String> allcategories() {    //查找所有种类的名字
        List<Category> categories = categoryMapper.selectList(null);
        List<String> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(category.getName());
        }
        return list;
    }

    // prodlist
//select * from products where status=1 and (price between #{minPrice} and #{maxPrice})
//	<if test="name!=null and name!=''">
//		and name like concat('%',#{name},'%')
//	</if>
//	<if test="category!=null and category!=''">
//		and category=#{category}
//	</if>
    @Override
    public List<Product> prodlist(Map<String, Object> map) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (map.get("name") != null && !map.get("name").equals("")) {
            wrapper.eq("name", map.get("name"));
        }
        if (map.get("category") != null && !map.get("category").equals("")) {
            wrapper.eq("category", map.get("category"));
        }
        Object minPrice = map.get("minPrice");
        Object maxPrice = map.get("maxPrice");

        wrapper.between("price", minPrice, maxPrice);
        return productsMapper.selectList(wrapper);
    }

    @Override
    public Product prodInfo(String pid) {
//        select *from products where id = #{pid}
        return productsMapper.selectById(pid);
    }

    @Override
    public List<Product> prodclass(String proclass) {
//        select *from products where  status=1 and category=#{category}
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.eq("category", proclass);
        return productsMapper.selectList(wrapper);
    }

    @Override
    public Page<Product> selectPage(Page<Product> page, QueryWrapper<Product> wrapper) {
        return productsMapper.selectPage(page, wrapper);
    }

    @Override
    public Product oneProduct(String product_id) {
        //	select *from products where id=#{product_id}
        return productsMapper.selectById(product_id);
    }

    @Override
    public String save(ProductInfo myproducts, HttpServletRequest request) {
        // 1.判断后缀是否合法
        // 获取图名称，后缀名称
        String originName = myproducts.getImgurl().getOriginalFilename();

        // 截取后缀substring split (".png" ".jgp")
        String extName = originName.substring(originName.lastIndexOf("."));

        if (!(extName.equalsIgnoreCase(".jpg") || extName.equalsIgnoreCase(".png")
                || extName.equalsIgnoreCase(".gif"))) {// 图片后缀不合法
            return "图片后缀不合法!";
        }
        // 判断木马(java的类判断是否是图片属性，也可以引入第三方jar包判断)
        try {
            BufferedImage bufImage = ImageIO.read(myproducts.getImgurl().getInputStream());
            bufImage.getHeight();
            bufImage.getWidth();
        } catch (Exception e) {
            return "该文件不是图片！";
        }
        // 2.创建upload开始的一个路径
        // 生成多级路径
        String imgurl = "";
        // /a/2/e/a/2/3/j/p
        for (int i = 0; i < 8; i++) {
            imgurl += "/" + Integer.toHexString(new Random().nextInt(16));
        }
        String realpath = request.getServletContext().getRealPath("/WEB-INF");
        realpath += "/upload";
        // D:\java\Workspace\.metadata\.plugins\org.eclipse.wst.server.core
        // \tmp0\wtpwebapps\EasyMall18\WEB-INF/upload/2/6/2/7/2/d/2/1

        File file = new File(realpath + imgurl, originName);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 上传文件
        try {
            myproducts.getImgurl().transferTo(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 拼接图片存入数据库的路径

        System.out.println(imgurl + "-------");
        imgurl = "/upload" + imgurl + "/" + originName;
        System.out.println(imgurl);
        String id = UUID.randomUUID().toString();
        Product product = new Product();
        product.setId(id);
        product.setName(myproducts.getName());
        product.setCategory(myproducts.getCategory());
        product.setPrice(myproducts.getPrice());
        product.setPnum(myproducts.getPnum());
        product.setImgurl(imgurl);
        product.setDescription(myproducts.getDescription());
        if (findByImgurl(product.getImgurl()) == null) {
            productsMapper.insert(product);/////修改 原来为save
        } else {
            String fname = imgurl.substring(0, imgurl.lastIndexOf("."));
            imgurl = fname + System.currentTimeMillis() + extName;
            System.out.println(imgurl);
            product.setImgurl(imgurl);
            System.out.println(product.getImgurl());
            productsMapper.insert(product);          /////修改 原来为save
        }
        return "商品添加成功";
    }


    @Override
    public List<Product> allprods() {
//        select * from products
        return productsMapper.selectList(null);
    }

    @Override
    public void updateSaleStatus(Map<String, Object> map) {
//        update products set status=#{status} where id=#{id}
        String id = (String) map.get("id");
        Product products = productsMapper.selectById(id);
        Integer status = (Integer) map.get("status");
        products.setStatus(status);
        productsMapper.updateById(products);
    }

    //save  insert into products(id,name,price,category,pnum,imgurl,description)
    //		values(#{id},#{name},#{price},#{category},#{pnum},#{imgurl},#{description});
    public void save(Product product) {
        productsMapper.insert(product);
    }

    // findByImgurl --select * from products where imgurl=#{imgurl}
    public List<Product> findByImgurl(String imgUrl) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("imgurl", imgUrl);
        return productsMapper.selectList(wrapper);
    }

    //update
    //	update products set name = #{name}, price = #{price}, category = #{category},
    //	imgurl = #{imgurl}, pnum = #{pnum}, description = #{description} where id = #{id};
    @Override
    public void updateProduct(Product product) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("id", product.getId());
        productsMapper.update(product, wrapper);
    }

    //delete
    //	delete from products where id = #{id}
    @Override
    public void deleteProduct(String id) {
        productsMapper.deleteById(id);
    }

    @Override
    public List<Product> findProductById(Integer Id) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("Id", Id);
        return productsMapper.selectList(wrapper);
    }
}