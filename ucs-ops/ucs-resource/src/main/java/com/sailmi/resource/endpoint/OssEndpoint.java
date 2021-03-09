/**
 * Copyright (c) 2018-2028.
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
package com.sailmi.resource.endpoint;

import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.provider.OssProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.sailmi.core.oss.model.GeneralFile;
import com.sailmi.core.oss.model.OssFile;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.Locale;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor()
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class OssEndpoint {
	//初始化OSSProperties
	@Autowired
	private OssProvider ossProvider;

	/**
	 * 获取文件信息
	 *
	 * @param fileName 对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/file/{fileName}")
	public HttpServletResponse File(@PathVariable("fileName") String fileName) {
		//缺省为公共的库，后面为每一个企业指定库。
		String buckets="public";
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();
		System.out.println("request file name is:"+fileName);
		InputStream fis= ossProvider.getFile(buckets,fileName);
		response.setContentType("application/octet-stream");
		OutputStream outputStream = response.getOutputStream();
		int bufferLen= 1024;
		byte[] buffer = new byte[bufferLen];
		int readLen = fis.read(buffer);
		while(readLen>0){
			outputStream.write(buffer,0,readLen);
			readLen = fis.read(buffer);
		}
		outputStream.close();
		fis.close();
		return response;
	}


	/**
	 * 获取文件信息
	 *
	 * @param fileName 对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public R<OssFile> statFile(@RequestParam String fileName) {
		String buckets="public";

		return R.data(ossProvider.statFile(buckets,fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public R<String> filePath(@RequestParam String fileName) {
		String buckets="public";
		return R.data(ossProvider.filePath(buckets,fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	@ApiOperation("获取文件链接")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fileName", value = "文件名字", required = true)
	})
	public R<String> fileLink(@RequestParam String fileName) {

		String buckets="public";
		return R.data(ossProvider.fileLink(buckets,fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	@ApiOperation("上传文件并自动生成文件名字")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file", value = "二进制文件", required = true)
	})
	public R<GeneralFile> putFile(@RequestParam MultipartFile file) {
		String buckets="public";

		GeneralFile generalFile = null;
		generalFile = ossProvider.putFile(buckets,file.getOriginalFilename(), file);
		System.out.println("dataFile file link is:"+generalFile.getLink());
		System.out.println("dataFile file name is:"+generalFile.getName());
		System.out.println("dataFile file original name is:"+generalFile.getOriginalName());
		return R.data(generalFile);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	@ApiOperation("上传文件，并以指定的名字存储")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fileName", value = "文件名", required = true),
		@ApiImplicitParam(name = "file", value = "二进制文件", required = true)
	})
	public R<GeneralFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		String buckets="public";

		GeneralFile generalFile = null;
		generalFile = ossProvider.putFile(file.getOriginalFilename(), (MultipartFile) file.getInputStream());
		return R.data(generalFile);
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	public R removeFile(@RequestParam String fileName) {
		String buckets="public";

		ossProvider.removeFile(buckets,fileName);
		return R.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	public R removeFiles(@RequestParam String fileNames) {
		String buckets="public";

		ossProvider.removeFiles(buckets,Func.toStrList(fileNames));
		return R.success("操作成功");
	}
}
