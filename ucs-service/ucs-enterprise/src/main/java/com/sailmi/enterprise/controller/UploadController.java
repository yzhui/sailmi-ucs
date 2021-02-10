package com.sailmi.enterprise.controller;

import com.sailmi.system.entity.UpResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/upload")
public class UploadController {

	/**
	 * 文件上传
	 *使用ucs上传组件后该接口废弃
	 * @param picture
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
	public UpResult upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) {

		// 获取文件在服务器的储存位置
		// String path =
		// request.getSession().getServletContext().getRealPath("D:\\home\\ftpuser\\");
//		String path = "/var/www/html/ftpuser";
		String path = "/usr/share/nginx/html/ucs/ftpuser";
		File filePath = new File(path);
		System.out.println("文件的保存路径：" + path);
		if (!filePath.exists() && !filePath.isDirectory()) {
			System.out.println("目录不存在，创建目录:" + filePath);
			filePath.mkdir();
		}

		// 获取原始文件名称(包含格式)
		String originalFileName = picture.getOriginalFilename();
		System.out.println("原始文件名称：" + originalFileName);

		// 获取文件类型，以最后一个`.`为标识
		String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		System.out.println("文件类型：" + type);
		// 获取文件名称（不包含格式）
		String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

		// 设置文件新名称: 当前时间+文件名称（不包含格式）
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(d);
		String fileName = date + name + "." + type;
		// String fileName = date + "." + type;
		System.out.println("新文件名称：" + fileName);

		// 在指定路径下创建一个文件
		File targetFile = new File(path, fileName);

		// 将文件保存到服务器指定位置
		try {
			picture.transferTo(targetFile);
			System.out.println("上传成功");
			// 将文件在服务器的存储路径返回
			return new UpResult(true, fileName);
		} catch (IOException e) {
			System.out.println("上传失败");
			e.printStackTrace();
			return new UpResult(false, "上传失败");
		}
	}*/
}
