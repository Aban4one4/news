数据库连接池配置步骤
1. 修改DBHepler.java
	public Connection getConnection(){
		//javax.naming.Context提供了查找JNDI 的接口
		try {
			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/usersys");
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

2.修改tomcat的配置文件context.xml,在<Context></Context>中添加如下代码
	<Resource
	    name="jdbc/news"   
	    auth="Container"   
	    type="javax.sql.DataSource" 
	    driverClassName="oracle.jdbc.driver.OracleDriver"
	    username="scott"
	    password="a"
		url="jdbc:oracle:thin:@127.0.0.1:1521:ORCL"
	    autoReconnect="true"
		autoReconnectForPools="true"
	    maxActive="120"
	    maxIdle="30"
	    maxWait="8000"
		removeAbandoned="true"
		removeAbandonedTimeout="60"
		logAbandoned="true"
	    />
	    
3. 将数据库的驱动包拷贝到tomcat的lib目录下