package com.easychat.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.redisson.transaction.operation.UnlinkOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.enums.AppUpdateFileTypeEnum;
import com.easychat.enums.AppUpdateStatusEnum;
import com.easychat.enums.PageSize;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Update;
import com.easychat.entity.query.UpdateQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.UpdateService;
import com.easychat.utils.StringTools;
import com.easychat.mappers.UpdateMapper;

/**
 * app发布ServiceImpl
 * @auther: 系统
 * @date: 2024-07-27 13:27
 */
@Service("updateService")
public class UpdateServiceImpl implements UpdateService {

	@Resource
	private UpdateMapper<Update, UpdateQuery> updateMapper;

	@Resource
	private AppConfig appConfig;

	/**
	 * 根据条件查询列表
	 */
	public List<Update> findListByParam(UpdateQuery query) {
		return this.updateMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(UpdateQuery query) {
		return this.updateMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Update> findListByPage(UpdateQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<Update> list = this.findListByParam(query);
		PaginationResultVO<Update> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Update bean) {
		return this.updateMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<Update> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.updateMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<Update> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.updateMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Update selectById(Integer id) {
		return this.updateMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateById(Update bean, Integer id) {
		return this.updateMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteById(Integer id) {
		return this.updateMapper.deleteById(id);
	}

	/**
	 * 根据Version查询
	 */
	public Update selectByVersion(String version) {
		return this.updateMapper.selectByVersion(version);
	}

	/**
	 * 根据Version更新
	 */
	public Integer updateByVersion(Update bean, String version) {
		return this.updateMapper.updateByVersion(bean, version);
	}

	/**
	 * 根据Version删除
	 */
	public Integer deleteByVersion(String version) {
		return this.updateMapper.deleteByVersion(version);
	}

	@Override
	public void saveUpdate(Update update, MultipartFile file) throws IOException {
		AppUpdateFileTypeEnum typeEnum = AppUpdateFileTypeEnum.getBytype(update.getFileType());
		if(null==typeEnum) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if(update.getId()!=null) {
			Update dbinfo =this.updateMapper.selectById(update.getId());
			if(dbinfo.getStatus()!=AppUpdateStatusEnum.INIT.getStatus()) {
				throw new BusinessException("已发布无法进行修改");
			}
		}
		
		UpdateQuery updateQuery =new UpdateQuery();
		updateQuery.setOrderBy("id desc");
		updateQuery.setSimplePage(new SimplePage(0,1));
		List<Update> appUpdateList = updateMapper.selectList(updateQuery);
		if(0!=appUpdateList.size()) {
			Update latest = appUpdateList.get(0);
			if(update.getId()==null&&StringTools.versionCompare(latest.getVersion(), update.getVersion())) {
				throw new BusinessException("当前版本必须大于历史版本");
			}			
		}
		if(update.getId()==null) {
			update.setCreateTime(new Date());
			update.setStatus(AppUpdateStatusEnum.INIT.getStatus());
			updateMapper.insert(update);			
		}else {
			update.setStatus(null);
			update.setGrayscaleUid(null);
			updateMapper.insert(update);
		}
		if (file!=null) {
			File folder =new File(appConfig.getProjectFloder()+Constants.APP_UPDATE_FOLDER);
			if (!folder.exists()) {
				folder.mkdirs();				
			}
			file.transferTo(new File(folder.getAbsolutePath()+"/"+update.getId()+Constants.APP_EXE_SUFFIX));
			
		}
		
		
	}

	@Override
	public void postUpdate(Integer id, Integer status, String grayScaleUid) {
		AppUpdateStatusEnum statusEnum =AppUpdateStatusEnum.getByStatus(status);
		if(null==statusEnum) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if(statusEnum==AppUpdateStatusEnum.GRAYSCALE&&grayScaleUid==null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (statusEnum!=AppUpdateStatusEnum.GRAYSCALE) {
			grayScaleUid="null";
		}
		Update update =new Update();
		update.setStatus(status);
		update.setGrayscaleUid(grayScaleUid);
		updateMapper.updateById(update, id);
	}

	@Override
	public Update getLatestUpdate(String vesion, String uid) {
		
		return updateMapper.selectLatestUpdate(vesion, uid);
	}

		
	

}