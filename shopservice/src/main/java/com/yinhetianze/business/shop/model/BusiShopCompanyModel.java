package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class BusiShopCompanyModel extends BasicModel{
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 营业执照编码
     */
    private String licenseCode;

    /**
     * 营业执照url
     */
    private String businessLicense;

    /**
     * 法人代表
     */
    private String legalOwner;

    /**
     * 注册时间
     */
    private Date regeisterTime;

    /**
     * 税务登记照片url
     */
    private String taxRegeister;

    /**
     * 法人联系电话
     */
    private Integer legalOwnerPhone;

    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    /**
     * 州省
     */
    private String regionLocation;

    /**
     * 城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 区域
     */
    private String areaCounty;

    /**
     * 法人身份证号
     */
    private String idCard;

    /**
     * 身份证正面照
     */
    private String idCardP;

    /**
     * 身份证反面照
     */
    private String idCardN;

    /**
     * 企业门头照
     */
    private String companyPhoto;

    /**
     * 所属消费者
     */
    private Integer customerId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取公司名称  
     *
     * @return company_name - 公司名称  
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称  
     *
     * @param companyName 公司名称  
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取营业执照url  
     *
     * @return business_license - 营业执照url  
     */
    public String getBusinessLicense() {
        return businessLicense;
    }

    /**
     * 设置营业执照url  
     *
     * @param businessLicense 营业执照url  
     */
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    /**
     * 获取法人代表  
     *
     * @return legal_owner - 法人代表  
     */
    public String getLegalOwner() {
        return legalOwner;
    }

    /**
     * 设置法人代表  
     *
     * @param legalOwner 法人代表  
     */
    public void setLegalOwner(String legalOwner) {
        this.legalOwner = legalOwner;
    }

    /**
     * 获取注册时间  
     *
     * @return regeister_time - 注册时间  
     */
    public Date getRegeisterTime() {
        return regeisterTime;
    }

    /**
     * 设置注册时间  
     *
     * @param regeisterTime 注册时间  
     */
    public void setRegeisterTime(Date regeisterTime) {
        this.regeisterTime = regeisterTime;
    }

    /**
     * 获取税务登记照片url  
     *
     * @return tax_regeister - 税务登记照片url  
     */
    public String getTaxRegeister() {
        return taxRegeister;
    }

    /**
     * 设置税务登记照片url  
     *
     * @param taxRegeister 税务登记照片url  
     */
    public void setTaxRegeister(String taxRegeister) {
        this.taxRegeister = taxRegeister;
    }

    /**
     * 获取法人联系电话
     *
     * @return legal_owner_phone - 法人联系电话
     */
    public Integer getLegalOwnerPhone() {
        return legalOwnerPhone;
    }

    /**
     * 设置法人联系电话
     *
     * @param legalOwnerPhone 法人联系电话
     */
    public void setLegalOwnerPhone(Integer legalOwnerPhone) {
        this.legalOwnerPhone = legalOwnerPhone;
    }

    /**
     * 获取0 审核通过 1 审核不通过  2待审核
     *
     * @return audit_status - 0 审核通过 1 审核不通过  2待审核
     */
    public Short getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置0 审核通过 1 审核不通过  2待审核
     *
     * @param auditStatus 0 审核通过 1 审核不通过  2待审核
     */
    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getRegionLocation() {
        return regionLocation;
    }

    public void setRegionLocation(String regionLocation) {
        this.regionLocation = regionLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaCounty() {
        return areaCounty;
    }

    public void setAreaCounty(String areaCounty) {
        this.areaCounty = areaCounty;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardP() {
        return idCardP;
    }

    public void setIdCardP(String idCardP) {
        this.idCardP = idCardP;
    }

    public String getIdCardN() {
        return idCardN;
    }

    public void setIdCardN(String idCardN) {
        this.idCardN = idCardN;
    }

    public String getCompanyPhoto() {
        return companyPhoto;
    }

    public void setCompanyPhoto(String companyPhoto) {
        this.companyPhoto = companyPhoto;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}