package com.sailmi.system.user.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

public class UploadUtil {
	public static HashMap<String, Object> uploadData(MultipartFile file,String pathUrl,int config) {
		String path = pathUrl;
		File filePath = new File(path);
	    System.out.println("文件的保存路径：" + path);

	    if (!filePath.exists() && !filePath.isDirectory()) {
	        System.out.println("目录不存在，创建目录:" + filePath);
	        filePath.mkdirs();
	    }

	    //获取原始文件名称(包含格式)
	    String originalFileName = file.getOriginalFilename();
	    System.out.println("原始文件名称：" + originalFileName);

	    //获取文件类型，以最后一个`.`为标识
	    String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
	    System.out.println("文件类型：" + type);
	    //获取文件名称（不包含格式）
	    String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
	    //获取文件大小  bytes值
	    long size = file.getSize();

	    String fileName = originalFileName;
	    if(config != 1) {
	    	//设置文件新名称: 当前时间+文件名称（不包含格式）
	    	Date d = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    	String date = sdf.format(d);
	    	//String fileName = date + name + "." + type;
	    	fileName = date + "." + type;
	    	System.out.println("新文件名称：" + fileName);
	    }
	    HashMap<String, Object> hashMap = new HashMap<String,Object>();
	    //在指定路径下创建一个文件
	    File targetFile = new File(path, fileName);
	    //将文件保存到服务器指定位置
	    try {
	    	file.transferTo(targetFile);
	        System.out.println("上传成功");
	        //将文件在服务器的存储路径返回
	        String url=pathUrl+"/"+fileName;
	        hashMap.put("status", 1);
	        hashMap.put("url", url);
	        hashMap.put("fileName", fileName);//新文件名
	        hashMap.put("size", size);//大小
	        hashMap.put("name", name);//名称
	        hashMap.put("type", type);//格式
	    } catch (IOException e) {
	        System.out.println("上传失败");
	        e.printStackTrace();
	        hashMap.put("status", 0);
	        hashMap.put("url", e);
	    }
		return hashMap;
	}

	 public HttpServletResponse download(String path, HttpServletResponse response) {
	        try {
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            String ext = filename.substring(filename.lastIndexOf(",") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return response;
	    }




}
