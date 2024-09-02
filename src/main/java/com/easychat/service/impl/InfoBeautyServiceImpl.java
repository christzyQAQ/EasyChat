package com.easychat.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.easychat.enums.BeautyAccountStatusEnum;
import com.easychat.enums.PageSize;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.dto.UserContactSearchResultDto;
import com.easychat.entity.po.Info;
import com.easychat.entity.po.InfoBeauty;
import com.easychat.entity.query.InfoBeautyQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.InfoBeautyService;
import com.easychat.service.InfoService;
import com.easychat.mappers.InfoBeautyMapper;
import com.easychat.mappers.InfoMapper;
import com.easychat.enums.*;

/**
 * ServiceImpl
 * 
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */

@Service("infoBeautyService")
public class InfoBeautyServiceImpl implements InfoBeautyService {

	@Resource
	private InfoBeautyMapper<InfoBeauty, InfoBeautyQuery> infoBeautyMapper;

	@Resource
	private InfoService infoService;

	/**
	 * 根据条件查询列表
	 */
	public List<InfoBeauty> findListByParam(InfoBeautyQuery query) {
		return this.infoBeautyMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(InfoBeautyQuery query) {
		return this.infoBeautyMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<InfoBeauty> findListByPage(InfoBeautyQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<InfoBeauty> list = this.findListByParam(query);
		PaginationResultVO<InfoBeauty> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(InfoBeauty bean) {
		return this.infoBeautyMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<InfoBeauty> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoBeautyMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<InfoBeauty> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoBeautyMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public InfoBeauty selectById(Integer id) {
		return this.infoBeautyMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateById(InfoBeauty bean, Integer id) {
		return this.infoBeautyMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteById(Integer id) {
		return this.infoBeautyMapper.deleteById(id);
	}

	/**
	 * 根据UserId查询
	 */
	public InfoBeauty selectByUserId(String userId) {
		return this.infoBeautyMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	public Integer updateByUserId(InfoBeauty bean, String userId) {
		return this.infoBeautyMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	public Integer deleteByUserId(String userId) {
		return this.infoBeautyMapper.deleteByUserId(userId);
	}

	/**
	 * 根据Email查询
	 */
	public InfoBeauty selectByEmail(String email) {
		return this.infoBeautyMapper.selectByEmail(email);
	}

	/**
	 * 根据Email更新
	 */
	public Integer updateByEmail(InfoBeauty bean, String email) {
		return this.infoBeautyMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	public Integer deleteByEmail(String email) {
		return this.infoBeautyMapper.deleteByEmail(email);
	}


	@Override
	public void saveAccount(InfoBeauty beauty) {
		if (beauty.getId() != null) {
			InfoBeauty dbInfoBeauty = this.infoBeautyMapper.selectByEmail(beauty.getEmail());
			if (BeautyAccountStatusEnum.USEED.getStatus().equals(dbInfoBeauty.getStatus()))
				;
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		InfoBeauty dbInfoBeauty = this.infoBeautyMapper.selectByEmail(beauty.getEmail());
		// 新增是判断邮箱是否存在
		if (beauty.getUserId() == null && dbInfoBeauty != null) {
			throw new BusinessException("靓号邮箱已经存在");
		}

		// 修改时判断邮箱是否存在
		if (beauty.getId() != null && dbInfoBeauty != null && dbInfoBeauty.getUserId() != null
				&& !beauty.getId().equals(dbInfoBeauty.getId())) {
			throw new BusinessException("靓号邮箱已经存在");
		}
		// 判断靓号是否存在

		dbInfoBeauty = this.infoBeautyMapper.selectByUserId(beauty.getUserId());
		if (beauty.getId() == null && dbInfoBeauty != null) {
			throw new BusinessException("靓号已经存在");
		}
		// 修改时判断靓号是否存在
		if (beauty.getId() != null && dbInfoBeauty != null && dbInfoBeauty.getUserId() != null
				&& !beauty.getId().equals(dbInfoBeauty.getId())) {
			throw new BusinessException("靓号已经存在");
		}
		// 判断邮箱是否注册
		Info info = this.infoService.selectByEmail(beauty.getEmail());
		if (info != null) {
			throw new BusinessException("邮箱已注册");
		}
		// 判断账号是否注册
		 info = this.infoService.selectByUserId(beauty.getUserId());
		if (info != null) {
			throw new BusinessException("账号已注册");
		}
		if (beauty.getId()!=null) {
			this.infoBeautyMapper.updateById(beauty,beauty.getId());
		}else {
			beauty.setStatus(BeautyAccountStatusEnum.NO_USE.getStatus());
			this.infoBeautyMapper.insert(beauty);
		}
		
	}

}