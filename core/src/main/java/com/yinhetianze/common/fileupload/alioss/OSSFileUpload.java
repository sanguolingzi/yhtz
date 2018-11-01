package com.yinhetianze.common.fileupload.alioss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.FileUploadAdapter;
import com.yinhetianze.core.utils.Asserts;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * 阿里云oss文件访问工具类
 */
@Service
public class OSSFileUpload extends FileUploadAdapter
{
    /**
     * 必须配置的阿里云终端链接点
     */
//    @Value("${aliyun.endpoint}")
//    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

    /**
     * 阿里云访问key
     */
//    @Value("${aliyun.accessKeyId}")
//    private String accessKeyId = "LTAI5ATikrnJch2J"; // 所有权限
//    private String accessKeyId = "LTAID2ZUQjTdvv39"; // oss和sms

    /**
     * 阿里云访问密钥
     */
//    @Value("${aliyun.accessKeySecret}")
//    private String accessKeySecret = "FhEWBxBQeT2nZQVkczwn7AnkDkKgDq"; // 所有权限
//    private String accessKeySecret = "7ycXJuAF6SVxLr2WZfu1RywBOEyVQr"; // oss和sms

//    @Value("${aliyun.maxConnections}")
//    private Integer maxConnections = 500;

//    @Value("${aliyun.socketTimeout}")
//    private Integer socketTimeout = 50 * 1000;

//    @Value("${aliyun.maxErrorRetry}")
//    private Integer maxErrorRetry = 3;

//    @Value("${aliyun.supportCname}")
//    private Boolean isSupportCname = true;

    /**
     * defaultBuketName是企业在aliyun的oss服务上创建的能够唯一标识该企业的命名空间,如：buketName=yinhetianze
     * 则对应的最终图片访问路径为：https://{buketName}.oss-cn-hangzhou.aliyuncs.com
     */
//    @Value("${aliyun.defaultBuketName}")
//    private String defaultBuketName = "yhtz-20180425-private"; //默认的文件存储空间名字

    private ClientConfiguration conf = null;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    /*public OSSFileUpload()
    {
        init();
    }*/

    /*public OSSFileUpload(String endpoint, String accessKeyId, String accessKeySecret)
    {
        Assert.notNull(endpoint, "终端地址不能为空");
        Assert.notNull(accessKeyId, "访问ID不能为空");
        Assert.notNull(accessKeySecret, "访问密钥不能为空");

        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;

        init();
    }*/

    /*private void init()
    {
        // 配置项设置
        conf = new ClientConfiguration();
        conf.setMaxConnections(maxConnections);
        conf.setMaxErrorRetry(maxErrorRetry);
        conf.setSocketTimeout(socketTimeout);
        if (CommonUtil.isEmpty(isSupportCname))
        {
            conf.setSupportCname(isSupportCname);
        }
    }*/

    private synchronized ClientConfiguration getConf()
    {
        if (CommonUtil.isEmpty(conf))
        {
            // 配置项设置
            conf = new ClientConfiguration();

            conf.setMaxConnections(sysPropertiesUtils.getIntValue("aliyun.oss.maxConnections", 500));
            conf.setMaxErrorRetry(sysPropertiesUtils.getIntValue("aliyun.oss.maxErrorRetry", 3));
            conf.setSocketTimeout(sysPropertiesUtils.getIntValue("aliyun.oss.socketTimeout", 50 * 1000));
            Boolean supportCname = sysPropertiesUtils.getBoolValue("aliyun.oss.supportCname", true);
            if (CommonUtil.isEmpty(supportCname))
            {
                conf.setSupportCname(supportCname);
            }
        }

        return conf;
    }

    /**
     * 直接上传文件
     * 以文件名称作为访问文件
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public String fileUpload(File file) throws Exception
    {
        if (CommonUtil.isNotEmpty(file))
        {
            return fileUpload(file, null, file.getName());
        }
        else
        {
            return null;
        }
    }

    @Override
    public String fileUpload(File file, String destPath) throws Exception
    {
        if (CommonUtil.isNotEmpty(file))
        {
            return fileUpload(file, destPath, file.getName());
        }
        else
        {
            return null;
        }
    }

    /**
     * 根据oss服务器的目录结构进行文件上传
     * 上传后的文件访问形式为：http://{buketName}.endpoint/{destPath}/{fileName}
     * 其中buketName，destPath，fileName均为oss上传的配置项和方法参数
     *
     * @param file 被上传的文件对象
     * @param destPath oss服务buketName下的子目录文件夹
     * @param fileName 最终保存的文件名字
     * @return destPath/fileName的格式的字符串
     * @throws Exception
     */
    @Override
    public String fileUpload(File file, String destPath, String fileName) throws Exception
    {
        Asserts.notEmpty(file, "上传的文件对象不能为空");

        // 获取文件名称
        if (CommonUtil.isEmpty(fileName))
        {
            fileName = file.getName();
        }
        /*// 文件访问名称
        String fileKey = generateFileKey(destPath, fileName);

        try
        {
            // 客户端初始化
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
            // 判断上传文件的空间是否存在，不存在则创建空间
            ossClient.putObject(defaultBuketName, fileKey, new FileInputStream(file));

            LoggerUtil.info(OSSFileUpload.class, "文件上传操作成功，上传文件[{}]", new Object[]{fileKey});
            ossClient.shutdown();
        }
        catch (Exception e)
        {
            LoggerUtil.error(OSSFileUpload.class, e);
            throw e;
        }

        return fileKey;*/

        return fileUpload(new FileInputStream(file), destPath, fileName);
    }

    @Override
    public String fileUpload(InputStream file, String destPath) throws Exception
    {
        return super.fileUpload(file, destPath, null);
    }


    @Override
    public String fileUpload(InputStream file, String destPath, String fileName) throws Exception{
        return fileUpload(file,destPath,fileName,null);
    }


    public String fileUpload(InputStream file, String destPath, String fileName,Map<String,Object> metaMap) throws Exception
    {
        String defaultBuketName = sysPropertiesUtils.getStringValue("aliyun.oss.defaultBuketName");
        String endpoint = sysPropertiesUtils.getStringValue("aliyun.oss.endpoint");
        String accessKeyId = sysPropertiesUtils.getStringValue("aliyun.oss.accessKeyId");
        String accessKeySecret = sysPropertiesUtils.getStringValue("aliyun.oss.accessKeySecret");

        Asserts.notEmpty(file, "上传的文件内容不能为空");
        Asserts.notBlank(defaultBuketName, "文件上传配置参数[defaultBuketName]不能为空");
        Asserts.notBlank(endpoint, "文件上传配置参数[endpoint]不能为空");
        Asserts.notBlank(accessKeyId, "文件上传配置参数[accessKeyId]不能为空");
        Asserts.notBlank(accessKeySecret, "文件上传配置参数[accessKeySecret]不能为空");

        if (CommonUtil.isEmpty(fileName))
        {
            fileName = String.valueOf(new Date().getTime());
        }
        // 文件访问名称
        String fileKey = generateFileKey(destPath, fileName);

        try
        {

            // 创建上传文件的元信息，可以通过文件元信息设置HTTP header。
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentEncoding("utf-8");
            if(metaMap != null && !metaMap.isEmpty()){

                Object contentType = metaMap.getOrDefault("contentType",null);
                if(contentType != null)
                    meta.setContentType(contentType.toString());

                //String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(content.getBytes()));
                // 开启文件内容MD5校验。开启后OSS会把您提供的MD5与文件的MD5比较，不一致则抛出异常。
                //meta.setContentMD5(md5);
                // 指定上传的内容类型。内容类型决定浏览器将以什么形式、什么编码读取文件。如果没有指定则根据文件的扩展名生成，如果没有扩展名则为默认值application/octet-stream。
                //meta.setContentType("text/plain");
                // 设置内容被下载时的名称。
                //meta.setContentDisposition("Download File Name");
                // 设置上传文件的长度。如超过此长度，则会被截断，为设置的长度。如不足，则为上传文件的实际长度。
                //meta.setContentLength(content.length());
                // 设置内容被下载时网页的缓存行为。
                //meta.setCacheControl("Download Action");
                // 设置缓存过期时间，格式是格林威治时间（GMT）。
                //meta.setExpirationTime(DateUtil.parseIso8601Date("2022-10-12T00:00:00.000Z"));
                // 设置内容被下载时的编码格式。
                //meta.setContentEncoding("utf-8");
                // 设置header。
                //meta.setHeader("<yourHeader>", "<yourHeaderValue>");
            }

            // 客户端初始化
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, getConf());
            // 判断上传文件的空间是否存在，不存在则创建空间
            ossClient.putObject(defaultBuketName, fileKey, file,meta);

            LoggerUtil.info(OSSFileUpload.class, "文件上传操作成功，上传文件[{}]", new Object[]{fileKey});
            ossClient.shutdown();
        }
        catch (Exception e)
        {
            LoggerUtil.error(OSSFileUpload.class, e);
            throw e;
        }
        
        return fileKey;
    }

    @Override
    public List<String> batchFileUpload(Map<String, File> fileList, String destPath) throws Exception
    {
        List<String> uploadedFile = new ArrayList<>();
        List<String> errorFile = new ArrayList<>();

        if (CommonUtil.isNotEmpty(fileList))
        {
            Set<String> keySet = fileList.keySet();
            for (String key : keySet)
            {
                try
                {
                    uploadedFile.add(fileUpload(fileList.get(key), destPath, key));
                }
                catch (Exception e)
                {
                    errorFile.add(key);
                    LoggerUtil.error(OSSFileUpload.class, "批量文件上传文件发生异常：文件[{}]没有上传成功", new Object[]{key});
                }
            }
        }

        LoggerUtil.info(OSSFileUpload.class,"批量文件上传操作结束,成功上传[{}]个文件，失败文件[{}]个：[{}]", new Object[]{uploadedFile.size(), errorFile.size(), errorFile});
        return uploadedFile;
    }

    /**
     * 删除上传的文件
     * 根据filePath将oss服务上的文件删除
     * @param filePath 对应oss服务器上的fileKey
     * @return
     * @throws Exception
     */
    @Override
    public Boolean deleteFile(String filePath) throws Exception
    {
        String defaultBuketName = sysPropertiesUtils.getStringValue("aliyun.oss.defaultBuketName");
        String endpoint = sysPropertiesUtils.getStringValue("aliyun.oss.endpoint");
        String accessKeyId = sysPropertiesUtils.getStringValue("aliyun.oss.accessKeyId");
        String accessKeySecret = sysPropertiesUtils.getStringValue("aliyun.oss.accessKeySecret");

        Asserts.notEmpty(filePath, "上传的文件路径不能为空");
        Asserts.notBlank(defaultBuketName, "文件上传配置参数[defaultBuketName]不能为空");
        Asserts.notBlank(endpoint, "文件上传配置参数[endpoint]不能为空");
        Asserts.notBlank(accessKeyId, "文件上传配置参数[accessKeyId]不能为空");
        Asserts.notBlank(accessKeySecret, "文件上传配置参数[accessKeySecret]不能为空");

        if (CommonUtil.isNotEmpty(filePath))
        {
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret, getConf());
            try
            {
                if (client.doesObjectExist(defaultBuketName, filePath))
                {
                    client.deleteObject(defaultBuketName, filePath);
                    LoggerUtil.info(OSSFileUpload.class, "删除文件成功：{}", new Object[]{filePath});
                    client.shutdown();
                    return true;
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(OSSFileUpload.class, e);
            }
        }

        LoggerUtil.info(OSSFileUpload.class, "删除文件未成功：{}", new Object[]{filePath});
        return false;
    }

    @Override
    public Boolean deleteFile(String destPath, String filePath) throws Exception
    {
        String fileKey = generateFileKey(destPath, filePath);
        return deleteFile(fileKey);
    }

    @Override
    public Object batchDeleteFile(Map<String, File> fileMap, String destPath) throws Exception
    {
        // 删除失败的文件集合
        List<String> failedFile = new ArrayList<>();
        if (CommonUtil.isNotEmpty(fileMap))
        {
            // 最终文件的标志
            String fileKey = null;
            // 根据fileMap的key值进行循环
            Set<String> keySet = fileMap.keySet();
            for (String key : keySet)
            {
                try
                {
                    // 拼凑key值
                    fileKey = generateFileKey(destPath, key);
                    // 执行删除，判断是否删除成功
                    if (!deleteFile(fileKey))
                    {
                        // 未成功添加文件到失败集合中
                        failedFile.add(key);
                    }
                }
                catch (Exception e)
                {
                    // 记录异常日志
                    LoggerUtil.error(OSSFileUpload.class, "删除文件失败：{}", new Object[]{key});
                }
            }
        }

        // 记录执行操作日志
        LoggerUtil.info(OSSFileUpload.class, "批量删除文件操作完成，[{}]执行成功，[{}]执行失败：{}", new Object[]{fileMap.size() - failedFile.size(), failedFile.size(), failedFile});
        return failedFile;
    }

    /* ============================================= 私有方法分区 begin ======================================================*/
    /**
     * 去掉首尾的第一个/
     * @param destPath
     * @param fileName
     * @return
     */
    private static String generateFileKey(String destPath, String fileName)
    {
        StringBuffer sb = new StringBuffer();
        if (CommonUtil.isNotEmpty(destPath))
        {
            // 文件路径destPath去掉首尾的/
            int start = destPath.indexOf(CommonConstant.CHAR_SLASH) == 0 ? 1 : 0; // 开始字符去掉/
            int end = destPath.lastIndexOf(CommonConstant.CHAR_SLASH) == destPath.length() - 1 ? destPath.length() - 1 : destPath.length() - 0; //结束字符去掉/
            sb.append(destPath.substring(start, end));
        }
        if (CommonUtil.isNotEmpty(fileName))
        {
            if (CommonUtil.isNotEmpty(destPath))
            {
                sb.append(CommonConstant.CHAR_SLASH);
            }
            // destPath和fileName中间添加/
            // 文件名fileName去掉首字母/
            sb.append(fileName.substring(fileName.indexOf(CommonConstant.CHAR_SLASH) == 0 ? 1 : 0));
        }
        return sb.toString();
    }
}
