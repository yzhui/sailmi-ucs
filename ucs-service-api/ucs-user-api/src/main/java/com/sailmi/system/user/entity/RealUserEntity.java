package com.sailmi.system.user.entity;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

/**
 * 用户真实信息实体类
 * @author syt
 */
public class RealUserEntity {

    /**
     * 用户实名信息ID
     */
    private BigInteger id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 证件类型
     */
    private String papers;

    /**
     * 证件号码
     */
    private String papersNum;

    /**
     * 所在地区
     */
    private String area;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 证件照片正面路径
     */
    private String imgInfo;

    /**
     * 证件照片反面路径
     */
    private String imgInfo2;

    /**
     * 手持证件照路径
     */
    private String imgInfo3;

    /**
     * 证件照片正面
     */
    private MultipartFile file;

    /**
     * 证件照片反面
     */
    private MultipartFile file2;

    /**
     * 证件照片手持
     */
    private MultipartFile file3;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankNum;

    /**
     * 用户ID
     */
    private BigInteger userId;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getPapersNum() {
		return papersNum;
	}

	public void setPapersNum(String papersNum) {
		this.papersNum = papersNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getImgInfo() {
		return imgInfo;
	}

	public void setImgInfo(String imgInfo) {
		this.imgInfo = imgInfo;
	}

	public String getImgInfo2() {
		return imgInfo2;
	}

	public void setImgInfo2(String imgInfo2) {
		this.imgInfo2 = imgInfo2;
	}

	public String getImgInfo3() {
		return imgInfo3;
	}

	public void setImgInfo3(String imgInfo3) {
		this.imgInfo3 = imgInfo3;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile2() {
		return file2;
	}

	public void setFile2(MultipartFile file2) {
		this.file2 = file2;
	}

	public MultipartFile getFile3() {
		return file3;
	}

	public void setFile3(MultipartFile file3) {
		this.file3 = file3;
	}




}
