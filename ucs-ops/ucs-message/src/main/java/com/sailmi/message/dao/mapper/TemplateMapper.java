package com.sailmi.message.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.message.dao.model.Template;
import com.sailmi.message.model.vo.TemplateVO;


import java.util.List;

public interface TemplateMapper extends BaseMapper<Template> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param template
	 * @return
	 */
	List<TemplateVO> selectTemplatePage(IPage page, TemplateVO template);
}
