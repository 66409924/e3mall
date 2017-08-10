package cn.e3mall.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {
	
	@Test
	public void testUploadFile() throws Exception {
		// 1、把FastDFS客户端的jar包添加到工程中，jar包在中央仓库中没有。
		// 2、创建一个配置文件。配置trackerServer所在的ip地址和端口号
		// 3、加载配置文件。
		ClientGlobal.init("F:/itheima_class06/workspace/e3-manager-web/src/main/resources/conf/client.conf");
		// 4、创建一个TrackerClient对象，直接new，没有参数。
		TrackerClient trackerClient = new TrackerClient();
		// 5、通过TrackerClient对象获得TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 6、创建一个StorageClinet对象，需要两个参数TrackerServer、StorageServer（null）
		StorageClient storageClient = new StorageClient(trackerServer,null);
		// 7、使用StorageClient上传文件，返回文件的路径及文件名。
		String[] strings = storageClient.upload_file("C:/Users/lenovo/Pictures/lovewallpaper/11577-106.jpg", "jpg", null);
		// 8、打印结果
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastDFSTest() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("F:/itheima_class06/workspace/e3-manager-web/src/main/resources/conf/client.conf");
		String result = fastDFSClient.uploadFile("C:/Users/lenovo/Pictures/lovewallpaper/198007-106.jpg", "jpg");
		System.out.println(result);
	}
}
