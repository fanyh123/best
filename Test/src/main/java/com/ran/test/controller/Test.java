package com.ran.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.JedisCluster;

import com.ran.test.entity.User;

@Controller
public class Test {
	
	Logger logger = Logger.getLogger(Test.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/test/test")
	public void test(){
		jedisCluster.set("name", "12345");
		String name = jedisCluster.get("name");
		System.out.println("name finish......"+name);
	}
	
	@RequestMapping("a/test")
	public String a(HttpServletRequest request){//返回页面，视图解析，最终也是要走ModelAndView
		String encode = request.getCharacterEncoding();
		logger.info("a/test + test");
		return "test";
	}
	
	@RequestMapping("b/test")
	public ModelAndView b(){//返回视图和参数
		ModelAndView mav = new ModelAndView();
		mav.addObject("as", "xjx");
		mav.addObject("name", "zs");
		mav.setViewName("test");
		logger.info("------b/test + test");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="c/test")
	@ResponseBody
	/* @ResponseBody对json做处理，必须加入转json格式的依赖包
	 * 对于请求入参，配置HttpMessageConvert处理请求和响应数据，才能自动匹配；否则一定要加@RequestParam注解,不配请求会报错405、404.
	 */
	public List<User> getUserList(String name){
		logger.info("name="+name);
		List<User> listu = new ArrayList<User>();
		User user = new User("zs", "gender", "23");
		listu.add(user);
		return listu;
	}
	
	@RequestMapping("d/file")
	public String fileupload(MultipartFile file) throws IOException{
		if(file != null){
			String name=file.getOriginalFilename();
			logger.info("name="+name);
			String suffix = name.substring(name.lastIndexOf("."));
			logger.info("suffix="+suffix);
			File newFile = new File("test"+suffix);
			String path = newFile.getAbsolutePath();//  d://eclipse
			FileCopyUtils.copy(file.getBytes(), newFile);
		}		
		return "success";
	}

}
