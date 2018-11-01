package com.yinhetianze.common.fileupload;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件上传适配类
 * 实现类可继承此类做相应的文件操作
 */
public class FileUploadAdapter implements IFileUpload
{
    @Override
    public String fileUpload(File file) throws Exception
    {
        throw new Exception("请实现满足当前业务的上传文件方法");
    }

    @Override
    public String fileUpload(File file, String destPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的上传文件方法");
    }

    @Override
    public String fileUpload(File file, String destPath, String fileName) throws Exception
    {
        throw new Exception("请实现满足当前业务的上传文件方法");
    }

    @Override
    public String fileUpload(InputStream file, String destPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的上传文件方法");
    }

    @Override
    public String fileUpload(InputStream file, String destPath, String fileName) throws Exception
    {
        throw new Exception("请实现满足当前业务的上传文件方法");
    }

    @Override
    public List<String> batchFileUpload(Map<String, File> fileList, String destPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的批量上传文件的方法");
    }

    @Override
    public Boolean deleteFile(File file) throws Exception
    {
        throw new Exception("请实现满足当前业务的删除文件方法");
    }

    @Override
    public Boolean deleteFile(File file, String descPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的删除文件方法");
    }

    @Override
    public Boolean deleteFile(String fileName, String descPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的删除文件方法");
    }

    @Override
    public Boolean deleteFile(String filePath) throws Exception
    {
        throw new Exception("请实现满足当前业务的删除文件方法");
    }

    @Override
    public Object batchDeleteFile(Map<String, File> fileMap, String destPath) throws Exception
    {
        throw new Exception("请实现满足当前业务的批量删除文件的方法");
    }
}
