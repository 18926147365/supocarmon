package com.ocarmon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * @author 李浩铭
 * @since 2017年7月28日 下午5:31:45
 */
public class OSSClientUtil {
	private final static String ENCODE = "UTF-8";
	// endpoint以杭州为例，其它region请按实际情况填写
	private String endpoint = "oss-cn-shenzhen.aliyuncs.com";
	// accessKey
	private String accessKeyId = "LTAIwP5b0ptQhpfl";
	private String accessKeySecret = "wKDgoHbPC896xBfei9ivkFL99ERBV8";
	// 空间
	private String bucketName = "myocarmon";
	// 文件存储目录
	private String filedir = "test/image/";
	private OSSClient ossClient;

	public OSSClientUtil(String filedir) {
		this.filedir=filedir;
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	public OSSClientUtil() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	/**
	 * 初始化
	 */
	public void init() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		ossClient.shutdown();
	}

	/**
	 * 上传图片
	 *
	 * @param url
	 */
	public void uploadImg2Oss(String url) {
		File fileOnServer = new File(url);
		FileInputStream fin;
		try {
			fin = new FileInputStream(fileOnServer);
			String[] split = url.split("/");
			this.uploadFile2OSS(fin, split[split.length - 1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得图片路径
	 *
	 * @param fileUrl
	 * @return
	 */
	public String getImgUrl(String fileUrl) {
		if (!StringUtils.isEmpty(fileUrl)) {
			String[] split = fileUrl.split("/");
			return this.getUrl(this.filedir + split[split.length - 1]);
		}
		return null;
	}

public static void main(String[] args) throws Exception {
	 URL url = new URL("https://pic2.zhimg.com/v2-6d5e3e82a13f3677711c7a018732b43a_r.jpg");  
     //打开链接  
     HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
     //设置请求方式为"GET"  
     conn.setRequestMethod("GET");  
     //超时响应时间为5秒  
     conn.setConnectTimeout(5 * 1000);  
     //通过输入流获取图片数据  
     InputStream inStream = conn.getInputStream();  
     //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
     
     OSSClientUtil os=new OSSClientUtil();
     os.uploadFile2OSS(inStream,"123123.jpg");
}
	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
			ret = putResult.getETag();
		} catch (IOException e) {
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 *
	 * @param FilenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String FilenameExtension) {
		if (FilenameExtension.equalsIgnoreCase("bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equalsIgnoreCase("gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equalsIgnoreCase("jpeg") || FilenameExtension.equalsIgnoreCase("jpg")
				|| FilenameExtension.equalsIgnoreCase("png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equalsIgnoreCase("html")) {
			return "text/html";
		}
		if (FilenameExtension.equalsIgnoreCase("txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equalsIgnoreCase("vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equalsIgnoreCase("pptx") || FilenameExtension.equalsIgnoreCase("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equalsIgnoreCase("docx") || FilenameExtension.equalsIgnoreCase("doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equalsIgnoreCase("xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}

	/**
	 * 获得url链接
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
		if (url != null) {
			return url.toString();
		}

		return null;
	}

	public void deleteFile(String key) {
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			GenericRequest request = new DeleteObjectsRequest(bucketName)
					.withKey(getURLDecoderString(getFileName(key)));
			ossClient.deleteObject(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getURLDecoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, ENCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getFileName(String fileUrl) {
		String str = "aliyuncs.com/";
		int beginIndex = fileUrl.indexOf(str);
		if (beginIndex == -1)
			return null;
		String st = fileUrl.substring(beginIndex + str.length());
		int i = st.indexOf("?");
		return st.substring(0, i);

	}

}
