package online.shixun.module.sort.controller;

import online.shixun.dto.ProductDTO;
import online.shixun.module.sort.service.SortService;
import online.shixun.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SortController extends BaseController {

    @Autowired
    private SortService sortService;

    /**
     * 返回到排序选择页面
     *
     * @return
     */
    @RequestMapping(value = "/front/sorting")
    public String sorting() {
        return "/sorting";
    }

    /**
     * 价格由低到高
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/front/sort/asc")
    public String sortAsc(Model model) {
        //获取到所有商品及其属性值
        List<ProductDTO> allProduct = sortService.getAllProducts();
        model.addAttribute("allProduct", allProduct);

//        Double[] price=new Double[100];
//
//        ProductDTO pro=allProduct.get(0);
//        price[0]=pro.getPrice();
//
//        for(int i=1;i<allProduct.size();i++){
//             pro=allProduct.get(i);
//            price[i]=pro.getPrice();
//            double temp=price[i];
//            int j;
//            for (j=i;j>0&&temp<price[j-1];j--){
//                price[j]=price[j-1];
//            }
//            price[j]=temp;
//        }
//
//        System.out.print(Arrays.toString(price));

        return "/sort/asc";
    }

    @RequestMapping(value = "/front/sort/desc")
    public String sortDesc(Model model) {
        //获取到所有商品及其属性值
        List<ProductDTO> DllProduct = sortService.getDllProducts();
        model.addAttribute("dllProduct", DllProduct);

        return "/sort/desc";
    }
}
