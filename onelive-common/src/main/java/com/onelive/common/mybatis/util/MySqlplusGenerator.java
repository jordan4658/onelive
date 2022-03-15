package com.onelive.common.mybatis.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @ClassName:
 * @Description: mybatis-plus 连接mysql生成增删改查代码
 * @author: muyu
 * @date: 2020-7-27 13:59:49
 */
public class MySqlplusGenerator {
    public static void main(String[] args) {

        String moduleName = scanner("输入all 不区分路径生成，否则按照模块包名生成");//模块名可根据实际情况是否需要
        //指定表生成，不指定则全部生成
        //sc.setInclude("sys_parameter");
        String tables = scanner("输入all 全库生成 ，输入表名单个或多个，多个英文逗号分割");

        AutoGenerator ag = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取当前项目的路径
        projectPath = "C:";
        String projectName = "onelive-common";//模块名可根据实际情况是否需要
        gc.setOutputDir(projectPath + "/" + projectName + "/src/main/java");//配置生成的代码目录
        //gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setIdType(IdType.AUTO);  //自增
        gc.setDateType(DateType.ONLY_DATE);    //日期类型用 java.util.date
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        ag.setGlobalConfig(gc);


        //数据源配置
        DataSourceConfig ds = new DataSourceConfig();
        ds.setUrl("jdbc:mysql://database-1.cpbp5ehdkqcm.ap-northeast-2.rds.amazonaws.com:3306/onelive?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8");
        ds.setDriverName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("24fhMJTuuWUWvfHi");
        ag.setDataSource(ds);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.onelive.common.mybatis");//自己的包名

        if (StringUtils.isNotBlank(moduleName) && !moduleName.equals("all")) {
            //配置路径
            pc.setEntity("entity");
            pc.setMapper("mapper.master." + moduleName);
            pc.setService("service." + moduleName);
            pc.setServiceImpl("service." + moduleName + ".impl");
            pc.setController("");
            //不生成Controller 和 xml 两个命名的包(不是指**Controller.java 和 *.xml文件)
            //因为我们项目没有Controller层 和使用的是MyBatis的注解,所以不需要生成Controller和XML文件
            //pc.setController("controller." + moduleName);
            pc.setXml("xml.master." + moduleName);
        }
        ag.setPackageInfo(pc);

        //策略配置
        StrategyConfig sc = new StrategyConfig();

        if (StringUtils.isNotBlank(tables) && !tables.equals("all")) {
            sc.setInclude(tables.split(","));
        }
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);//开启驼峰命名
//		sc.setSuperControllerClass("com.fl.play.base.BaseController");
        sc.setControllerMappingHyphenStyle(true);

        //自动填充的配置
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);//设置时的生成策略
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);//设置更新时间的生成策略
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(create_time);
        list.add(update_time);
        sc.setTableFillList(list);

        ag.setStrategy(sc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        ag.setTemplate(templateConfig);

        //执行生成文件
        ag.execute();

//-----------------------------------------------------------------------------------------------------
/**                     生成从库的基础代码       */
//-----------------------------------------------------------------------------------------------------
/**-----------------------------------------------------------------------------------------------------
 生成从库的基础代码
 //-----------------------------------------------------------------------------------------------------*/

        AutoGenerator ag_slave = new AutoGenerator();
        //全局配置
        GlobalConfig gc_slave = new GlobalConfig();
        String projectPath_slave = System.getProperty("user.dir");//获取当前项目的路径
        projectPath_slave = "C:";
        String projectName_slave = "onelive-common";//模块名可根据实际情况是否需要
        gc_slave.setOutputDir(projectPath_slave + "/" + projectName_slave + "/src/main/java");//配置生成的代码目录
        //gc.setSwagger2(true);
        gc_slave.setBaseColumnList(true);
        gc_slave.setBaseResultMap(true);
        gc_slave.setIdType(IdType.AUTO);  //自增
        gc_slave.setDateType(DateType.ONLY_DATE);    //日期类型用 java.util.date
        gc_slave.setServiceName("%sService");
        gc_slave.setServiceImplName("%sServiceImpl");
        gc_slave.setMapperName("%sMapper");
        gc_slave.setXmlName("%sMapper");
        gc_slave.setMapperName("Slave%sMapper");
        gc_slave.setXmlName("Slave%sMapper");
        ag_slave.setGlobalConfig(gc_slave);

        //数据源配置
        DataSourceConfig ds_slave = new DataSourceConfig();
        ds_slave.setUrl("jdbc:mysql://192.168.1.110:3306/onelive?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8");
        ds_slave.setDriverName("com.mysql.cj.jdbc.Driver");
        ds_slave.setUsername("root");
        ds_slave.setPassword("root");
        ag_slave.setDataSource(ds_slave);

        // 包配置
        PackageConfig pc_slave = new PackageConfig();
        pc_slave.setParent("com.onelive.common.mybatis");//自己的包名

        if (StringUtils.isNotBlank(moduleName) && !moduleName.equals("all")) {
            //配置路径
            pc_slave.setEntity("entity");
            pc_slave.setMapper("mapper.slave." + moduleName);
            pc_slave.setService("service." + moduleName);
            pc_slave.setServiceImpl("service." + moduleName + ".impl");
            pc_slave.setController("");
            //不生成Controller 和 xml 两个命名的包(不是指**Controller.java 和 *.xml文件)
            //因为我们项目没有Controller层 和使用的是MyBatis的注解,所以不需要生成Controller和XML文件
            //pc.setController("controller." + moduleName);
            pc_slave.setXml("xml.slave." + moduleName);
        }
        ag_slave.setPackageInfo(pc_slave);

        //策略配置
        StrategyConfig sc_slave = new StrategyConfig();

        if (StringUtils.isNotBlank(tables) && !tables.equals("all")) {
            sc_slave.setInclude(tables.split(","));
        }

        sc_slave.setNaming(NamingStrategy.underline_to_camel);
        sc_slave.setColumnNaming(NamingStrategy.underline_to_camel);
        sc_slave.setEntityLombokModel(true);
        sc_slave.setRestControllerStyle(true);
//		sc_slave.setSuperControllerClass("com.fl.play.base.BaseController");
        sc_slave.setControllerMappingHyphenStyle(true);
        ag_slave.setStrategy(sc_slave);

        // 配置模板
        TemplateConfig templateConfig_slave = new TemplateConfig();
        templateConfig_slave.setController(null);
        ag_slave.setTemplate(templateConfig_slave);

        //执行生成文件
        ag_slave.execute();
    }

    /**
     * <p>
     * 读取控制台内容,用于自己输入要生成的模块(生成后以文件夹形式)和表名
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append(tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}

