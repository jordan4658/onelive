package com.onelive.pay.model.business;

import cn.hutool.core.date.DateField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.pay.TotalUserIncomeAndExpensesDTO;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.req.pay.TransactionReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.PayGoldChangeDetailVO;
import com.onelive.common.model.vo.pay.PayGoldChangeVO;
import com.onelive.common.model.vo.pay.TransactionTypeVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.pay.common.utils.ApiBusinessRedisUtils;
import com.onelive.pay.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemGoldchangeBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/15 17:11
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemGoldchangeBusiness {

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private SysBusParameterService sysBusParameterService;
    @Resource
    private PayOrderWithdrawService payOrderWithdrawService;
    @Resource
    private MemGoldchangeService memGoldchangeService;
    @Resource
    private PayTransactionTypeCfgService payTransactionTypeCfgService;
    @Resource
    private LiveGiftService liveGiftService;
    @Resource
    private LotteryLangService lotteryLangService;


    public List<PayGoldChangeVO> getListPage(TransactionReq req, AppLoginUser user) {
        if (req.getTransactionType() == null) {
            throw new BusinessException("交易类型为空！");
        }
        PayGoldChangeVO payGoldChangeVO = new PayGoldChangeVO();
        String lang = LoginInfoUtil.getLang();
        //判断交易类型是否设置的交易类型范围
        List<SysBusParameter> typeListOr = sysBusParameterService.getByParentCode(PayConstants.TRANSACTION_TYPE);
        List<SysBusParameter> isExist = typeListOr.stream().filter(p -> p.getParamCode().equals(req.getTransactionType().toString())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(isExist)) {
            throw new BusinessException("交易类型错误！");
        }
        List<SysBusParameter> typeList;
        List<SysBusParameter> allTypeList;
        allTypeList = typeListOr.stream().filter(p -> !p.getParamCode().equals("0")).collect(Collectors.toList());
        //判断是否查询全部
        if (req.getTransactionType() == 0) {
            typeList = typeListOr.stream().filter(p -> p.getParamCode().equals("0")).collect(Collectors.toList());
        } else {
            typeList = typeListOr.stream().filter(p -> !p.getParamCode().equals("0")).collect(Collectors.toList());
        }
        //获取交易类型多语言配置
        List<PayTransactionTypeCfg> transactionTypeCfgLangList = payTransactionTypeCfgService.list();
        //获取对应的账变类型
        List<Integer> transactionTypes = new ArrayList<>();
        //匹配类型
        for (SysBusParameter parame : typeList) {
            if (req.getTransactionType().equals(Integer.valueOf(parame.getParamCode()))) {
                transactionTypes = Arrays.stream(parame.getParamValue().split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            }
        }
        Date queryDate = null;
        Date startDate = null;
        if (req.getQueryDate() != null && req.getQueryDate() != 0L) {
            queryDate = DateInnerUtil.date(req.getQueryDate());
        }
        if (req.getStartDate() != null && req.getStartDate() != 0L) {
            startDate = DateInnerUtil.date(req.getStartDate());
        }
        //查询账变记录 集合
        List<MemGoldchange> list = memGoldchangeService.getUserChangeLog(transactionTypes, user.getUserAccount(), req.getPageSize(), queryDate, startDate);
        if (CollectionUtils.isEmpty(list)) {
            List<PayGoldChangeVO> listVo = new ArrayList<>();
            return listVo;
        }
        //组装数据
        List<PayGoldChangeDetailVO> detailVOList = list.stream().map(a -> {
            PayGoldChangeDetailVO detailVO = new PayGoldChangeDetailVO();
            detailVO.setGoldType(a.getGoldType());
            detailVO.setGoldChangId(a.getGoldChangId());
            detailVO.setPrice(a.getAmount());
            detailVO.setOrderNo(a.getRefNo());
            if (a.getChangeType() == 1) {
                detailVO.setPrice(a.getSilverAmount());
            }
            detailVO.setPayDate(a.getCreateTime());
            //匹配类型
            for (SysBusParameter parame : allTypeList) {
                for (String str : parame.getParamValue().split(",")) {
                    if (str.equals(a.getChangeType().toString())) {
                        detailVO.setTransactionType(parame.getParamCode());
                    }
                }
            }
            /**---------------匹配项目名称----start-------------**/
            //匹配注单、中奖类型的 项目名称
            if (detailVO.getTransactionType().equals(PayConstants.TRANSACTION_TYPE_ENUM.TYPE_5.getCode().toString()) || detailVO.getTransactionType().equals(PayConstants.TRANSACTION_TYPE_ENUM.TYPE_4.getCode().toString())) {
                //数据结构: lang:name
                Map<String, String> langNameMap = getLotteryLangNameMap(Integer.valueOf(a.getOpnote()));
                detailVO.setProjectName(langNameMap.get(lang));

            } else if (detailVO.getTransactionType().equals(PayConstants.TRANSACTION_TYPE_ENUM.TYPE_3.getCode().toString())) { //匹配礼物类型 项目名称
                //数据结构: lang:name
                List<LiveGiftForIndexDto> liveGiftList = liveGiftService.getList(LoginInfoUtil.getCountryCode(), lang);
                for (LiveGiftForIndexDto gif : liveGiftList) {
                    if (gif.getGiftId().equals(a.getOpnote())) {
                        detailVO.setProjectName(gif.getGiftName());
                    }
                }
//                Map<String, String> langNameMap = ApiBusinessRedisUtils.getLotteryLangNameMap(Integer.valueOf(a.getOpnote()));
//                detailVO.setProjectName(langNameMap.get(lang));
            } else {
                for (PayTransactionTypeCfg t : transactionTypeCfgLangList) {
                    Boolean flag = false;
                    List<String> listStr = Arrays.asList(t.getTransactionTypeCode().split(","));
                    if (t.getTransactionTypeCode().indexOf("5") > 0) {
                        log.info("t.getTransactionTypeCode()=====:" + t.getTransactionTypeCode());
                    }
                    for (String str : listStr) {
                        if (str.equals(a.getChangeType().toString()) && t.getLangValue().equals(lang)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        detailVO.setProjectName(t.getTransactionTypeName());
                        break;
                    }
                }
            }
            /**---------------匹配项目名称----end---------------**/
            //匹配充值订单状态
            if (detailVO.getTransactionType().equals(PayConstants.TRANSACTION_TYPE_ENUM.TYPE_1.getCode().toString())) {
//                //成功
//                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getPayTypeCode()) {
//                    detailVO.setOrderStatus(PayConstants.TransactionStatusEnum.STATUS_1.getCode());
//                }
//                //失败
//                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE15.getPayTypeCode()) {
//                    detailVO.setOrderStatus(PayConstants.TransactionStatusEnum.STATUS_2.getCode());
//                }
//                //取消
//                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE16.getPayTypeCode()) {
//                    detailVO.setOrderStatus(PayConstants.TransactionStatusEnum.STATUS_3.getCode());
//                }
                //这里改为直接查询充值订单表数据
                PayOrderRecharge orderRecharge = payOrderRechargeService.getByOrderNo(a.getRefNo());
                if (orderRecharge != null) {
                    detailVO.setOrderStatus(orderRecharge.getOrderStatus());
                }

            }
            //匹配提现订单状态
            if (detailVO.getTransactionType().equals(PayConstants.TRANSACTION_TYPE_ENUM.TYPE_2.getCode().toString())) {
                //处理中
                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE6.getPayTypeCode()) {
                    detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.IN_HAND.getCode());
                }
                //申请受理中
                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE3.getPayTypeCode()) {
                    detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.PENDING.getCode());
                }
                //成功
                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE7.getPayTypeCode()) {
                    detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.SUCCESS.getCode());
                }
                //失败
                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE5.getPayTypeCode()) {
                    detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.UN_SUCCESS.getCode());
                }
                //取消
                if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE4.getPayTypeCode()) {
                    detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.CANCEL.getCode());
                }
            }
            //匹配撤单状态
            if (a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE23.getPayTypeCode() || a.getChangeType() == PayConstants.AccountChangTypeEnum.CHANG_TYPE24.getPayTypeCode()) {
                detailVO.setOrderStatus(PayConstants.PayOrderStatusEnum.CANCELLATIONS.getCode());
            }

            return detailVO;
        }).collect(Collectors.toList());
        //分离出日期来
        List<String> listDate = list.stream().map(a -> {
            String thisDateYear = DateInnerUtil.format(a.getCreateTime(), DateInnerUtil.FORMAT_YYYY_MM);
            return thisDateYear;
        }).collect(Collectors.toList());
        //去重
        HashSet h = new HashSet(listDate);
        listDate.clear();
        listDate.addAll(h);
        //统计月份收支情况
        List<TotalUserIncomeAndExpensesDTO> totalList = memGoldchangeService.totalUserIncomeAndExpenses(listDate, transactionTypes, user.getUserAccount());
        List<PayGoldChangeVO> listVo = totalList.stream().map(a -> {
                    PayGoldChangeVO vo = new PayGoldChangeVO();
                    vo.setTotalInfo(a);
                    List<PayGoldChangeDetailVO> detailList = new ArrayList<>();
                    for (PayGoldChangeDetailVO detailVO : detailVOList) {
                        String thisDateYear = DateInnerUtil.format(detailVO.getPayDate(), DateInnerUtil.FORMAT_YYYY_MM);
                        if (a.getTotalDate().equals(thisDateYear)) {
                            detailList.add(detailVO);
                        }
                    }
                    vo.setDetailList(detailList);
                    return vo;
                }
        ).collect(Collectors.toList());
        return listVo;
    }


    /**
     * 充值交易明细
     *
     * @param pageNum
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param account
     * @param transactionType
     * @return
     */
    private PageInfo<MemGoldchangeVO> rechargeRecordList(Integer pageNum, Integer pageSize, Date startDate, Date endDate, String account, SysBusParameter transactionType) {
        PageHelper.startPage(pageNum, pageSize);
        List<MemGoldchangeVO> list = payOrderRechargeService.rechargeRecordList(startDate, endDate, account);
        List<MemGoldchangeVO> newList = list.stream().map(e -> {
            e.setTransactionTypeName(transactionType.getParamValue());
            e.setTransactionTypeIconUrl(transactionType.getRemark());
            return e;
        }).collect(Collectors.toList());
        return new PageInfo<MemGoldchangeVO>(newList);
    }

    /**
     * 提现记录明细
     *
     * @param pageNum
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param account
     * @param transactionType
     * @return
     */
    private PageInfo<MemGoldchangeVO> withdrawRecordList(Integer pageNum, Integer pageSize, Date startDate, Date endDate, String account, SysBusParameter transactionType) {
        PageHelper.startPage(pageNum, pageSize);
        List<MemGoldchangeVO> list = payOrderWithdrawService.withdrawRecordList(startDate, endDate, account);
        List<MemGoldchangeVO> newList = list.stream().map(e -> {
            e.setTransactionTypeName(transactionType.getParamValue());
            e.setBankNo(bankConvertToHidden(e.getBankNo()));
            e.setTransactionTypeIconUrl(transactionType.getRemark());
            return e;
        }).collect(Collectors.toList());
        return new PageInfo<MemGoldchangeVO>(newList);
    }

    /**
     * 银行卡号进行隐式显示 例如 ：6214830119460961 ->  **** **** **** 0961
     *
     * @param bankNo
     * @return
     */
    private String bankConvertToHidden(String bankNo) {
        StringBuffer hiddenBankNo = new StringBuffer();
        String cutOutBankNo = bankNo.substring(bankNo.length() - 4, bankNo.length());
        hiddenBankNo.append(PayConstants.CONVERT_STR).append(cutOutBankNo);
        return hiddenBankNo.toString();
    }

    public List<TransactionTypeVO> transactionTypeList(AppLoginUser user) {
        List<SysBusParameter> typeList = sysBusParameterService.getByParentCode(PayConstants.TRANSACTION_TYPE);
        List<TransactionTypeVO> list = new ArrayList<>();
        for (SysBusParameter parameter : typeList) {
            TransactionTypeVO typeVO = new TransactionTypeVO();
            typeVO.setCode(parameter.getParamCode());
            typeVO.setIconUrl(parameter.getRemark());
            typeVO.setValue(parameter.getParamValue());
            list.add(typeVO);
        }
        return list;
    }

    /**
     * 查询游戏的多语言名称并缓存
     *
     * @param lotteryId
     * @return
     */
    private Map<String, String> getLotteryLangNameMap(Integer lotteryId) {
        if (lotteryId == null) {
            return null;
        }
        Map<String, String> langNameMap = ApiBusinessRedisUtils.getLotteryLangNameMap(lotteryId);
        if (CollectionUtil.isNotEmpty(langNameMap)) {
            return langNameMap;
        }
        QueryWrapper<LotteryLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryLang::getLotteryId, lotteryId);
        List<LotteryLang> langList = lotteryLangService.list(queryWrapper);
        if (CollectionUtil.isEmpty(langList)) {
            return null;
        }
        //缓存数据
        langNameMap = new HashMap<>();
        for (LotteryLang lang : langList) {
            langNameMap.put(lang.getLang(), lang.getLotteryName());
        }
        ApiBusinessRedisUtils.setLotteryLangNameMap(langNameMap, lotteryId);
        return langNameMap;
    }

}
