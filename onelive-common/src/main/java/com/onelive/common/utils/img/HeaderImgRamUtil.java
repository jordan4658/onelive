package com.onelive.common.utils.img;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HeaderImgRamUtil {

    private static List<String> HEADLIST = new ArrayList<>();
    static {
        HEADLIST.add("/oneLive_image/headImg1-1.png");
        HEADLIST.add("/oneLive_image/headImg1-2.png");
        HEADLIST.add("/oneLive_image/headImg1-3.png");

        HEADLIST.add("/oneLive_image/headImg2-1.png");
        HEADLIST.add("/oneLive_image/headImg2-2.png");
        HEADLIST.add("/oneLive_image/headImg2-3.png");

        HEADLIST.add("/oneLive_image/headImg3-1.png");
        HEADLIST.add("/oneLive_image/headImg3-2.png");
        HEADLIST.add("/oneLive_image/headImg3-3.png");

        HEADLIST.add("/oneLive_image/headImg4-1.png");
        HEADLIST.add("/oneLive_image/headImg4-2.png");
        HEADLIST.add("/oneLive_image/headImg4-3.png");
    }

    /**
     *  随机获取用户头像
     * @return
     */
    public static String userHeadImg(){
      String url  = null;
      try{
          Integer count = (int) (Math.random() * (HEADLIST.size() - 1));
          url = HEADLIST.get(count);
      }catch (Exception e){
          log.error("随机获取用户头像出错", e);
      }
      return url;
    }

}
