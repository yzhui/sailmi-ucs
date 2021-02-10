/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.cms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.cms.entity.File;
import com.sailmi.cms.vo.FileVO;
import com.sailmi.cms.wrapper.FileWrapper;
import com.sailmi.cms.service.IFileService;
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 内容附件 控制器
 *
 * @author sailmi
 * @since 2020-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "内容附件", tags = "内容附件接口")
public class FileController extends AppController {

	private IFileService fileService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入file")
	public R<FileVO> detail(File file) {
		File detail = fileService.getOne(Condition.getQueryWrapper(file));
		return R.data(FileWrapper.build().entityVO(detail));
	}

	/**
	* 分页 内容附件
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入file")
	public R<IPage<FileVO>> list(File file, Query query) {
		IPage<File> pages = fileService.page(Condition.getPage(query), Condition.getQueryWrapper(file));
		return R.data(FileWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 内容附件
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入file")
	public R<IPage<FileVO>> page(FileVO file, Query query) {
		IPage<FileVO> pages = fileService.selectFilePage(Condition.getPage(query), file);
		return R.data(pages);
	}

	/**
	* 新增 内容附件
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入file")
	public R save(@Valid @RequestBody File file) {
		return R.status(fileService.save(file));
	}

	/**
	* 修改 内容附件
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入file")
	public R update(@Valid @RequestBody File file) {
		return R.status(fileService.updateById(file));
	}

	/**
	* 新增或修改 内容附件
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入file")
	public R submit(@Valid @RequestBody File file) {
		return R.status(fileService.saveOrUpdate(file));
	}

	
	/**
	* 删除 内容附件
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fileService.deleteLogic(Func.toLongList(ids)));
	}

	
}
