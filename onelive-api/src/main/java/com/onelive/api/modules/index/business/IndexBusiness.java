package com.onelive.api.modules.index.business;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.onelive.api.service.index.LiveColumnService;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.platform.GameIndexService;
import com.onelive.api.service.sys.SysAdvFlashviewService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.ColumnCodeEnum;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.model.dto.index.IndexTopGameListDTO;
import com.onelive.common.model.req.live.LiveColumnCodeReq;
import com.onelive.common.model.req.live.LiveIndexSearchReq;
import com.onelive.common.model.req.live.LiveListReq;
import com.onelive.common.model.vo.index.IndexTopGameListVO;
import com.onelive.common.model.vo.index.LiveColumnVO;
import com.onelive.common.model.vo.live.LiveAdvListVO;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchListVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.AesUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.upload.AWSS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Component
public class IndexBusiness {

	@Autowired
	private LiveColumnService liveColumnService;
	@Autowired
	private LiveStudioListService liveStudioListService;
	@Autowired
	private MemUserService memUserService;
	@Autowired
	private SysAdvFlashviewService sysAdvFlashviewService;
	@Resource
	private GameIndexService gameIndexService;
	@Resource
	private SysParameterService sysParameterService;

	//todo 后面修改成配置
	public static final String KEY = "c1kgVioySoUVimtw";


	/**
	 * 	推荐标签直播间列表:后台配置排序规则(排除推荐区的四个推荐和光年推荐的四个）
	 * 
	 * @param req
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getRecommendByHeat(LiveListReq req) {
		return liveStudioListService.getRecommendByHeatNew(req);
	}

	/**
	 * 	获取栏目列表
	 * 
	 * @return
	 */
	public List<LiveColumnVO> getLiveColumnList() {
		String lang = StringUtils.isEmpty(LoginInfoUtil.getLang()) ? "zh_CN" : LoginInfoUtil.getLang();
		return liveColumnService.getAll(lang);
	}

	/**
	 * 	获取关注的直播房间列表
	 * 
	 * @param req
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getLiveFocusList(LiveListReq req)  {
		return liveStudioListService.getLiveFocusListVO(req);
	}

	/**
	 * 获取关注页签推荐的直播房间列表
	 * 
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getLiveFocusRecommendList()  {
		// 随机获取10条数据，并且不包含已经关注的
		return liveStudioListService.getLiveFocusRecommendList();
	}

	/**
	 * 光年推荐(最多四个，人数前四的游戏直播间)
	 * 
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getLivePopList()  {
		return liveStudioListService.getPromotion();
	}

	/**
	 * 	首页搜索  返回两个列表，一个主播名片，一个主播的直播间列表
	 *		
	 *	如果主播开播，返回主播直播间信息
	 * 
	 * @param req
	 * @return
	 */
	public MemUserAnchorSearchVO search(LiveIndexSearchReq req) {
		MemUserAnchorSearchVO memUserAnchorSearchVO = new MemUserAnchorSearchVO();
		// 模糊匹配:匹配accno,用户昵称,直播间标题  精确匹配：房间号， userid，房间号
		List<MemUserAnchorSearchListVO> anchorList = memUserService.search(req);
		memUserAnchorSearchVO.setAnchorList(anchorList);
		List<LiveStudioList> liveStudioListList = new ArrayList<>(anchorList.size());
		for (MemUserAnchorSearchListVO memUserAnchorSearchListVO : anchorList) {
			LiveStudioList liveStudioList = new LiveStudioList();
			liveStudioList.setStudioNum(memUserAnchorSearchListVO.getStudioNum());
			liveStudioList.setCountryCode(memUserAnchorSearchListVO.getCountryCode());
			liveStudioListList.add(liveStudioList);
		}
		// 正在直播的直播间标题，房间号，直播间对应的主播信息
		List<LiveStudioListForIndexVO> liveStudioListVoList = liveStudioListService.studioListByOrder(liveStudioListList, null, null, 1);
		memUserAnchorSearchVO.setStudioList(liveStudioListVoList);
		return memUserAnchorSearchVO;
	}

	/**
	 *	 推荐区的固定四个推荐直播
	 * 
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getRecommend() {
		List<LiveStudioListForIndexVO> result = liveStudioListService.getRecommend(LoginInfoUtil.getCountryCode());
		return result;
	}

	/**
	 * 	根据轮播图类型查询
	 * @return
	 */
	public List<LiveAdvListVO> getAdvByType(String flashviewType) {
		List<SysAdvFlashviewLang> sysAdvFlashviews = sysAdvFlashviewService.getAdvByType(flashviewType);
		List<LiveAdvListVO> result = new ArrayList<>();
		for (SysAdvFlashviewLang sysAdvFlashview : sysAdvFlashviews) {
			LiveAdvListVO liveAdvListVO = new LiveAdvListVO();
			liveAdvListVO.setId(sysAdvFlashview.getId());
			liveAdvListVO.setAdvName(sysAdvFlashview.getFlashviewName());
			liveAdvListVO.setAdvImg(AWSS3Util.getAbsoluteUrl(sysAdvFlashview.getImgUrl()));
			liveAdvListVO.setSkipModel(sysAdvFlashview.getSkipModel());
			liveAdvListVO.setSkipUrl(sysAdvFlashview.getSkipUrl());
			result.add(liveAdvListVO);
		}
		return result;
	}
	
	/**
	 * 最近浏览的直播间信息
	 * @return
	 */
	public List<LiveStudioListForIndexVO> browseHistory() {
		Long userId = LoginInfoUtil.getUserId();
		// 最近浏览的房间号
		LinkedHashSet<String> rangeZset = ApiBusinessRedisUtils.reverseRangeZset(WebSocketRedisKeys.studioNum_browse_history + userId, 0, -1);
		 // 主播列表
        Map<String, Object> anchorStudioNumList = ApiBusinessRedisUtils.hMGet(WebSocketRedisKeys.anchor_studioNum);
        Set<String> keySet = anchorStudioNumList.keySet();
        List<LiveStudioListForIndexVO> result = new ArrayList<>();
        // 如果没有记录，或者没有在直播的的房间
        if (CollectionUtils.isEmpty(rangeZset) || CollectionUtils.isEmpty(anchorStudioNumList)) {
        	return result;
        }

        // 最近浏览中并且在直播的直播间
        List<String> liveStudioNums = new ArrayList<>(Sets.intersection(rangeZset, keySet));
        if (CollectionUtils.isEmpty(liveStudioNums)) {
        	return result;
        }
        // 最多展示四条
        liveStudioNums = liveStudioNums.size() > 4 ? liveStudioNums.subList(0, 4) : liveStudioNums;
        List<LiveStudioListForIndexVO> list = liveStudioListService.getLiveByStudioNums(liveStudioNums);
        // 按照浏览时间排序
        for (String liveStudioNum : liveStudioNums) {
        	LiveStudioListForIndexVO liveStudioListForIndexVO = list.stream().filter(t -> t.getStudioNum().equals(liveStudioNum)).findFirst().get();
			result.add(liveStudioListForIndexVO);
		}
		return result;
	}
	

	/**
	 *		光年推荐(最多四个，人数前四的游戏直播间)
	 * 
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getPromotion() {
		List<LiveStudioListForIndexVO> getPromotion = liveStudioListService.getPromotion();
		return getPromotion;
	}


	/**
	 * 	根据栏目code查询直播房间列表(除了推荐和关注栏目外)
	 * 		热门，星秀，附近，游戏
	 * 
	 * @param req
	 * @return
	 */
	public List<LiveStudioListForIndexVO> getLiveByColumnCode(LiveColumnCodeReq req) {
		List<LiveStudioListForIndexVO> liveStudioListVoList = new ArrayList<>();
		// 按照在线人数查询
		if (ColumnCodeEnum.hot.toString().equals(req.getColumnCode())){
			liveStudioListVoList = liveStudioListService.getHotList(req);
		}
		// 星秀纯黄播 以火力值排名
		else if (ColumnCodeEnum.star.toString().equals(req.getColumnCode())){
			liveStudioListVoList = liveStudioListService.getStarList(req);
		}
		// 与用户同一国家的主播
		else if (ColumnCodeEnum.nearby.toString().equals(req.getColumnCode())){
			liveStudioListVoList = liveStudioListService.getNearbyList(req);
		}
		// 直播间gameid不为空的
		else if (ColumnCodeEnum.game.toString().equals(req.getColumnCode())){
			liveStudioListVoList = liveStudioListService.getGameList(req);
		}
		
		return liveStudioListVoList;
	}


	/**
	 * 查询首页顶部游戏列表配置
	 * @return
	 */
    public List<IndexTopGameListVO> queryIndexTopGameList() {

		List<IndexTopGameListDTO> dtoList = gameIndexService.queryIndexTopGameList();
		//对数据进行拼接
		List<IndexTopGameListVO> voList = new LinkedList<>();
		if(!CollectionUtils.isEmpty(dtoList)){

			SysParameter sysParameter = sysParameterService.getByCode(SysParamEnum.APP_ROUTE_ROOT);
			//取路由要链接
			String rootRoute = "";
			if(sysParameter!=null){
				rootRoute = sysParameter.getParamValue();
			}
			for (IndexTopGameListDTO dto : dtoList) {
				IndexTopGameListVO vo = new IndexTopGameListVO();
				BeanCopyUtil.copyProperties(dto,vo);
				StringBuilder sb = new StringBuilder();
				if(dto.getSkipModel()==3){//app内页面
					String params = dto.getParams();
					sb.append(rootRoute).append(dto.getRoute());
					if(StrUtil.isNotBlank(params)) {
						sb.append("?").append(AesUtil.aesEncrypt(params, KEY));
					}
					vo.setSkipUrl(sb.toString());
				}
				voList.add(vo);
			}
		}
		return voList;
    }
}
