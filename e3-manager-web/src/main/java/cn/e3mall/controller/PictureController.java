package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {
	
	@Value("${image.server.url}")
	private String imageServerUrl;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			// 1、把commons-io、commons-fileupload包添加到工程中。
			// 2、在springmvc的配置文件中配置多部件解析器。
			// 3、在Controller中添加一个方法接收上传的文件，参数MultiPartFile uploadFile
			// 4、把文件上传到图片服务器
			// 1）取文件原始名称
			String originalFilename = uploadFile.getOriginalFilename();
			// 2）取文件扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			// 3）创建一个FastDFSClient对象
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			// 4）上传文件，返回url
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			// 5、把返回的url拼接成完成的url。
			url = imageServerUrl + url;
			// 6、创建一个map
			Map map = new HashMap<>();
			// 7、把返回的结果添加到map中
			map.put("error", 0);
			map.put("url", url);
			// 8、返回map
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			Map map = new HashMap<>();
			// 7、把返回的结果添加到map中
			map.put("error", 1);
			map.put("message", "上传失败");
			// 8、返回map
			return JsonUtils.objectToJson(map);
		}
	}
}
