package com.yinhetianze.common.fileupload;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IFileUpload
{
    /**
     * 上传指定的文件对象
     * @param file 被上传的目标文件对象
     * @return 上传后的文件名称
     */
    String fileUpload(File file) throws Exception;

    /**
     * 上传指定的文件到服务器的目标目录。文件名称不变
     * @param file 被上传的文件对象
     * @param destPath 上传到服务器的目标目录
     * @return 上传后的文件名称
     */
    String fileUpload(File file, String destPath) throws Exception;

    /**
     * 上传指定文件对象到服务器的指定目录并命名成新的文件对象
     * @param file 被上传的文件对象
     * @param destPath 上传到服务器的目标目录
     * @param fileName 上传到服务器后最终保存的文件名称
     * @return
     */
    String fileUpload(File file, String destPath, String fileName) throws Exception;

    /**
     * 上传指定文件对象到服务器的指定目录并命名成新的文件对象
     * @param file 被上传的文件输入流
     * @param destPath 上传到服务器的目标目录
     * @return
     */
    String fileUpload(InputStream file, String destPath) throws Exception;

    /**
     * 上传指定文件对象到服务器的指定目录并命名成新的文件对象
     * @param file 被上传的文件输入流
     * @param destPath 上传到服务器的目标目录
     * @param fileName 上传到服务器后最终保存的文件名称
     * @return
     */
    String fileUpload(InputStream file, String destPath, String fileName) throws Exception;

    /**
     * 文件批量上传
     * @param fileList Map类型参数中，String为文件最终命名，File为上传的文件对象
     * @param destPath 保存到上传的服务器路径
     * @return
     * @throws Exception
     */
    List<String> batchFileUpload(Map<String, File> fileList, String destPath) throws Exception;

    /**
     * 删除制定的文件对象
     * @param file
     * @return
     */
    Boolean deleteFile(File file) throws Exception;

    /**
     * 删除制定目录下的文件对象
     * @param file 被删除的文件对象
     * @param descPath 被删除的文件对象所在目录
     * @return
     */
    Boolean deleteFile(File file, String descPath) throws Exception;

    /**
     * 删除制定目录下的文件对象
     * @param fileName 被删除的文件名称
     * @param descPath 被删除的文件对象所在目录
     * @return
     */
    Boolean deleteFile(String fileName, String descPath) throws Exception;

    /**
     * 删除指定名字的文件
     * @param filePath
     * @return
     * @throws Exception
     */
    Boolean deleteFile(String filePath) throws Exception;

    /**
     * 批量删除文件
     * @param fileMap
     * @param destPath
     * @return
     * @throws Exception
     */
    Object batchDeleteFile(Map<String, File> fileMap, String destPath) throws Exception;
}
