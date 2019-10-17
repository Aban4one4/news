package com.yc.news.dao;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.yc.news.utils.LogUtil;

public class DBHelper {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private  ResultSet rs=null;
	private CallableStatement cs=null;

	public Connection getConnection(){
		//javax.naming.Context提供了查找JNDI 的接口
		try {
			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/news");
			con=ds.getConnection(); //与JNDI获取到的数据源建立连接
		} catch (NamingException e) {
			e.printStackTrace();
			LogUtil.log.error(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.log.error(e.toString());
		}
		return con;
	}


	/**
	 * 关闭的方法
	 */
	public void closeAll(Connection con,PreparedStatement pstmt,ResultSet rs,CallableStatement cs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
			}
		}
		
		if(cs!=null){
			try {
				cs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
			}
		}
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
			}
		}
	}

	/**
	 * 设置PreparedStatement对象的sql语句中的参数？
	 */
	public void setValues(PreparedStatement pstmt,List<Object> params){
		if(pstmt!=null&&params!=null&&params.size()>0){
			for(int i=0;i<params.size();i++){
				Object obj=params.get(i);
				try {
					if("javax.sql.rowset.serial.SerialBlob".equals(obj.getClass().getName())){
						pstmt.setBlob(i+1, (Blob)obj);
					}if("java.lang.Integer".equals(obj.getClass().getName()) ){
						pstmt.setInt(i+1, (Integer)obj);
					}else{
						pstmt.setString(i+1,(String)obj);
					}
				} catch (SQLException e) {
					LogUtil.log.error(e.toString());
				}
			}
		}
	}

	/**
	 * 增删改
	 * @param sql：sql语句集合，里面可以加？
	 * @param params：表示?对应的参数值的集合
	 * @return int:返回的值。成功>0，失败<=0
	 */
	public synchronized int update(List<String> sql,List<List<Object>> params){
		int result=0;
		con=getConnection();	
		try {
			con.setAutoCommit(false);  //事务处理
			for(int i=0;i<sql.size();i++){
				List<Object> param=params.get(i);
				pstmt=con.prepareStatement(sql.get(i));  //预编译对象
				setValues(pstmt,param);    //设置参数
				result=pstmt.executeUpdate();
			}
			con.commit(); //没有错处执行
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			try {
				con.rollback();  //出错回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(con,pstmt,null,null);
		}
		return result;
	}
	
	/**
	 * 单表增删改
	 * @param sql：sql语句集合，里面可以加？
	 * @param params：表示?对应的参数值的集合
	 * @return int:返回的值。成功>0，失败<=0
	 */
	public  synchronized int update(String sql,List<Object> params){
		int result=0;
		con=getConnection();	
		try {
			pstmt=con.prepareStatement(sql);  //预编译对象
			setValues(pstmt,params);    //设置参数
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.log.error(e.toString());
		}finally{
			closeAll(con,pstmt,null,null);
		}
		return result;
	}


	/**
	 * 聚合查询
	 * @param sql：聚合查询语句
	 * @param params：参数列表，用来替换sql中的?（占位符）
	 * @return list:结果集
	 */

	public List<String> uniqueResult(String sql,List<Object> params){
		List<String> list=new ArrayList<String>();
		con=getConnection();
		try {
			pstmt=con.prepareStatement(sql);  //预编译对象
			setValues(pstmt,params);   //设置参数
			rs=pstmt.executeQuery();  //执行查询

			ResultSetMetaData md=rs.getMetaData();  //结果集的元数据，它反映了结果集的信息
			int count=md.getColumnCount();    //取出结果集中列的数量

			if(rs.next()){
				for(int i=1;i<=count;i++){
					list.add(rs.getString(i));
				}
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
		}finally{
			closeAll(con,pstmt,rs,null);
		}
		return list;
	}

	/**
	 * 查询单个表
	 * @param <T> 泛型：即你要得到的集合中存的对象的类型
	 * @param sql: 查询语句，可以含有?
	 * @param params: ?所对应的参数值的集合
	 * @param c： 泛型类型所对应的反射对象
	 * @return ：存储了对象的集合
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <T> List<T> findResult(String sql,List<Object> params,Class<T> c) {
		List<T> list=new ArrayList<T>(); //要返回的结果的集合
		con=getConnection();  //获取连接

		try {
			pstmt=con.prepareStatement(sql); //预编译对象
			setValues(pstmt, params); //设置占位符
			rs=pstmt.executeQuery();  //执行查询语句，得到结果集

			Method[] ms=c.getMethods(); //取出这个反射实例的所有方法
			ResultSetMetaData md=rs.getMetaData(); //获取结果集的元数据，它反映了结果集的信息
			
			String[] colnames=new String[md.getColumnCount()];//创建一个数据colnames，用来存放列的名字
			for(int i=0;i<colnames.length;i++){  //将列名保存到colname数组中
				colnames[i]=md.getColumnName(i+1);
			}
			
			T t;
			String mname=null;  //方法名
			String cname=null;  //列名
			String ctypename=null; //类型名
			
			while(rs.next()){
				t=(T)c.newInstance(); //创建反射类的实例化对象    Product t=(Product)c.newInstance();
				for(int i=0;i<colnames.length;i++){//循环方法名 ,格式为setXXXX或getXXX
					cname="set"+colnames[i]; //取出列名并在前面加上set  setXXX
					
					if(ms!=null&&ms.length>0){
						for(Method m:ms){//循环列名
							mname=m.getName(); //取出方法名
							if(cname.equalsIgnoreCase(mname)&&rs.getObject(colnames[i])!=null){//判断方法名和列名是否一样，相同则激活方法，注入数据                           //只要"set"+数据列名.equalsIgnoreCase（方法名），则激活这个方法
								//setXXX(String str); setXXX(int num); 激活对应的方法还必须知道它的数据类型
								ctypename=rs.getObject(colnames[i]).getClass().getName();//获取当前列的类型名
								
								if("java.lang.Integer".equals(ctypename)){
									m.invoke(t,rs.getInt(colnames[i])); //obj.setXX(xx);
								}else if("java.lang.String".equals(ctypename)){
									m.invoke(t, rs.getString(colnames[i]));
								}else if("java.math.BigInteger".equals(ctypename)){
									m.invoke(t, rs.getDouble(colnames[i]));
								}else if("java.math.BigDecimal".equals(ctypename)){
									try{
										m.invoke(t, rs.getInt(colnames[i]));
									}catch(Exception e1){
										m.invoke(t, rs.getDouble(colnames[i]));
									}
								}else if("java.sql.Timestamp".equals(ctypename)){
									m.invoke(t, rs.getString(colnames[i]));
								}else if("java.sql.Date".equals(ctypename)){
									m.invoke(t, rs.getString(colnames[i]));
								}else if("java.sql.Time".equals(ctypename)){
									m.invoke(t, rs.getString(colnames[i]));
								}else if("image".equals(ctypename)) {
									m.invoke(t,rs.getBlob(colnames[i]));
								}else{
									m.invoke(t, rs.getString(colnames[i]));
								}
								break;
							}
						}
					}
				}
				list.add(t);
			}
		} catch (SecurityException e) {
			LogUtil.log.equals(e.toString());
		} catch (IllegalArgumentException e) {
			LogUtil.log.equals(e.toString());
		} catch (SQLException e) {
			LogUtil.log.equals(e.toString());
		} catch (InstantiationException e) {
			LogUtil.log.equals(e.toString());
		} catch (IllegalAccessException e) {
			LogUtil.log.equals(e.toString());
		} catch (InvocationTargetException e) {
			LogUtil.log.equals(e.toString());
		}finally{
			closeAll(con, pstmt, rs,null);
		}
		return list;
	}

	/**
	 * 多表查询
	 * @param sql：查询语句
	 * @param params： 查询语句中?所对应的值
	 * @return：结果集，存在一个List表中，用Map一对一的存放
	 * @throws SQLException
	 */
	public List<String> findResult(String sql,List<Object> params){
		List<String> result=new ArrayList<String>(); //将结果一次存在list中返回

		con=this.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			this.setValues(pstmt, params);
			rs=pstmt.executeQuery();

			ResultSetMetaData md=rs.getMetaData(); //获取结果集的元数据
			
			String[] colnames=new String[md.getColumnCount()]; //获取结果集中的列名
			for(int i=0;i<colnames.length;i++){
				colnames[i]=md.getColumnName(i+1);
			}
			while(rs.next()){
				for(int i=0;i<colnames.length;i++){
					result.add(rs.getString(i+1));
				}
			}
		} catch (SQLException e) {
			LogUtil.log.equals(e.toString());
		}finally{
			this.closeAll(con, pstmt, rs ,null);
		}
		return result;
	}


	/**
	 * 存过过程参数设置
	 * @param cst
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void setParams(CallableStatement cs,Map<Integer,Object> paramsIn,Map<Integer,String> paramsOut){
		int key=0; //对应的问号的序号
		Object value=null;
		String typename=null;

		String attrType;
		Set keys;  //所有的键
		if(paramsIn!=null&&paramsIn.size()>0){
			keys=paramsIn.keySet();  //取出所有入参的键，即入参对应的问号的序号
			if(keys!=null){
				Iterator iterator=keys.iterator();
				while(iterator.hasNext()){
					key=(Integer) iterator.next();
					value=paramsIn.get(key);      //1,88
					attrType=value.getClass().getName();

					//判断值的数据类型
					try {
						if("java.lang.Integer".equals(attrType)){
							cs.setInt(key,(Integer)value);
						}else if("java.lang.String".equals(attrType)){
							cs.setString(key,(String)value);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		int typeId=0;

		if(paramsOut!=null&&paramsOut.size()>0){
			keys=paramsOut.keySet();  //取出所有入参的键，即入参对应的问号的序号
			if(keys!=null){
				Iterator iterator=keys.iterator();
				while(iterator.hasNext()){
					key=(Integer) iterator.next();
					typename=(String) paramsOut.get(key);      //3,varchar  4, cursor

					//判断值的数据类型
					try {
						if("cursor".equals(typename)){
							typeId=oracle.jdbc.OracleTypes.CURSOR;
						}else if("int".equals(typename)){
							typeId=Types.INTEGER;
						}else if("double".equals(typename)){
							typeId=Types.NUMERIC;
						}else{
							typeId=Types.VARCHAR;
						}
						cs.registerOutParameter(key,typeId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 执行oracle中的存储过程
	 * @param prc：要执行的存储过程名字  如：test(?);
	 * @param params：占位符对于的参数列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<String> execProcedure(String prc,Map<Integer,Object> paramsIn,Map<Integer,String> paramsOut){
		List<String> list=new ArrayList<String>();
		String pro="{call "+prc+"}";

		con=getConnection();
		
		int key;
		String typename;
		Set keys;
		ResultSetMetaData md=null;
		
		try {
			cs=con.prepareCall(pro);
			//设置过程的参数
			setParams(cs,paramsIn,paramsOut);
			cs.execute();
			
			if(paramsOut!=null&&paramsOut.size()>0){
				keys=paramsOut.keySet();  //取出所有入参的键，即入参对应的问号的序号
				if(keys!=null){
					Iterator iterator=keys.iterator();
					while(iterator.hasNext()){
						key=(Integer) iterator.next();
						typename=(String) paramsOut.get(key);      //3,varchar  4, cursor

						//判断值的数据类型
						try {
							if("cursor".equals(typename)){
								rs=(ResultSet) cs.getObject(key);  //1 a 24  2 b 23
								md=rs.getMetaData();
								while(rs.next()){
									for(int i=0;i<md.getColumnCount();i++){
										list.add(rs.getString(i+1));
									}
								}
							}else{
								list.add(cs.getString(key));
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll(con,null,rs,cs);
		}
		return list;
	}
	
	/**
	 * oracle数据的备份
	 * @param path：备份文件存放路径
	 * @param tablespaceName：要备份的表空间
	 * @return
	 */
	public int OracleBackUp(String path,String tablespaceName){
		Runtime rt = Runtime.getRuntime();
		@SuppressWarnings("unused")
		Process processexp = null;
		//String exp="exp system/a@orcl tablespaces=("+tablespaceName+") file="+path+".dmp log="+path+".log owner=scott";
		String exp="exp system/a@orcl tablespaces=("+tablespaceName+") file="+path+".dmp";

		try {
			processexp = rt.exec(exp);
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Oracle中按用户方式恢复数据库
	 * @param uname：用户名
	 * @return
	 */
	public int dataResume(String path,String uname){
		Runtime rt = Runtime.getRuntime();
		@SuppressWarnings("unused")
		Process processimp = null;
		//String imp="imp system/a@orcl file="+path+" log="+path.substring(0,path.lastIndexOf("."))+".log ignore=y owner=scott";  // full=y

//		List<String> list=new ArrayList<String>();
//		list.add("tri_aid");
//		list.add("tri_cid");
//		list.add("tri_eid");
//		list.add("tri_pid");
//		list.add("tri_sid");
//		list.add("tri_uid");
//		String sql;
//
//		for(String str:list){
//			sql="alter trigger "+str+" disable";
//			con=getConnection();  //获取连接
//
//			try {
//				pstmt=con.prepareStatement(sql); //预编译对象
//				pstmt.execute();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}

		String imp="imp system/a@orcl file="+path+" ignore=y full=y"; 

		try {
			processimp = rt.exec(imp);
//			
//			for(String str:list){
//				sql="alter trigger "+str+" enable";
//				con=getConnection();  //获取连接
//
//				try {
//					pstmt=con.prepareStatement(sql); //预编译对象
//					pstmt.execute();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			
			
			
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取当前数据库中的所有表的表名
	 * @return：返回一个list,存放着当前数据库中的所有表的表名
	 * oracle中获取当前模式的所有表信息为select * from tab;
	 */
	public List<String>  findtable(){
		List<String> list=new ArrayList<String>();
		con=this.getConnection();
		try {
			DatabaseMetaData dmd=con.getMetaData();
			rs=dmd.getTables(null, null, null,new String[]{"TABLE"});
			while(rs.next()){
				list.add(rs.getString("Table_name")); //将当前数据库中是所有表存到list中
			}
		} catch (SQLException e) {
			LogUtil.log.equals(e.toString());
		}
		return list;
	}


	/**
	 * 自动创建一个由一个类中的属性作为字段的表
	 * @param <T>
	 * @param c：要生成表的类
	 */
	public <T> void getTable(Class<T> c){
		con=this.getConnection();
		Field[] fs=c.getDeclaredFields(); //获取元数据
		String st=c.getName(); //得到结果集中的每个列名

		st=st.substring(st.lastIndexOf(".")+1); //截取类名
		st=st.substring(0,1).toLowerCase()+st.substring(1,st.length()); //将首字母变为小写

		String str1=null;
		StringBuffer str=new StringBuffer();
		String str3=null;
		List<String> list=this.findtable();

		try {
			for(String lt:list){
				if(lt.equalsIgnoreCase(st)){
					String sql="drop table "+st;
					pstmt=con.prepareStatement(sql);
					pstmt.executeUpdate();
					break;
				}
			}
			str.append("create table "+st+"(id int primary key identity(1,1), ");

			for(int i=0;i<fs.length;i++){
				str1=fs[i].getName();  //得到每个属性的名字
				Class<?> str2=fs[i].getType();  //得到每个属性的数据类型
				str3=str2.toString();
				if("class java.lang.String".equals(str3)){//如果是String类型，则把它改为varchar(50)
					str3="varchar(50)";
				}
				if("double".equals(str3)){//如果是double类型，则把它改为float
					str3="float";
				}
				if(i==fs.length-1){
					str.append(str1+"\t"+str3+")"); //如果是最后一个字段，不要加逗号而加括号
				}else{
					str.append(str1+"\t"+str3+",");  //如果不是最后一个，则在语句后在括号
				}
			}	
			String sql=str.toString();	
			System.out.println(sql);
			boolean result=true;

			pstmt=con.prepareStatement(sql);		
			result=pstmt.execute();	
			if(result!=true){
				LogUtil.log.info(st+"数据库创建成功!");
			}else{
				LogUtil.log.info(st+"数据库创建失败!");
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
		}	

	}	

	/**
	 * 自动创建一个由一个类中的属性作为字段的表
	 * @param <T>
	 * @param c：要生成表的类
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("null")
	public <T> void getTables(File f){
		Class<?> c = null;
		con=this.getConnection();
		File[] fls=f.listFiles(); //得到f路径下的文件
		String fname=null;
		String st=null;
		String str1=null;
		String str;
		String str3=null;
		Field[] fd;
		if(fls!=null){ //判断是不是有文件
			for(File fs:fls){ //循环读取文件
				fname=fs.getName(); //得到文件名

				if(fname.substring(fname.lastIndexOf(".")+1).equalsIgnoreCase("java")){//判断是不是java文件
					st=fname.substring(0,fname.indexOf("."))+".class";//取到类名，并添加后缀.class

					fd=c.getClass().getDeclaredFields();//得到java类中的属性
					str="create table "+st+"(id int primary key identity(1,1), ";
					for(int i=0;i<fd.length;i++){
						str1=fd[i].getName();  //得到每个属性的名字
						Class<?> str2=fd[i].getType();  //得到每个属性的数据类型
						str3=str2.toString();
						if("class java.lang.String".equals(str3)){//如果是String类型，则把它改为varchar(50)
							str3="varchar(50)";
						}
						if("double".equals(str3)){//如果是double类型，则把它改为float
							str3="float";
						}
						if(i==fd.length-1){
							str=str+str1+"\t"+str3+")"; //如果是最后一个字段，不要加逗号而加括号
						}else{
							str=str+str1+"\t"+str3+",";  //如果不是最后一个，则在语句后在括号
						}
					}	
					String sql=str;	
					System.out.println(sql);
					boolean result=true;
					try {
						pstmt=con.prepareStatement(sql);		
						result=pstmt.execute();	
						if(result!=true){
							LogUtil.log.info("数据库创建成功!");
						}else{
							LogUtil.log.info("数据库创建失败!");
						}	
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						this.closeAll(con, pstmt,null,null);
					}
				}	
			}
		}else{
			LogUtil.log.info("此文件下，没有文件！");
		}
	}
}

