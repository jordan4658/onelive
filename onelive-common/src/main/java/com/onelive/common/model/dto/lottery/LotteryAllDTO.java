package com.onelive.common.model.dto.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;


@Data
public class LotteryAllDTO {

    List<LotteryPlayAllDTO> plays;

    private String cateName;

    private String intro;

    private Integer id;

    private Integer endTime;

    /**
     * 说明: 彩票名称
     */
    private String name;

    /**
     * 说明: 彩票分类id
     */
    private Integer categoryId;

    /**
     * 说明: 开奖号码源彩种
     */
    private Integer parentId;

    /**
     * 说明: 是否开售
     */
    private Integer isWork;

    /**
     * 说明: 彩种编号
     */
    private Integer lotteryId;

    /**
     * 最大赔率
     *
     * @mbggenerated
     */
    @JsonIgnore
    private Double maxOdds;

    /**
     * 最小赔率
     *
     * @mbggenerated
     */
    @JsonIgnore
    private Double minOdds;

    private Integer sort;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsWork() {
        return isWork;
    }

    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public List<LotteryPlayAllDTO> getPlays() {
        return plays;
    }

    public void setPlays(List<LotteryPlayAllDTO> plays) {
        this.plays = plays;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Double getMaxOdds() {
        return maxOdds;
    }

    public void setMaxOdds(Double maxOdds) {
        this.maxOdds = maxOdds;
    }

    public Double getMinOdds() {
        return minOdds;
    }

    public void setMinOdds(Double minOdds) {
        this.minOdds = minOdds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "LotteryAllDTO{" +
                "plays=" + plays +
                ", cateName='" + cateName + '\'' +
                ", intro='" + intro + '\'' +
                ", id=" + id +
                ", endTime=" + endTime +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", parentId=" + parentId +
                ", isWork=" + isWork +
                ", lotteryId=" + lotteryId +
                ", maxOdds=" + maxOdds +
                ", minOdds=" + minOdds +
                ", sort=" + sort +
                '}';
    }
}
