/**
 * @filename:UserController 2019年4月9日
 * @project wallet-manage  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.zzw.generator;

import com.zzw.generator.entity.BasisInfo;
import com.zzw.generator.util.EntityInfoUtil;
import com.zzw.generator.util.Generator;
import com.zzw.generator.util.MySqlToJavaUtil;

import java.sql.SQLException;
import java.util.Date;
/**
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 自动生成工具</P>
 * <p>源码地址：https://gitee.com/flying-cattle/mybatis-dsc-generator</P>
 */
public class MyGenerator {
		// 基础信息：项目名、作者、版本
		public static final String PROJECT = "hh-blog";
		public static final String AUTHOR = "zzw";
		public static final String VERSION = "V1.0";
		// 数据库连接信息：连接URL、用户名、秘密、数据库名
		public static final String URL = "jdbc:mysql://zzw-mysql:3306/hh-blog?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT&useSSL=false&allowPublicKeyRetrieval=true";
		public static final String NAME = "zhangzhiwen";
		public static final String PASS = "113536";
		public static final String DATABASE = "hh-blog";
		// 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
		public static final String CLASSNAME = "tag";
		public static final String TABLE = "zzw_tag";
		public static final String CLASSCOMMENT = "博客标签相关";
		public static final String TIME = "2020年07月13日";
		public static final String AGILE = new Date().getTime() + "";
		// 路径信息，分开路径方便聚合工程项目，微服务项目
		public static final String ENTITY_URL = "com.zzw.core.api.po";
		public static final String DAO_URL = "com.zzw.blog.mapper";
		public static final String XML_URL = "com.zzw.blog.mapper";
		public static final String SERVICE_URL = "com.zzw.blog.service";
		public static final String SERVICE_IMPL_URL = "com.zzw.blog.service.impl";
		public static final String CONTROLLER_URL = "com.zzw.blog.controller";
		//是否是Swagger配置
		public static final String IS_SWAGGER = "true";
		
	public static void main(String[] args) {
		BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, TIME, AGILE, ENTITY_URL,
				DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL,IS_SWAGGER);
		bi.setTable(TABLE);
		bi.setEntityName(MySqlToJavaUtil.getClassName(CLASSNAME));
		bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
		bi.setEntityComment(CLASSCOMMENT);
		try {
			bi = EntityInfoUtil.getInfo(bi);
			String entityUrl = "F:\\studyProject\\hh-blog\\zzw-service\\zzw-module\\zzw-core-api\\src\\main\\java\\";// 生成文件存放位置
			String   fileUrl = "F:\\studyProject\\hh-blog\\zzw-service\\zzw-module\\zzw-blog\\src\\main\\java\\";// 生成文件存放位置
			//开始生成文件
			String aa1 = Generator.createEntity(entityUrl, bi).toString();
			String aa2 = Generator.createDao(fileUrl, bi).toString(); 
			String aa3 = Generator.createDaoImpl(fileUrl, bi).toString();
			String aa4 = Generator.createService(fileUrl, bi).toString(); 
			String aa5 = Generator.createServiceImpl(fileUrl, bi).toString(); 
			String aa6 = Generator.createController(fileUrl, bi).toString();
			// 是否创建swagger配置文件
			String aa7 = Generator.createSwaggerConfig(fileUrl, bi).toString();
			
			System.out.println(aa1);
			System.out.println(aa2); System.out.println(aa3); System.out.println(aa4);
			System.out.println(aa5); System.out.println(aa6); System.out.println(aa7);
			
			//System.out.println(aa7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
