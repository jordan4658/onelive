package com.onelive.manage.modules.live.business;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.mybatis.entity.RoomFastWords;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.live.RoomFastWordsService;

@Component
public class RoomFastWordsBusiness {

	@Autowired
	private RoomFastWordsService roomFastWordsService;

	public PageInfo<RoomFastWordsDto> getList(RoomFastWordsDto roomFastWordsDto) {
		PageHelper.startPage(roomFastWordsDto.getPageNum(), roomFastWordsDto.getPageSize());
		List<RoomFastWords> list = roomFastWordsService.getList(roomFastWordsDto);
		return new PageInfo<>(BeanCopyUtil.copyCollection(list, RoomFastWordsDto.class));
	}

	public void save(RoomFastWordsDto roomFastWordsDto) {
		RoomFastWords roomFastWords = new RoomFastWords();
		BeanUtils.copyProperties(roomFastWordsDto, roomFastWords);
		roomFastWordsService.save(roomFastWords);
	}

	public void update(RoomFastWordsDto roomFastWordsDto) {
		if (roomFastWordsDto.getId() == null) {
			throw new BusinessException("主键不能为空");
		}
		RoomFastWords roomFastWords = roomFastWordsService.getById(roomFastWordsDto.getId());
		if (roomFastWords == null) {
			throw new BusinessException("找不到对应的快捷回复信息");
		}
		BeanUtils.copyProperties(roomFastWordsDto, roomFastWords);
		roomFastWordsService.updateById(roomFastWords);
	}

	public void enableOrDisable(RoomFastWordsDto roomFastWordsDto) {
		if (roomFastWordsDto.getId() == null) {
			throw new BusinessException("主键不能为空");
		}
		RoomFastWords roomFastWords = roomFastWordsService.getById(roomFastWordsDto.getId());
		if (roomFastWords == null) {
			throw new BusinessException("找不到对应的快捷回复信息");
		}
		roomFastWords.setIsShow(roomFastWordsDto.getIsShow());
		roomFastWordsService.updateById(roomFastWords);
	}

	public void delete(RoomFastWordsDto roomFastWordsDto) {
		roomFastWordsService.removeById(roomFastWordsDto.getId());
	}

}
