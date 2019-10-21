package com.shuangduan.zcy.utils;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class HtmlUtils {

    /**
     * 获取html中的图片src
     * @param content
     * @return
     */
    public static List<String> getImg(String content){
        List<String> srcList = new ArrayList<String>(); //用来存储获取到的图片地址
        Pattern p = Pattern.compile("<(img|IMG)(.*?)(>|></img>|/>)");//匹配字符串中的img标签
        Matcher matcher = p.matcher(content);
        boolean hasPic = matcher.find();

        if(hasPic == true)//判断是否含有图片
        {
            while(hasPic) //如果含有图片，那么持续进行查找，直到匹配不到
            {
                String group = matcher.group(2);//获取第二个分组的内容，也就是 (.*?)匹配到的
                Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//匹配图片的地址
                Matcher matcher2 = srcText.matcher(group);
                if( matcher2.find() )
                {
                    srcList.add( matcher2.group(3) );//把获取到的图片地址添加到列表中
                }
                hasPic = matcher.find();//判断是否还有img标签
            }
        }
        return srcList;
    }

    /**
     * 读取.json文件
     * @param context
     * @return
     */
    private String getData(Context context){
        try {
            InputStream is = context.getAssets().open("china_city_data");
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            LogUtils.i(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
