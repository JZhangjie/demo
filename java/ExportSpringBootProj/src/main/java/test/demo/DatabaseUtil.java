package test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.demo.entityxml.Entity;
import test.demo.entityxml.Field;
import test.demo.entityxml.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);

    private static  String DRIVER = "org.postgresql.Driver";
    private static  String URL = "jdbc:postgresql://127.0.0.1:5432/wfjsjg";
    private static  String USERNAME = "postgres";
    private static  String PASSWORD = "123456";

    private static  String SQL = "SELECT * FROM ";// 数据库操作
    
    /**
     * 设置参数
     *
     * @return
     */
    public static void setParams(Project project) {
    	DRIVER = project.getDbdriver();
    	URL = project.getDburl();
    	USERNAME = project.getDbusername();
    	PASSWORD = project.getDbpassword();
    	
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<String>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, "public", null, new String[] { "TABLE" });
            while(rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<String>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<String>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }
    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public static Entity getEntityFromTable(String tableName,Entity entity) {
    	if(entity==null) {
       		entity = new Entity();
    	}
    	String tablenamelower = tableName.toLowerCase();
    	entity.setName(tablenamelower.substring(0,1).toUpperCase()+tablenamelower.substring(1));
    	entity.setTable(tableName);
    	entity.setNamelow(tablenamelower);
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tablenamelower;
        String primarykeyname ="";
        try {
        	DatabaseMetaData metaData = conn.getMetaData();
        	pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            ResultSet  rSet =metaData.getPrimaryKeys(rsmd.getCatalogName(0), rsmd.getSchemaName(0), tablenamelower);
            while (rSet.next()) {
            	primarykeyname =rSet.getString("COLUMN_NAME");
			}
            Field pField =entity.getPrimarykey();
            if(pField==null) {
            	pField = new Field();
            	entity.setPrimarykey(pField);
            }
            
            List<Field> fields = entity.getFields();
            if(fields==null) {
        		fields = new ArrayList<Field>();
        		entity.setFields(fields);
        	}
            
            int size = rsmd.getColumnCount();
            
            for (int i = 0; i < size; i++) {
            	String colname = rsmd.getColumnName(i + 1);
            	String name =colname.toLowerCase();
            	String upname = name.substring(0, 1).toUpperCase()+name.substring(1);
            	String classname = rsmd.getColumnClassName(i+1);
            	int typenum = rsmd.getColumnType(i+1);
            	JDBCType s = JDBCType.valueOf(typenum);
            	String jdbctypename = s.getName();
            	
            	Field field = new Field();
            	field.setName(name);
            	int index =fields.indexOf(field);
            	if(index!=-1) {
            		field = fields.get(index);
            	}
            	
        		field.setNameupper(upname);
            	field.setDbtype(jdbctypename);
            	field.setJavatype(classname);
            	
            	if(!fields.contains(field)) {
            		fields.add(field);
            	}
            	if(primarykeyname.equals(colname)) {
            		pField.setName(name);
            		pField.setNameupper(upname);
            		pField.setDbtype(jdbctypename);
            		pField.setJavatype(classname);
            	}

            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return entity;
    }

    /**
     * 获取表中字段的所有注释
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<String>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<String>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }
    
    public static void main( String[] args )
    {
    	Entity entity = DatabaseUtil.getEntityFromTable("rymlk",null);
    	System.out.println(entity.getName());
    }
    
}